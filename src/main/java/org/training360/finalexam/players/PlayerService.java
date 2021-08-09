package org.training360.finalexam.players;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private PlayerRepository repository;
    private ModelMapper modelMapper;

    public PlayerService(PlayerRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<PlayerDTO> listPlayers(Optional<String> prefix) {
        Type targetListType = new TypeToken<List<PlayerDTO>>() {}.getType();
        List<Player> filteredPlayers = repository.findAll().stream()
                .filter(player -> prefix.isEmpty()
                        || player.getName().toLowerCase().contains(prefix.get().toLowerCase()))
                .collect(Collectors.toList());
        return modelMapper.map(filteredPlayers, targetListType);
    }

    public PlayerDTO createPlayer(CreatePlayerCommand command) {
        Player player = new Player(command.getName());
        if (command.getBirthDate() != null) {
            player.setBirthDate(command.getBirthDate());
        }
        if (command.getPositionType() != null) {
            player.setPositionType(command.getPositionType());
        }

        repository.save(player);
        return modelMapper.map(player, PlayerDTO.class);
    }

    public void deletePlayer(long id) {
        repository.deleteById(id);
    }
}
