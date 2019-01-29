package de.spries.fleetcommander.model.universe;

import de.spries.fleetcommander.model.Player;

public interface TurnEventBus {

	void fireConqueredEnemyPlanet(Player invader);

	void fireConqueredUninhabitedPlanet(Player invader);

	void fireDefendedPlanet(Player inhabitant);

	void fireLostPlanet(Player previousInhabitant);

	void fireLostShipFormation(Player commander);
}
