package domain;

import java.util.List;

public class PlayerCards extends Cards implements Drawable {

    private static final int MAX_SCORE = 21;

    public PlayerCards(Player player, List<Card> cards) {
        super(player, cards);
    }

    @Override
    public boolean canDraw() {
        return sum() <= MAX_SCORE;
    }

    public Name getPlayerName() {
        return ((Player) participant).getName();
    }
}
