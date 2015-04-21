package de.spries.fleetcommander.service.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.spries.fleetcommander.model.core.Game;
import de.spries.fleetcommander.model.core.Player;
import de.spries.fleetcommander.model.facade.PlayerSpecificGame;
import de.spries.fleetcommander.persistence.GameStore;
import de.spries.fleetcommander.service.core.dto.GameAccessParams;
import de.spries.fleetcommander.service.core.dto.GameParams;
import de.spries.fleetcommander.service.core.dto.ShipFormationParams;

public class GamesService {

	private static final Logger LOGGER = LogManager.getLogger(GamesService.class.getName());

	public GameAccessParams createNewGame(String playerName) {
		Game game = new Game();
		Player p = new Player(playerName);
		game.addPlayer(p);

		int gameId = GameStore.INSTANCE.create(game);
		game.setId(gameId);
		String authToken = GameAuthenticator.INSTANCE.createAuthToken(gameId);

		LOGGER.debug("Game {}: Created", gameId);

		return new GameAccessParams(gameId, authToken);
	}

	public PlayerSpecificGame getGame(int gameId) {
		Game game = GameStore.INSTANCE.get(gameId);
		LOGGER.debug("Game {}: Get", gameId);
		if (game != null) {
			Player player = game.getPlayers().get(0);
			return new PlayerSpecificGame(game, player);
		}
		LOGGER.warn("Game {}: Get, but doesn't exist", gameId);
		throw new IllegalArgumentException("The game doesn't exist on the server");
	}

	public void deleteGame(int gameId) {
		LOGGER.debug("Game {}: Delete", gameId);
		GameAuthenticator.INSTANCE.deleteAuthToken(gameId);
		GameStore.INSTANCE.delete(gameId);
	}

	public void addComputerPlayer(int gameId) {
		LOGGER.debug("Game {}: Add computer player", gameId);
		PlayerSpecificGame game = getGame(gameId);
		game.addComputerPlayer();
	}

	public void modifyGame(int gameId, GameParams params) {
		LOGGER.debug("Game {}: Modify with params {}", gameId, params);
		if (Boolean.TRUE.equals(params.getIsStarted())) {
			PlayerSpecificGame game = getGame(gameId);
			game.start();
		}
	}

	public void endTurn(int gameId) {
		LOGGER.debug("Game {}: End turn", gameId);
		PlayerSpecificGame game = getGame(gameId);
		game.endTurn();
	}

	public void sendShips(int gameId, ShipFormationParams ships) {
		LOGGER.debug("Game {}: Send ships with params {}", gameId, ships);
		PlayerSpecificGame game = getGame(gameId);
		game.getUniverse().sendShips(ships.getShipCount(), ships.getOriginPlanetId(),
				ships.getDestinationPlanetId());
	}

	public void buildFactory(int gameId, int planetId) {
		LOGGER.debug("Game {}: Build factory on planet {}", gameId, planetId);
		PlayerSpecificGame game = getGame(gameId);
		game.getUniverse().getPlanet(planetId).buildFactory();
	}
}
