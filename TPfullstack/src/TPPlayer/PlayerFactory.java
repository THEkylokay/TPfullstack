package TPPlayer;

public class PlayerFactory {
    public static void createApp() {
        PlayerModel model = new PlayerModel();
        PlayerView view = new PlayerView();
        new PlayerController(model, view);
    }
}
