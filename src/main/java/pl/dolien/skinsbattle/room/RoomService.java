package pl.dolien.skinsbattle.room;

import pl.dolien.skinsbattle.player.dto.PlayerResponse;
import pl.dolien.skinsbattle.room.dto.RoomRequest;
import pl.dolien.skinsbattle.room.dto.RoomResponse;

import java.util.List;

public interface RoomService {

    List<RoomResponse> getAllRooms();
    RoomResponse getRoomById(Long id);
    RoomResponse createRoom(RoomRequest request);
    boolean joinRoom(Long roomId, Long playerId);
    boolean leaveRoom(Long roomId, Long playerId);
    List<PlayerResponse> getPlayersInRoom(Long roomId);
}

