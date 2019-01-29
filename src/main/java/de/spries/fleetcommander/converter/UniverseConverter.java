package de.spries.fleetcommander.converter;

import de.spries.fleetcommander.dto.UniverseDto;
import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.universe.ShipFormation;
import de.spries.fleetcommander.model.universe.Universe;

public class UniverseConverter {

    public static UniverseDto convert(Universe universe, Player player) {
        if (universe != null && player != null) {
            UniverseDto universeDto = new UniverseDto();
            universeDto.setPlanets(PlanetConverter.convert(universe.getPlanets(), player));
            universeDto.setHomePlanet(PlanetConverter.convert(universe.getHomePlanetOf(player), player));
            universeDto.setTravellingShipFormations(
                    ShipFormation.filterByCommander(universe.getTravellingShipFormations(), player));
            return universeDto;
        } else {
            return null;
        }
    }

}
