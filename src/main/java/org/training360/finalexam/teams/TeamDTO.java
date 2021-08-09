package org.training360.finalexam.teams;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.training360.finalexam.players.PlayerDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private Long id;

    private String name;

    @JsonBackReference
    @ToString.Exclude
    private List<PlayerDTO> players;
}
