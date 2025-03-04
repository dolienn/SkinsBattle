package pl.dolien.skinsbattle.room;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.dolien.skinsbattle.exception.RoomNotFoundException;
import pl.dolien.skinsbattle.player.Player;
import pl.dolien.skinsbattle.player.PlayerMapper;
import pl.dolien.skinsbattle.player.PlayerService;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;
import pl.dolien.skinsbattle.room.dto.RoomRequest;
import pl.dolien.skinsbattle.room.dto.RoomResponse;

import java.util.List;
import java.util.stream.Collectors;

import static pl.dolien.skinsbattle.room.RoomMapper.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PlayerService playerService;
    private final SimpMessagingTemplate messagingTemplate;

    public List<RoomResponse> getAllRooms() {
        return toRoomResponseList(roomRepository.findAll());
    }

    public RoomResponse getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(RoomMapper::toRoomResponse)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    public RoomResponse createRoom(RoomRequest request) {
        Room room = toRoom(request);
        return toRoomResponse(roomRepository.save(room));
    }

    @Transactional
    public boolean joinRoom(Long roomId, Long playerId) {
        Room room = getRoomEntityById(roomId);
        Player player = playerService.getPlayerEntityById(playerId);

        leaveCurrentRoomIfExists(player);

        room.addPlayer(player);
        roomRepository.save(room);
        notifyRoomUpdate(roomId);

        return true;
    }

    @Transactional
    public boolean leaveRoom(Long roomId, Long playerId) {
        Room room = getRoomEntityById(roomId);
        Player player = playerService.getPlayerEntityById(playerId);

        room.removePlayer(player);
        roomRepository.save(room);
        notifyRoomUpdate(roomId);

        return true;
    }

    public List<PlayerResponse> getPlayersInRoom(Long roomId) {
        Room room = getRoomEntityById(roomId);
        return room.getPlayers().stream()
                .map(PlayerMapper::toPlayerResponse)
                .toList();
    }

    private Room getRoomEntityById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    private void leaveCurrentRoomIfExists(Player player) {
        if (player.getRoomId() != null) {
            leaveRoom(player.getRoomId(), player.getId());
        }
    }

    private void notifyRoomUpdate(Long roomId) {
        List<String> playerUsernames = roomRepository.findById(roomId)
                .map(room -> room.getPlayers().stream()
                        .map(Player::getUsername)
                        .collect(Collectors.toList()))
                .orElse(List.of());

        messagingTemplate.convertAndSend("/topic/room/" + roomId, playerUsernames);
    }
}
