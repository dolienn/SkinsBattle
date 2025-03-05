package pl.dolien.skinsbattle.room;

import jakarta.persistence.*;
import lombok.*;
import pl.dolien.skinsbattle.player.Player;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "roomId")
    private Set<Player> players = new HashSet<>();

    public void addPlayer(Player player) {
        players.add(player);
        player.setRoomId(this.id);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setRoomId(null);
    }
}
