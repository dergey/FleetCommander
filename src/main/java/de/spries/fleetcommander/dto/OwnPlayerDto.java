package de.spries.fleetcommander.dto;

import de.spries.fleetcommander.enums.PlayerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OwnPlayerDto extends OtherPlayerDto {

    private int credits;
    private boolean canAffordFactory;

    public OwnPlayerDto(String name, PlayerStatus status, boolean humanPlayer, int credits, boolean canAffordFactory) {
        super(name, status, humanPlayer);
        this.credits = credits;
        this.canAffordFactory = canAffordFactory;
    }

}
