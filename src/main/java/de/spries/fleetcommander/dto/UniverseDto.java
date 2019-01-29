package de.spries.fleetcommander.dto;

import de.spries.fleetcommander.model.universe.ShipFormation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class UniverseDto {

    private Collection<PlanetDto> planets;
    private PlanetDto homePlanet;
    private Collection<ShipFormation> travellingShipFormations;

}
