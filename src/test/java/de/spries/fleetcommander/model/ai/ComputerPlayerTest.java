package de.spries.fleetcommander.model.ai;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.spries.fleetcommander.model.ai.ComputerPlayer;
import de.spries.fleetcommander.model.ai.behavior.BuildingStrategy;
import de.spries.fleetcommander.model.ai.behavior.FleetStrategy;
import de.spries.fleetcommander.model.core.Game;
import de.spries.fleetcommander.model.core.Player.Status;
import de.spries.fleetcommander.model.core.universe.Universe;
import de.spries.fleetcommander.model.facade.PlayerSpecificUniverse;

public class ComputerPlayerTest {

	private ComputerPlayer player;
	private Game game;
	private Universe universe;
	private BuildingStrategy buildingStrategy;
	private FleetStrategy fleetStrategy;

	@Before
	public void setUp() {
		buildingStrategy = mock(BuildingStrategy.class);
		fleetStrategy = mock(FleetStrategy.class);
		player = new ComputerPlayer("Computer", buildingStrategy, fleetStrategy);

		game = mock(Game.class);
		universe = mock(Universe.class);

		doReturn(universe).when(game).getUniverse();
	}

	@Test
	public void isntAHumanPlayer() throws Exception {
		assertThat(player.isHumanPlayer(), is(false));
	}

	@Test
	public void isReadyInitially() throws Exception {
		assertThat(player.getStatus(), is(Status.READY));
	}

	@Test
	public void callsBuildingStrategy() throws Exception {
		player.notifyNewTurn(game);
		verify(buildingStrategy).buildFactories(Mockito.any(PlayerSpecificUniverse.class));
	}

	@Test
	public void callsFleetStrategy() throws Exception {
		player.notifyNewTurn(game);
		verify(fleetStrategy).sendShips(Mockito.any(PlayerSpecificUniverse.class));
	}

	@Test
	public void endsTurn() {
		player.notifyNewTurn(game);
		verify(game).endTurn(player);
	}

	@Test
	public void stillEndsTurnWhenPlayerHasNoMoreHomePlanet() throws Exception {
		doThrow(RuntimeException.class).when(universe).getHomePlanetOf(player);
		player.notifyNewTurn(game);
		verify(game).endTurn(player);
	}

	@Test
	public void endsTurnIfFleetStrategyThrowsException() throws Exception {
		doThrow(RuntimeException.class).when(fleetStrategy).sendShips(Mockito.any(PlayerSpecificUniverse.class));
		player.notifyNewTurn(game);
		verify(game).endTurn(player);
	}

	@Test
	public void endsTurnIfBuildingStrategyThrowsException() throws Exception {
		doThrow(RuntimeException.class).when(buildingStrategy)
				.buildFactories(Mockito.any(PlayerSpecificUniverse.class));
		player.notifyNewTurn(game);
		verify(game).endTurn(player);
	}

}
