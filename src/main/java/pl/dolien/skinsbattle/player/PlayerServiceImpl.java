package pl.dolien.skinsbattle.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dolien.skinsbattle.exception.PlayerNotFoundException;
import pl.dolien.skinsbattle.player.dto.PlayerRequest;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;

import java.util.List;

import static pl.dolien.skinsbattle.player.PlayerMapper.*;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public List<PlayerResponse> getAllPlayers() {
        return toPlayerResponseList(playerRepository.findAll());
    }

    public PlayerResponse getPlayerById(Long id) {
        return toPlayerResponse(playerRepository.findById(id).orElseThrow(
                () -> new PlayerNotFoundException("Player not found")
        ));
    }

    public PlayerResponse createPlayer(PlayerRequest request) {
        return toPlayerResponse(playerRepository.save(toPlayer(request)));
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
