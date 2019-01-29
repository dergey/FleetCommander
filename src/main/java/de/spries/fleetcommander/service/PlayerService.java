package de.spries.fleetcommander.service;

import de.spries.fleetcommander.model.Game;
import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.ai.ComputerPlayer;
import de.spries.fleetcommander.model.ai.behavior.AggressiveFleetStrategy;
import de.spries.fleetcommander.model.ai.behavior.DefaultBuildingStrategy;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    public Player createNewPlayer(String playerName) {
        return new Player(playerName);
    }
    
    public Player addComputerPlayer(Game game) {
        long numComputerPlayers = game.getPlayers().stream().filter(p -> !p.isHumanPlayer()).count();
        String name = "Computer " + (numComputerPlayers + 1);
        ComputerPlayer player = new ComputerPlayer(name, new DefaultBuildingStrategy(), new AggressiveFleetStrategy());
        game.addPlayer(player);
        return player;
    }

}
