package org.training360.finalexam.teams;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training360.finalexam.players.Player;
import org.training360.finalexam.players.PlayerRepository;
import org.training360.finalexam.players.PositionType;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private TeamRepository repository;
    private ModelMapper modelMapper;

    @Autowired
    private PlayerRepository playerRepository;

    public TeamService(TeamRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<TeamDTO> listTeams(Optional<String> prefix) {
        Type targetListType = new TypeToken<List<TeamDTO>>() {}.getType();
        List<Team> filteredTeams = repository.findAll().stream()
                .filter(team -> prefix.isEmpty()
                        || team.getName().toLowerCase().contains(prefix.get().toLowerCase()))
                .collect(Collectors.toList());
        return modelMapper.map(filteredTeams, targetListType);
    }

    public TeamDTO createTeam(CreateTeamCommand command) {
        Team team = new Team(command.getName());
        repository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO createAndAddPlayerToTeam(long id, CreateAndAddPlayerToTeamCommand command) {
        Team team = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team with id: " + id + " not found."));
        Player player = new Player(command.getName(), command.getBirthDate(), command.getPositionType(), team);
        team.addPlayer(player);
        playerRepository.save(player);
        repository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    @Transactional
    public TeamDTO addExistingPlayerToTeam(long id, UpdateWithExistingPlayerCommand command) {
        Team team = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team with id: " + id + " not found."));
        Player player = playerRepository.findById(command.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("Player with id: " + command.getPlayerId() + " not found."));
        if (player.getTeam() == null && getNumberOfPlayersInAnExactTeamWithExactPositionType(team, player.getPositionType()) < 2) {
            team.addPlayer(player);
        }
        repository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }

    private int getNumberOfPlayersInAnExactTeamWithExactPositionType(Team team, PositionType positionType) {
        int number = (int) team.getPlayers().stream()
                .filter(player -> player.getPositionType() == positionType)
                .count();
        return number;
    }
}
