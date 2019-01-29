package de.spries.fleetcommander.model.ai;

import de.spries.fleetcommander.model.Game;
import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.ai.behavior.BuildingStrategy;
import de.spries.fleetcommander.model.ai.behavior.FleetStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ComputerPlayer extends Player {

	private static final Logger LOGGER = LogManager.getLogger(ComputerPlayer.class.getName());
	private BuildingStrategy buildingStrategy;
	private FleetStrategy fleetStrategy;

	public ComputerPlayer(String name, BuildingStrategy buildingStrategy, FleetStrategy fleetStrategy) {
		super(name);
		this.buildingStrategy = buildingStrategy;
		this.fleetStrategy = fleetStrategy;
		setReady();
	}

	@Override
	public boolean isHumanPlayer() {
		return false;
	}

	@Override
	public void notifyNewTurn(Game game) {
		try {
			playTurn(game);
		} catch (Exception e) {
			// Just end the turn (still it shouldn't happen)
			String msg = String.format("Game %d: Computer player '%s' caused an exception: ", game.getId(), getName());
			LOGGER.warn(msg, e);
		}
		game.endTurn(this);
	}

	public void playTurn(Game game) {
		buildingStrategy.buildFactories(game.getUniverse(), this);
		fleetStrategy.sendShips(game.getUniverse(), this);
	}

}
