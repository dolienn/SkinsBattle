package pl.dolien.skinsbattle.player;

import pl.dolien.skinsbattle.player.dto.PlayerRequest;
import pl.dolien.skinsbattle.player.dto.PlayerResponse;

import java.util.List;

public class PlayerMapper {

    private PlayerMapper() {}

    public static List<PlayerResponse> toPlayerResponseList(List<Player> players) {
        return players.stream()
                .map(PlayerMapper::toPlayerResponse)
                .toList();
    }

    public static PlayerResponse toPlayerResponse(Player player) {
        return PlayerResponse.builder()
                .username(player.getUsername())
                .roomId(player.getRoomId())
                .build();
    }

    public static Player toPlayer(PlayerRequest request) {
        return Player.builder()
                .username(request.getUsername())
                .roomId(request.getRoomId())
                .build();
    }
}
