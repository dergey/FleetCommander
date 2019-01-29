package de.spries.fleetcommander.dto;

import de.spries.fleetcommander.enums.PlayerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherPlayerDto {

    private String name;
    private PlayerStatus status;
    private boolean humanPlayer;

}
