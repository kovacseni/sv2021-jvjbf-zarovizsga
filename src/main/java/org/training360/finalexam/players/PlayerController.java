package org.training360.finalexam.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlayerDTO> listPlayers(@RequestParam Optional<String> prefix) {
        return service.listPlayers(prefix);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO createPlayer(@Valid @RequestBody CreatePlayerCommand command) {
        return service.createPlayer(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(@PathVariable("id") long id) {
        service.deletePlayer(id);
    }
}
