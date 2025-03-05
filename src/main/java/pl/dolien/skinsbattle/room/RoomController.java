package pl.dolien.skinsbattle.room;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;
import pl.dolien.skinsbattle.room.dto.RoomRequest;
import pl.dolien.skinsbattle.room.dto.RoomResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomServiceImpl roomServiceImpl;

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomServiceImpl.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable String id) {
        return roomServiceImpl.getRoomById(id);
    }

    @PostMapping
    public RoomResponse createRoom(@RequestBody RoomRequest request) {
        return roomServiceImpl.createRoom(request);
    }

    @PostMapping("/{roomId}/join/{playerId}")
    public boolean joinRoom(@PathVariable String roomId, @PathVariable String playerId) {
        return roomServiceImpl.joinRoom(roomId, playerId);
    }

    @PostMapping("/{roomId}/leave/{playerId}")
    public boolean leaveRoom(@PathVariable String roomId, @PathVariable String playerId) {
        return roomServiceImpl.leaveRoom(roomId, playerId);
    }

    @GetMapping("/{roomId}/players")
    public List<PlayerResponse> getPlayersInRoom(@PathVariable String roomId) {
        return roomServiceImpl.getPlayersInRoom(roomId);
    }
}
