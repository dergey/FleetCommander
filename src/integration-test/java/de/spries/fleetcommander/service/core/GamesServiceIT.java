package de.spries.fleetcommander.service.core;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import de.spries.fleetcommander.model.core.Game.GameStatus;
import de.spries.fleetcommander.service.core.dto.GameAccessParams;
import de.spries.fleetcommander.service.core.dto.GameParams;
import de.spries.fleetcommander.service.core.dto.GamePlayer;

public class GamesServiceIT {

	private GamesService service;
	private GamePlayer gamePlayer;

	@Before
	public void setUp() {
		service = new GamesService();
		GameAccessParams accessParams = service.createNewGame("Player 1");
		gamePlayer = GamePlayer.forIds(accessParams.getGameId(), accessParams.getPlayerId());
	}

	@Test
	public void canGetCreatedGame() {
		assertThat(service.getGame(gamePlayer), is(notNullValue()));
	}

	@Test
	public void canAddComputerPlayer() throws Exception {
		assertThat(service.getGame(gamePlayer).getOtherPlayers(), hasSize(0));

		service.addComputerPlayer(gamePlayer);
		assertThat(service.getGame(gamePlayer).getOtherPlayers(), hasSize(1));
		assertThat(service.getGame(gamePlayer).getOtherPlayers().get(0).getName(), is("Computer 1"));

		service.addComputerPlayer(gamePlayer);
		assertThat(service.getGame(gamePlayer).getOtherPlayers(), hasSize(2));
		assertThat(service.getGame(gamePlayer).getOtherPlayers().get(1).getName(), is("Computer 2"));
	}

	@Test
	public void canStartGame() throws Exception {
		service.addComputerPlayer(gamePlayer);
		assertThat(service.getGame(gamePlayer).getStatus(), is(GameStatus.PENDING));

		GameParams params = new GameParams();
		service.modifyGame(gamePlayer, params);
		assertThat(service.getGame(gamePlayer).getStatus(), is(GameStatus.PENDING));

		params.setIsStarted(false);
		service.modifyGame(gamePlayer, params);
		assertThat(service.getGame(gamePlayer).getStatus(), is(GameStatus.PENDING));

		params.setIsStarted(true);
		service.modifyGame(gamePlayer, params);
		assertThat(service.getGame(gamePlayer).getStatus(), is(GameStatus.RUNNING));
	}
}
