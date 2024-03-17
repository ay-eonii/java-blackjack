import domain.game.BlackjackGame;
import manager.Casino;

public class BlackjackApplication {

    public static void main(String[] args) {
        final Casino casino = new Casino();
        final BlackjackGame game = casino.prepare();
        casino.start(game);
    }
}
