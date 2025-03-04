package pl.dolien.skinsbattle.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.dolien.skinsbattle.player.dto.PlayerRequest;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PlayerControllerTest {

    private static final Long PLAYER_ID = 1L;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private PlayerController playerController;

    @Mock
    private PlayerServiceImpl playerService;

    private MockMvc mockMvc;
    private PlayerResponse playerResponse;
    private PlayerRequest playerRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();

        initData();
    }

    @Test
    void shouldReturnAllPlayers() throws Exception {
        when(playerService.getAllPlayers()).thenReturn(Collections.singletonList(playerResponse));

        performGetAllPlayers()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(playerResponse))));

        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    void shouldReturnPlayerById() throws Exception {
        when(playerService.getPlayerById(PLAYER_ID)).thenReturn(playerResponse);

        performGetPlayerById()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(playerResponse)));

        verify(playerService, times(1)).getPlayerById(PLAYER_ID);
    }

    @Test
    void shouldCreatePlayer() throws Exception {
        when(playerService.createPlayer(any(PlayerRequest.class))).thenReturn(playerResponse);

        performCreatePlayer()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(playerResponse)));

        verify(playerService, times(1)).createPlayer(any(PlayerRequest.class));
    }

    @Test
    void shouldDeletePlayer() throws Exception {
        performDeletePlayer()
                .andExpect(status().isOk());

        verify(playerService, times(1)).deletePlayer(PLAYER_ID);
    }

    private ResultActions performDeletePlayer() throws Exception {
        return mockMvc.perform(delete("/players/" + PLAYER_ID));
    }

    private ResultActions performCreatePlayer() throws Exception {
        return mockMvc.perform(post("/players")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(playerRequest)));
    }

    private ResultActions performGetPlayerById() throws Exception {
        return mockMvc.perform(get("/players/" + PLAYER_ID));
    }

    private void initData() {
        playerResponse = PlayerResponse.builder()
                .username("testPlayer")
                .build();

        playerRequest = PlayerRequest.builder()
                .username("testPlayer")
                .build();
    }

    private ResultActions performGetAllPlayers() throws Exception {
        return mockMvc.perform(get("/players"));
    }
}