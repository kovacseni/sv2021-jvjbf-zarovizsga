package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.training360.finalexam.players.PositionType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAndAddPlayerToTeamCommand {

    @NotBlank
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private PositionType positionType;

    @NotNull
    private Long teamId;
}
