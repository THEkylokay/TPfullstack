package TPPlayer;
import java.util.ArrayList;
import java.util.List;

public class PlayerModel {
    private List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void updatePlayer(int index, Player p) {
        if (index >= 0 && index < players.size()) {
            players.set(index, p);
        }
    }

    public void removePlayer(int index) {
        if (index >= 0 && index < players.size()) {
            players.remove(index);
        }
    }
}
