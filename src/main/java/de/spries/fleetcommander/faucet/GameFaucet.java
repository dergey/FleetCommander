package de.spries.fleetcommander.faucet;

import de.spries.fleetcommander.converter.GameConverter;
import de.spries.fleetcommander.dto.*;
import de.spries.fleetcommander.exception.IllegalActionException;
import de.spries.fleetcommander.model.Game;
import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.universe.Planet;
import de.spries.fleetcommander.service.GameAuthenticatorService;
import de.spries.fleetcommander.service.GameService;
import de.spries.fleetcommander.service.JoinCodesService;
import de.spries.fleetcommander.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameFaucet {

    private final GameService gameService;
    private final PlayerService playerService;
    private final JoinCodesService joinCodesService;
    private final GameAuthenticatorService gameAuthenticatorService;

    public GameAccessDto createGame(String playerName) {
        Player player = playerService.createNewPlayer(playerName);
        String authToken = gameAuthenticatorService.createAuthToken(player);
        Game game = gameService.createNewGame(player);
        return new GameAccessDto(player.getId() + ":" + authToken, game.getId());
    }

    public GameAccessDto joinGame(String playerName, String joinCode) {
        Player player = playerService.createNewPlayer(playerName);
        String authToken = gameAuthenticatorService.createAuthToken(player);
        Game game = gameService.joinGame(player, joinCode);
        return new GameAccessDto(player.getId() + ":" + authToken, game.getId());
    }

    public GameDto getGameDto(int gameId, int playerId) {
        Game game = getGame(gameId, true);
        Player player = getPlayer(game, playerId, true);
        return GameConverter.convert(game, player);
    }

    public void quitGame(int gameId, int playerId) {
        Game game = getGame(gameId, true);
        Player player = getPlayer(game, playerId, true);
        gameAuthenticatorService.deleteAuthToken(player);
        game.quit(player);
    }

    public void addComputerPlayer(int gameId) {
        Game game = getGame(gameId, true);
        playerService.addComputerPlayer(game);
    }

    public void modifyGame(int gameId, int playerId, GameParamsDto params) {
        if (Boolean.TRUE.equals(params.getIsStarted())) {
            Game game = getGame(gameId, true);
            Player player = getPlayer(game, playerId, true);
            game.start(player);
            joinCodesService.invalidateAll(gameId);
        }
    }

    public void endTurn(int gameId, int playerId) {
        Game game = getGame(gameId, true);
        Player player = getPlayer(game, playerId, true);
        game.endTurn(player);
    }

    public void sendShips(int gameId, int playerId, ShipFormationParamsDto ships) {
        Game game = getGame(gameId, true);
        Player player = getPlayer(game, playerId, true);
        game.getUniverse().sendShips(ships.getShipCount(), ships.getOriginPlanetId(),
                ships.getDestinationPlanetId(), player);
    }

    public void changePlanetProductionFocus(int gameId, int playerId, int planetId, PlanetParamsDto params) {
        Game game = getGame(gameId, true);
        Player player = getPlayer(game, playerId, true);
        Planet planet = game.getUniverse().getPlanetForId(planetId);
        planet.setProductionFocus(params.getProductionFocus(), player);
    }

    public void buildFactory(int gameId, int playerId, int planetId) {
        Game game = getGame(gameId, true);
        Player player = getPlayer(game, playerId, true);
        game.getUniverse().getPlanetForId(planetId).buildFactory(player);
    }

    private Game getGame(int gameId, boolean throwExceptionIfNotFound) {
        Game game = gameService.getById(gameId);
        if (game == null && throwExceptionIfNotFound) throw new IllegalActionException("The game doesn't exist on the server");
        return game;
    }

    private Player getPlayer(Game game, int playerId, boolean throwExceptionIfNotFound) {
        Player player = game.getPlayerWithId(playerId);
        if (player == null && throwExceptionIfNotFound) throw new IllegalStateException("Can't found player with id " + playerId);
        return player;
    }



}
