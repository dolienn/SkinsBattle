package pl.dolien.skinsbattle.player.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponse {

    private String username;
    private String roomId;
}
