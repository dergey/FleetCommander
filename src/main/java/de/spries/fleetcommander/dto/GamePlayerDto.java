package de.spries.fleetcommander.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class GamePlayerDto {

	private int gameId;
	private int playerId;

}