package de.spries.fleetcommander.dto;

import de.spries.fleetcommander.model.universe.FactorySite;
import de.spries.fleetcommander.model.universe.HasCoordinates;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanetDto implements HasCoordinates {

    private int id;
    private boolean myHomePlanet;
    private FactorySite factorySite;
    private boolean underAttack;
    private boolean justInhabited;
    private String planetClass;
    private boolean knownAsEnemyPlanet;
    private int incomingShipCount;
    private int shipCount;
    private int x;
    private boolean inhabitedByMe;
    private int y;

}
