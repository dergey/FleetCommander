package de.spries.fleetcommander.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurnEventsDto {

    private boolean hasEvents;
    private int conqueredEnemyPlanets;
    private int conqueredUninhabitedPlanets;
    private int lostShipFormations;
    private int defendedPlanets;
    private int lostPlanets;

}
