package de.spries.fleetcommander.converter;

import de.spries.fleetcommander.dto.PlanetDto;
import de.spries.fleetcommander.model.Player;
import de.spries.fleetcommander.model.universe.Planet;

import java.util.List;
import java.util.stream.Collectors;

public class PlanetConverter {

    protected static List<PlanetDto> convert(List<Planet> planets, Player player) {
        return planets.stream().map(p -> convert(p, player)).collect(Collectors.toList());
    }

    public static PlanetDto convert(Planet planet, Player player) {
        if (planet != null) {
            boolean inhabitedByMe = planet.isInhabitedBy(player);

            PlanetDto planetDto = new PlanetDto();
            planetDto.setId(planet.getId());
            planetDto.setUnderAttack(false);
            planetDto.setJustInhabited(false);
            planetDto.setPlanetClass("?");
            planetDto.setKnownAsEnemyPlanet(planet.isKnownAsEnemyPlanet(player));
            planetDto.setIncomingShipCount(planet.getIncomingShipCount(player));
            planetDto.setShipCount(0);
            planetDto.setX(planet.getX());
            planetDto.setInhabitedByMe(inhabitedByMe);
            planetDto.setY(planet.getY());

            if (inhabitedByMe) {
                planetDto.setFactorySite(planet.getFactorySite());
                planetDto.setUnderAttack(planet.isUnderAttack());
                planetDto.setJustInhabited(planet.isJustInhabited());
                planetDto.setPlanetClass(planet.getPlanetClass().name());
                planetDto.setShipCount(planet.getShipCount());
            }

            return planetDto;
        } else {
            return null;
        }
    }

}
