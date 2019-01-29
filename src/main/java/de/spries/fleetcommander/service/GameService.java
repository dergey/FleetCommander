package de.spries.fleetcommander.service;

import de.spries.fleetcommander.model.Game;
import de.spries.fleetcommander.model.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class GameService {

    private final GameStoreService gameStoreService;
    private final JoinCodesService joinCodesService;

    public Game createNewGame(Player player) {
        Game game = new Game();
        game.addPlayer(player);
        int gameId = gameStoreService.create(game);
        game.setId(gameId);
        return game;
    }

    public Game joinGame(Player player, String joinCode) {
        int gameId = joinCodesService.invalidate(joinCode);
        Game game = gameStoreService.get(gameId);
        game.addPlayer(player);
        return game;
    }

    public Game getById(int gameId) {
        return gameStoreService.get(gameId);
    }

}
