package de.spries.fleetcommander.converter;

import de.spries.fleetcommander.dto.GameDto;
import de.spries.fleetcommander.model.Game;
import de.spries.fleetcommander.model.Player;

public class GameConverter {

    public static GameDto convert(Game game, Player player) {
        if (game != null && player != null) {
            GameDto gameDto = new GameDto();
            gameDto.setId(game.getId());
            gameDto.setUniverse(UniverseConverter.convert(game.getUniverse(), player));
            gameDto.setOtherPlayers(
                    OtherPlayerConverter.convert(
                            Player.filterAllOtherPlayers(game.getPlayers(), player)
                    )
            );
            gameDto.setTurnNumber(game.getTurnNumber());
            gameDto.setStatus(game.getStatus());
            gameDto.setMe(OwnPlayerConverter.convert(player));
            gameDto.setPreviousTurnEvents(TurnEventsConverter.convert(game.getPreviousTurnEvents(), player));
            return gameDto;
        } else {
            return null;
        }
    }

}
