package de.spries.fleetcommander.model.universe;

import de.spries.fleetcommander.exception.IllegalActionException;
import de.spries.fleetcommander.model.Player;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Universe {

	private final List<Planet> planets;
	private Collection<ShipFormation> travellingShipFormations;

	protected Universe(List<Planet> planets) {
		if (CollectionUtils.isEmpty(planets)) {
			throw new IllegalArgumentException("List of planets required");
		}
		this.planets = Collections.unmodifiableList(planets);
		travellingShipFormations = new HashSet<>();
	}

	public List<Planet> getPlanets() {
		return planets;
	}

	public Collection<Planet> getHomePlanets() {
		return Planet.filterHomePlanets(planets);
	}

	public Planet getHomePlanetOf(Player player) {
		Optional<Planet> homePlanet = Planet.filterHomePlanet(planets, player);
		if (homePlanet.isPresent()) {
			return homePlanet.get();
		}
		return null;
	}

	public void runFactoryProductionCycle() {
		planets.stream().forEach(p -> p.runProductionCycle());
	}

	public void runShipTravellingCycle() {
		travellingShipFormations.stream().sorted(ShipFormation.CLOSE_TO_TARGET_FIRST).forEach(s -> s.travel());
		travellingShipFormations = travellingShipFormations.stream().filter(s -> !s.hasArrived())
				.collect(Collectors.toSet());
	}

	public void resetPreviousTurnMarkers() {
		planets.stream().forEach(p -> p.resetMarkers());
	}

	public void sendShips(int shipCount, int originPlanetId, int destinationPlanetId, Player player) {
		Planet origin = getPlanetForId(originPlanetId);
		Planet destination = getPlanetForId(destinationPlanetId);
		sendShips(shipCount, origin, destination, player);
	}

	public void sendShips(int shipCount, Planet origin, Planet destination, Player player) {
		if (!planets.contains(origin) || !planets.contains(destination)) {
			throw new IllegalActionException("origin & destination planets must be contained in universe");
		}

		if (destination.equals(origin)) {
			return;
		}

		origin.sendShipsAway(shipCount, player);
		destination.addIncomingShips(shipCount, player);

		ShipFormation newShipFormation = new ShipFormation(shipCount, origin, destination, player);
		ShipFormation joinableFormation = getJoinableShipFormation(newShipFormation);

		if (joinableFormation == null) {
			travellingShipFormations.add(newShipFormation);
		} else {
			newShipFormation.join(joinableFormation);
		}
	}

	public Planet getPlanetForId(int planetId) {
		return Planet.findById(planets, planetId);
	}

	private ShipFormation getJoinableShipFormation(ShipFormation newShipFormation) {
		for (ShipFormation formation : travellingShipFormations) {
			if (newShipFormation.canJoin(formation)) {
				return formation;
			}
		}
		return null;
	}

	public Collection<ShipFormation> getTravellingShipFormations() {
		return travellingShipFormations;
	}

	public void setEventBus(TurnEventBus turnEventBus) {
		planets.stream().forEach(p -> p.setEventBus(turnEventBus));
	}

	public void handleDefeatedPlayer(Player newDefeatedPlayer) {
		planets.stream().forEach(p -> p.handleDefeatedPlayer(newDefeatedPlayer));
		travellingShipFormations = travellingShipFormations.stream()
				.filter(s -> !newDefeatedPlayer.equals(s.getCommander()))
				.collect(Collectors.toSet());
	}
}