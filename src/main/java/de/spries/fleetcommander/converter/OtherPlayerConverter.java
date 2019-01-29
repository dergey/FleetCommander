package de.spries.fleetcommander.converter;

import de.spries.fleetcommander.dto.OtherPlayerDto;
import de.spries.fleetcommander.model.Player;

import java.util.List;
import java.util.stream.Collectors;

public class OtherPlayerConverter {

    public static OtherPlayerDto convert(Player player) {
        if (player == null) return null;
        return new OtherPlayerDto(player.getName(), player.getStatus(), player.isHumanPlayer());
    }

    public static List<OtherPlayerDto> convert(List<Player> otherPlayers) {
        return otherPlayers.stream()
                .map(OtherPlayerConverter::convert)
                .collect(Collectors.toList());
    }

}
