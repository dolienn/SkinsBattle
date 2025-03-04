package pl.dolien.skinsbattle.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.dolien.skinsbattle.player.dto.PlayerRequest;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;

import java.util.Collections;
import java.util.List;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Mock
    private PlayerRepository playerRepository;

    private Player player;
    private PlayerResponse playerResponse;
    private PlayerRequest playerRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initData();
    }

    @Test
    void shouldReturnAllPlayers() {
        when(playerRepository.findAll()).thenReturn(Collections.singletonList(player));

        List<PlayerResponse> responses = playerService.getAllPlayers();

        assertEquals(1, responses.size());
        assertEquals(playerResponse.getUsername(), responses.get(0).getUsername());
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnPlayerById() {
        when(playerRepository.findById(1L)).thenReturn(of(player));

        PlayerResponse response = playerService.getPlayerById(1L);

        assertEquals(playerResponse.getUsername(), response.getUsername());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnPlayerEntityById() {
        when(playerRepository.findById(1L)).thenReturn(of(player));

        Player response = playerService.getPlayerEntityById(1L);

        assertEquals(player.getId(), response.getId());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void shouldCreatePlayer() {
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        PlayerResponse response = playerService.createPlayer(playerRequest);

        assertEquals(playerResponse.getUsername(), response.getUsername());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void shouldDeletePlayer() {
        playerService.deletePlayer(1L);

        verify(playerRepository, times(1)).deleteById(1L);
    }

    private void initData() {
        player = Player.builder()
                .id(1L)
                .username("testPlayer")
                .build();

        playerResponse = PlayerResponse.builder()
                .username("testPlayer")
                .build();

        playerRequest = PlayerRequest.builder()
                .username("testPlayer")
                .build();
    }
}