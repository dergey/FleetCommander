package de.spries.fleetcommander.service;

import de.spries.fleetcommander.model.Player;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameAuthenticatorService {

    private Map<Player, String> gamePlayerTokens;

    private GameAuthenticatorService() {
        reset();
    }

    public synchronized String createAuthToken(Player player) {
        if (gamePlayerTokens.containsKey(player)) {
            throw new IllegalArgumentException("There already is a token for this player!");
        }
        String token = UUID.randomUUID().toString();
        gamePlayerTokens.put(player, token);
        return token;
    }

    public void deleteAuthToken(Player player) {
        if (!gamePlayerTokens.containsKey(player)) {
            throw new IllegalArgumentException("There is no token for this game and player!");
        }
        gamePlayerTokens.remove(player);
    }

    public boolean isAuthTokenValid(Player player, String token) {
        return StringUtils.equals(token, gamePlayerTokens.get(player));
    }

    protected void reset() {
        gamePlayerTokens = new ConcurrentHashMap<>();
    }
}
