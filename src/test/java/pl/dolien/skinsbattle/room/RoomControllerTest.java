package pl.dolien.skinsbattle.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;
import pl.dolien.skinsbattle.room.dto.RoomRequest;
import pl.dolien.skinsbattle.room.dto.RoomResponse;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoomControllerTest {

    private static final String ROOM_ID = "1";
    private static final String PLAYER_ID = "1";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private RoomController roomController;

    @Mock
    private RoomServiceImpl roomService;

    private MockMvc mockMvc;
    private RoomRequest roomRequest;
    private RoomResponse roomResponse;
    private PlayerResponse playerResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController).build();

        initData();
    }

    @Test
    void shouldReturnAllRooms() throws Exception {
        when(roomService.getAllRooms()).thenReturn(Collections.singletonList(roomResponse));

        performGetAllRooms()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(roomResponse))));

        verify(roomService, times(1)).getAllRooms();
    }

    @Test
    void shouldReturnRoomById() throws Exception {
        when(roomService.getRoomById(ROOM_ID)).thenReturn(roomResponse);

        performGetRoomById()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(roomResponse)));

        verify(roomService, times(1)).getRoomById(ROOM_ID);
    }

    @Test
    void shouldCreateRoom() throws Exception {
        when(roomService.createRoom(any(RoomRequest.class))).thenReturn(roomResponse);

        performCreateRoom()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(roomResponse)));

        verify(roomService, times(1)).createRoom(any(RoomRequest.class));
    }

    @Test
    void shouldJoinRoom() throws Exception {
        when(roomService.joinRoom(ROOM_ID, PLAYER_ID)).thenReturn(true);

        performJoinRoom()
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(roomService, times(1)).joinRoom(ROOM_ID, PLAYER_ID);
    }

    @Test
    void shouldLeaveRoom() throws Exception {
        when(roomService.leaveRoom(ROOM_ID, PLAYER_ID)).thenReturn(true);

        performLeaveRoom()
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(roomService, times(1)).leaveRoom(ROOM_ID, PLAYER_ID);
    }

    @Test
    void shouldReturnPlayersInRoom() throws Exception {
        when(roomService.getPlayersInRoom(ROOM_ID)).thenReturn(Collections.singletonList(playerResponse));

        performGetPlayersInRoom()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(playerResponse))));

        verify(roomService, times(1)).getPlayersInRoom(ROOM_ID);
    }

    private void initData() {
        roomRequest = RoomRequest.builder()
                .name("testRoom")
                .build();

        roomResponse = RoomResponse.builder()
                .name("testRoom")
                .build();

        playerResponse = PlayerResponse.builder()
                .username("testPlayer")
                .roomId(ROOM_ID)
                .build();
    }

    private ResultActions performGetAllRooms() throws Exception {
        return mockMvc.perform(get("/rooms"));
    }

    private ResultActions performGetRoomById() throws Exception {
        return mockMvc.perform(get("/rooms/" + ROOM_ID));
    }

    private ResultActions performCreateRoom() throws Exception {
        return mockMvc.perform(post("/rooms")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomRequest)));
    }

    private ResultActions performJoinRoom() throws Exception {
        return mockMvc.perform(post("/rooms/" + ROOM_ID + "/join/" + PLAYER_ID));
    }

    private ResultActions performLeaveRoom() throws Exception {
        return mockMvc.perform(post("/rooms/" + ROOM_ID + "/leave/" + PLAYER_ID));
    }

    private ResultActions performGetPlayersInRoom() throws Exception {
        return mockMvc.perform(get("/rooms/" + ROOM_ID + "/players"));
    }
}