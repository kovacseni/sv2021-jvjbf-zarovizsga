package org.training360.finalexam.players;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.training360.finalexam.teams.TeamDTO;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private PositionType positionType;
    private TeamDTO team;
}
