package de.spries.fleetcommander.model.ai.behavior;

import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.universe.HasCoordinates;
import de.spries.fleetcommander.model.universe.Planet;
import de.spries.fleetcommander.model.universe.Universe;

import java.util.Collection;

public class DefaultBuildingStrategy implements BuildingStrategy {

	@Override
	public void buildFactories(Universe universe, Player player) {
		Collection<Planet> allPlanets = universe.getPlanets();
		Collection<Planet> myPlanets = Planet.filterPlayerPlanets(allPlanets, player);

		myPlanets = HasCoordinates.sortByDistanceAsc(myPlanets, universe.getHomePlanetOf(player));

		for (Planet planet : myPlanets) {
			while (planet.canBuildFactory(player)) {
				planet.buildFactory(player);
			}
		}
	}
}
