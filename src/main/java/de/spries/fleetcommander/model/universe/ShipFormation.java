package de.spries.fleetcommander.model.universe;

import de.spries.fleetcommander.exception.IllegalActionException;
import de.spries.fleetcommander.model.Player;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ShipFormation {

	public static final int DISTANCE_PER_TURN = 8;
	public static final Comparator<ShipFormation> CLOSE_TO_TARGET_FIRST = (o1, o2) -> Double.compare(
			o1.getDistanceRemaining(), o2.getDistanceRemaining());

	private int shipCount;
	private Planet origin;
	private Planet destination;
	private Player commander;
	private int distanceTravelled = 0;
	private final double distanceOverall;

	public ShipFormation(int shipCount, Planet origin, Planet destination, Player commander) {
		if (shipCount <= 0) {
			throw new IllegalActionException("Must send positive number of ships");
		}
		if (origin == null || destination == null || commander == null) {
			throw new IllegalActionException(
					"Must define non-null origin, destination & commander to send ships");
		}
		this.shipCount = shipCount;
		this.origin = origin;
		this.destination = destination;
		this.commander = commander;
		distanceOverall = origin.distanceTo(destination);
	}

	public int getShipCount() {
		return shipCount;
	}

	public Planet getOrigin() {
		return origin;
	}

	public Planet getDestination() {
		return destination;
	}

	public Player getCommander() {
		return commander;
	}

	public boolean canJoin(ShipFormation existingFormation) {
		if (origin.equals(existingFormation.origin) && destination.equals(existingFormation.destination)
				&& commander.equals(existingFormation.commander)
				&& distanceTravelled == existingFormation.distanceTravelled) {
			return true;
		}
		return false;
	}

	public void join(ShipFormation existingFormation) {
		if (!canJoin(existingFormation)) {
			throw new IllegalArgumentException("cannot join this formation");
		}
		existingFormation.shipCount += shipCount;
		shipCount = 0;
	}

	public void travel() {
		distanceTravelled += DISTANCE_PER_TURN;
		if (hasArrived()) {
			landOnDestination();
		}
	}

	protected void landOnDestination() {
		destination.landShips(shipCount, commander);
		shipCount = 0;
	}

	public boolean hasArrived() {
		return getDistanceRemaining() <= 0;
	}

	public int getDistanceTravelled() {
		return distanceTravelled;
	}

	public double getDistanceRemaining() {
		return distanceOverall - distanceTravelled;
	}

	public double getPositionX() {
		return origin.getX() + (destination.getX() - origin.getX()) * distanceTravelled / distanceOverall;
	}

	public double getPositionY() {
		return origin.getY() + (destination.getY() - origin.getY()) * distanceTravelled / distanceOverall;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public static Collection<ShipFormation> filterByCommander(Collection<ShipFormation> shipFormations, Player commander) {
		return shipFormations.stream()
				.filter(s -> s.getCommander().equals(commander)).collect(Collectors.toList());
	}

}
