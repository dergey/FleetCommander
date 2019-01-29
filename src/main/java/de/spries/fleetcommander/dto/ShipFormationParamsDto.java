package de.spries.fleetcommander.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipFormationParamsDto {

    private int shipCount;
    private int originPlanetId;
    private int destinationPlanetId;

}
