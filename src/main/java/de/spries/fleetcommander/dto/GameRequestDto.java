package de.spries.fleetcommander.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameRequestDto {

    @JsonProperty(value = "playerName", required = true)
    private String playerName;
    @JsonProperty(value = "joinCode", required = false)
    private String joinCode;

}
