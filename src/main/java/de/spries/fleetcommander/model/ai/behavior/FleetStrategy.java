package de.spries.fleetcommander.model.ai.behavior;

import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.universe.Universe;

public interface FleetStrategy {

	void sendShips(Universe universe, Player player);

}
