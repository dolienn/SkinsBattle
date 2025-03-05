package pl.dolien.skinsbattle.player;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dolien.skinsbattle.player.dto.PlayerRequest;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final PlayerServiceImpl playerServiceImpl;

    @GetMapping
    public List<PlayerResponse> getAllPlayers() {
        return playerServiceImpl.getAllPlayers();
    }

    @GetMapping("/{id}")
    public PlayerResponse getPlayerById(@PathVariable String id) {
        return playerServiceImpl.getPlayerById(id);
    }

    @PostMapping
    public PlayerResponse createPlayer(@RequestBody PlayerRequest request) {
        return playerServiceImpl.createPlayer(request);
    }

    @DeleteMapping("/{id}")
    public void deletePlayer(@PathVariable String id) {
        playerServiceImpl.deletePlayer(id);
    }
}
