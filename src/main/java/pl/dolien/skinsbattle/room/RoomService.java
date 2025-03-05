package pl.dolien.skinsbattle.room;

import pl.dolien.skinsbattle.player.dto.PlayerResponse;
import pl.dolien.skinsbattle.room.dto.RoomRequest;
import pl.dolien.skinsbattle.room.dto.RoomResponse;

import java.util.List;

public interface RoomService {

    List<RoomResponse> getAllRooms();
    RoomResponse getRoomById(String id);
    RoomResponse createRoom(RoomRequest request);
    boolean joinRoom(String roomId, String playerId);
    boolean leaveRoom(String roomId, String playerId);
    List<PlayerResponse> getPlayersInRoom(String roomId);
}

