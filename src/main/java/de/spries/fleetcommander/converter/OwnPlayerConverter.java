package de.spries.fleetcommander.converter;

import de.spries.fleetcommander.dto.OwnPlayerDto;
import de.spries.fleetcommander.model.Player;

public class OwnPlayerConverter {

    public static OwnPlayerDto convert(Player player) {
        if (player == null) return null;
        return new OwnPlayerDto(
                player.getName(),
                player.getStatus(),
                player.isHumanPlayer(),
                player.getCredits(),
                player.canAffordFactory()
        );
    }


}
