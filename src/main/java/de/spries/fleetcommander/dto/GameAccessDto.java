package de.spries.fleetcommander.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameAccessDto {

    private String fullAuthToken;
    private int gameId;

}
