package de.spries.fleetcommander.service;

import de.spries.fleetcommander.model.Game;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameStoreService {

	private Map<Integer, Game> gameStore;
	private int nextGameId;

	private GameStoreService() {
		reset();
	}

	public synchronized int create(Game game) {
		gameStore.put(nextGameId, game);
		return nextGameId++;
	}

	public Game get(int id) {
		return gameStore.get(id);
	}

	public void delete(int id) {
		gameStore.remove(id);
	}

	protected Collection<Game> getGames() {
		return gameStore.values();
	}

	protected void reset() {
		gameStore = new ConcurrentHashMap<>();
		nextGameId = 1;
	}
}
