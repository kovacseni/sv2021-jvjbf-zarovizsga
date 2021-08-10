package org.training360.finalexam.players;

import lombok.*;
import org.training360.finalexam.teams.Team;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_name", nullable = false)
    private String name;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private PositionType positionType;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Team team;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, LocalDate birthDate, PositionType positionType, Team team) {
        this.name = name;
        this.birthDate = birthDate;
        this.positionType = positionType;
        this.team = team;
    }
}
