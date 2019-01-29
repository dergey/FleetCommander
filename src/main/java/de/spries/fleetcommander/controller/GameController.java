package de.spries.fleetcommander.controller;

import de.spries.fleetcommander.dto.*;
import de.spries.fleetcommander.faucet.GameFaucet;
import de.spries.fleetcommander.filter.GameAccessTokenFilter;
import de.spries.fleetcommander.service.JoinCodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@RestController
@RequestMapping("api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameFaucet gameFaucet;
    private final JoinCodesService joinCodesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameAccessDto createOrJoinGame(
            @RequestBody GameRequestDto param,
            HttpServletResponse response) {
        if (param.getPlayerName() == null) {
            throw new IllegalStateException("Parameter 'playerName' is required");
        }
        GameAccessDto accessParams = (param.getJoinCode() == null) ?
                gameFaucet.createGame(param.getPlayerName()) :
                gameFaucet.joinGame(param.getPlayerName(), param.getJoinCode());
        response.setHeader("Location", "/api/games/" + + accessParams.getGameId());
        return accessParams;
    }

    @GetMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDto getGame(@PathVariable("id") int gameId, @RequestHeader("Authorization") String auth) {
        int playerId = GameAccessTokenFilter.extractPlayerIdFromHeadersValue(auth);
        return gameFaucet.getGameDto(gameId, playerId);
    }

    @PostMapping("{id}/joinCodes")
    @ResponseStatus(HttpStatus.CREATED)
    public void createJoinCode(@PathVariable("id") int gameId, HttpServletResponse response) {
        joinCodesService.create(gameId);
        response.setHeader("Location", "/api/games/" + gameId + "/joinCodes");
    }

    @GetMapping("{id}/joinCodes")
    @ResponseStatus(HttpStatus.OK)
    public JoinCodesDto getActiveJoinCodes(@PathVariable("id") int gameId) {
        Collection<String> joinCodes = joinCodesService.get(gameId);
        return new JoinCodesDto(joinCodes);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void quitGame(@PathVariable("id") int gameId, @RequestHeader("Authorization") String auth) {
        int playerId = GameAccessTokenFilter.extractPlayerIdFromHeadersValue(auth);
        gameFaucet.quitGame(gameId, playerId);
    }

    @PostMapping("{id}/players")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void addComputerPlayer(@PathVariable("id") int gameId) {
        gameFaucet.addComputerPlayer(gameId);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void startGame(@PathVariable("id") int gameId, @RequestBody GameParamsDto params,
                          @RequestHeader("Authorization") String auth) {
        int playerId = GameAccessTokenFilter.extractPlayerIdFromHeadersValue(auth);
        gameFaucet.modifyGame(gameId, playerId, params);
    }

    @PostMapping("{id}/turns")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void endTurn(@PathVariable("id") int gameId, @RequestHeader("Authorization") String auth) {
        int playerId = GameAccessTokenFilter.extractPlayerIdFromHeadersValue(auth);
        gameFaucet.endTurn(gameId, playerId);
    }

    @PostMapping("{id}/universe/travellingShipFormations")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendShips(@PathVariable("id") int gameId, @RequestBody ShipFormationParamsDto ships,
                          @RequestHeader("Authorization") String auth) {
        int playerId = GameAccessTokenFilter.extractPlayerIdFromHeadersValue(auth);
        gameFaucet.sendShips(gameId, playerId, ships);
    }

    @PostMapping("{id}/universe/planets/{planetId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void modifyPlanet(@PathVariable("id") int gameId, @PathVariable("planetId") int planetId,
                             @RequestBody PlanetParamsDto params, @RequestHeader("Authorization") String auth) {
        int playerId = GameAccessTokenFilter.extractPlayerIdFromHeadersValue(auth);
        gameFaucet.changePlanetProductionFocus(gameId, playerId, planetId, params);
    }

    @PostMapping("{id}/universe/planets/{planetId}/factories")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void buildFactory(@PathVariable("id") int gameId, @PathVariable("planetId") int planetId,
                             @RequestHeader("Authorization") String auth) {
        int playerId = GameAccessTokenFilter.extractPlayerIdFromHeadersValue(auth);
        gameFaucet.buildFactory(gameId, playerId, planetId);
    }

}
