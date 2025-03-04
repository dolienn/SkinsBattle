package pl.dolien.skinsbattle.player;

import pl.dolien.skinsbattle.player.dto.PlayerRequest;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;

import java.util.List;

public interface PlayerService {

    List<PlayerResponse> getAllPlayers();
    PlayerResponse getPlayerById(Long id);
    PlayerResponse createPlayer(PlayerRequest request);
    void deletePlayer(Long id);
}
