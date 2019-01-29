package de.spries.fleetcommander.converter;

import de.spries.fleetcommander.dto.TurnEventsDto;
import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.TurnEvents;

public class TurnEventsConverter {

    public static TurnEventsDto convert(TurnEvents events, Player player) {
        if (events != null && player != null) {
            TurnEventsDto turnEventsDto = new TurnEventsDto();
            turnEventsDto.setConqueredEnemyPlanets(events.getConqueredEnemyPlanets(player));
            turnEventsDto.setConqueredUninhabitedPlanets(events.getConqueredUninhabitedPlanets(player));
            turnEventsDto.setLostShipFormations(events.getLostShipFormations(player));
            turnEventsDto.setLostPlanets(events.getLostPlanets(player));
            turnEventsDto.setHasEvents(events.hasEvents(player));
            return turnEventsDto;
        }
        return null;
    }
}
