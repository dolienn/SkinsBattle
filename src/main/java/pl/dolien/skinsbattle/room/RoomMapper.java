package pl.dolien.skinsbattle.room;

import pl.dolien.skinsbattle.room.dto.RoomRequest;
import pl.dolien.skinsbattle.room.dto.RoomResponse;

import java.util.List;

public class RoomMapper {

    private RoomMapper() {}

    public static List<RoomResponse> toRoomResponseList(List<Room> rooms) {
        return rooms.stream()
                .map(RoomMapper::toRoomResponse)
                .toList();
    }

    public static RoomResponse toRoomResponse(Room room) {
        return RoomResponse.builder()
                .name(room.getName())
                .build();
    }

    public static Room toRoom(RoomRequest roomRequest) {
        return Room.builder()
                .name(roomRequest.getName())
                .build();
    }
}
