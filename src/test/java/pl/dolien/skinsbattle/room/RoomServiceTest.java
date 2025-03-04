package pl.dolien.skinsbattle.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pl.dolien.skinsbattle.exception.RoomNotFoundException;
import pl.dolien.skinsbattle.player.Player;
import pl.dolien.skinsbattle.player.PlayerService;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;
import pl.dolien.skinsbattle.room.dto.RoomRequest;
import pl.dolien.skinsbattle.room.dto.RoomResponse;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private PlayerService playerService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    private Room room;
    private RoomResponse roomResponse;
    private RoomRequest roomRequest;
    private Player player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initData();
    }

    @Test
    void shouldReturnAllRooms() {
        when(roomRepository.findAll()).thenReturn(Collections.singletonList(room));

        List<RoomResponse> responses = roomService.getAllRooms();

        assertEquals(1, responses.size());
        assertEquals(roomResponse.getName(), responses.get(0).getName());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnRoomById() {
        when(roomRepository.findById(1L)).thenReturn(of(room));

        RoomResponse response = roomService.getRoomById(1L);

        assertEquals(roomResponse.getName(), response.getName());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowRoomNotFoundException() {
        when(roomRepository.findById(1L)).thenReturn(empty());

        RoomNotFoundException exception = assertThrows(
                RoomNotFoundException.class,
                () -> roomService.getRoomById(1L)
        );

        assertEquals("Room not found", exception.getMessage());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void shouldCreateRoom() {
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        RoomResponse response = roomService.createRoom(roomRequest);

        assertEquals(roomResponse.getName(), response.getName());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void shouldJoinRoom() {
        when(roomRepository.findById(1L)).thenReturn(of(room));
        when(playerService.getPlayerEntityById(1L)).thenReturn(player);

        boolean result = roomService.joinRoom(1L, 1L);

        assertTrue(result);
        assertEquals(1, room.getPlayers().size());
        verify(roomRepository, times(4)).findById(1L);
        verify(playerService, times(2)).getPlayerEntityById(1L);
    }

    @Test
    void shouldLeaveRoom() {
        when(roomRepository.findById(1L)).thenReturn(of(room));
        when(playerService.getPlayerEntityById(1L)).thenReturn(player);

        boolean result = roomService.leaveRoom(1L, 1L);

        assertTrue(result);
        assertEquals(0, room.getPlayers().size());
        verify(roomRepository, times(2)).findById(1L);
        verify(playerService, times(1)).getPlayerEntityById(1L);
    }

    @Test
    void shouldReturnPlayersInRoom() {
        when(roomRepository.findById(1L)).thenReturn(of(room));

        List<PlayerResponse> players = roomService.getPlayersInRoom(1L);

        assertEquals(1, players.size());
        verify(roomRepository, times(1)).findById(1L);
    }

    private void initData() {
        player = Player.builder()
                .id(1L)
                .username("testPlayer")
                .roomId(1L)
                .build();

        room = Room.builder()
                .id(1L)
                .name("testRoom")
                .players(new HashSet<>(List.of(player)))
                .build();

        roomResponse = RoomResponse.builder()
                .name("testRoom")
                .build();

        roomRequest = RoomRequest.builder()
                .name("testRoom")
                .build();
    }
}