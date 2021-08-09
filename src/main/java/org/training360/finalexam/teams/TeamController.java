package org.training360.finalexam.teams;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.training360.finalexam.players.PlayerDTO;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @GetMapping
    public List<TeamDTO> listTeams(@RequestParam Optional<String> prefix) {
        return service.listTeams(prefix);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO createTeam(@Valid @RequestBody CreateTeamCommand command) {
        return service.createTeam(command);
    }

    @PostMapping("/{id}/players")
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDTO createAndAddPlayerToTeam(@PathVariable("id") long id, @Valid @RequestBody CreateAndAddPlayerToTeamCommand command) {
        return service.createAndAddPlayerToTeam(id, command);
    }

    @PutMapping("/{id}/players")
    public TeamDTO addExistingPlayerToTeam(@PathVariable("id") long id, UpdateWithExistingPlayerCommand command) {
        return service.addExistingPlayerToTeam(id, command);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException iae) {
        Problem problem =
                Problem.builder()
                        .withType(URI.create("teams/not-found"))
                        .withTitle("Not found")
                        .withStatus(Status.NOT_FOUND)
                        .withDetail(iae.getMessage())
                        .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }
}
