package de.spries.fleetcommander.model.ai.behavior;

import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.universe.Universe;

public interface BuildingStrategy {

	void buildFactories(Universe universe, Player player);

}
