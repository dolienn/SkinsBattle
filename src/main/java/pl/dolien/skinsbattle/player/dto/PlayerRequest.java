package pl.dolien.skinsbattle.player.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerRequest {

    private String username;
    private Long roomId;
}
