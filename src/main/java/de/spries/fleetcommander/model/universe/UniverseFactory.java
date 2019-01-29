package de.spries.fleetcommander.model.universe;

import de.spries.fleetcommander.model.Game;
import de.spries.fleetcommander.model.Player;

import java.util.*;

public class UniverseFactory {
	public static final int PLANET_COUNT = 100;
	private static final int MIN_PLANET_OFFSET = -2;
	private static final int MAX_PLANET_OFFSET = 2;
	private static final Random rand = new Random();

	/**
	 * Prototype implementation for testing purposes
	 */
	public static Universe generate(Collection<Player> players) {
		List<Planet> planets = new ArrayList<>(PLANET_COUNT);

		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				int offsetX = randomOffset();
				int offsetY = randomOffset();

				Planet planet = new Planet(col * 10 + 5 + offsetX, row * 10 + 5 + offsetY);
				planets.add(planet);
			}
		}

		Collections.shuffle(planets);

		planets = planets.subList(0, planets.size() / (1 + Game.MAX_PLAYERS - players.size()));

		int planetId = 0;
		for (Planet planet : planets) {
			planet.setId(planetId++);
		}

		int i = 0;
		for (Player player : players) {
			Planet oldUninhabitedPlanet = planets.get(i);
			Planet newHomePlanet = new Planet(oldUninhabitedPlanet.getX(), oldUninhabitedPlanet.getY(), player);
			newHomePlanet.setId(oldUninhabitedPlanet.getId());
			planets.set(i, newHomePlanet);
			i++;
		}

		return new Universe(planets);
	}

	private static int randomOffset() {
		return rand.nextInt(MAX_PLANET_OFFSET - MIN_PLANET_OFFSET + 1) + MIN_PLANET_OFFSET;
	}

}