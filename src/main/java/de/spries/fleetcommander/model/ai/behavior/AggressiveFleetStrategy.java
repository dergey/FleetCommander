package de.spries.fleetcommander.model.ai.behavior;

import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.universe.HasCoordinates;
import de.spries.fleetcommander.model.universe.Planet;
import de.spries.fleetcommander.model.universe.Universe;

import java.util.List;
import java.util.stream.Collectors;

public class AggressiveFleetStrategy implements FleetStrategy {

	@Override
	public void sendShips(Universe universe, Player player) {
		Planet homePlanet = universe.getHomePlanetOf(player);
		List<Planet> allPlanets = universe.getPlanets();
		List<Planet> myPlanets = Planet.filterPlayerPlanets(allPlanets, player);
		List<Planet> enemyPlanets =  Planet.filterEnemyPlanets(allPlanets, player);

		if (enemyPlanets.isEmpty()) {
			List<Planet> unknownPlanets = allPlanets.stream()
					.filter(p -> !p.isInhabitedBy(player) && p.getIncomingShipCount(player) == 0)
					.collect(Collectors.toList());
			unknownPlanets = HasCoordinates.sortByDistanceAsc(unknownPlanets, homePlanet);

			int numPlanetsToInvade = Math.min(unknownPlanets.size(), homePlanet.getShipCount());
			List<Planet> planetsToInvade = unknownPlanets.subList(0, numPlanetsToInvade);
			for (Planet unknownPlanet : planetsToInvade) {
				universe.sendShips(1, homePlanet.getId(), unknownPlanet.getId(), player);
			}
		}
		else {
			Planet someEnemyPlanet = enemyPlanets.get(0);
			for (Planet planet : myPlanets) {
				int ships = planet.getShipCount();
				if (ships > 0) {
					universe.sendShips(ships, planet.getId(), someEnemyPlanet.getId(), player);
				}
			}
		}
	}

}
