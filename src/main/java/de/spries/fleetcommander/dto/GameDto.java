package de.spries.fleetcommander.dto;

import de.spries.fleetcommander.enums.GameStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GameDto {

    private int id;
    private UniverseDto universe;
    private int turnNumber;
    private GameStatus status;
    private List<OtherPlayerDto> otherPlayers;
    private TurnEventsDto previousTurnEvents;
    private OwnPlayerDto me;

}

