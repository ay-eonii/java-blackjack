package domain.score;

import domain.player.card.Cards;
import domain.player.card.DealerCards;
import domain.player.card.PlayerCards;

import static domain.score.Outcome.*;

public class Referee {

    private static final int STANDARD = 21;

    public Outcome decideResult(DealerCards dealer, PlayerCards player) {
        return judgeOutcome(dealer, player);
    }

    private Outcome judgeOutcome(DealerCards dealer, PlayerCards player) {
        if (isBust(player)) {
            return LOSE;
        }
        if (isBlackJack(player)) {
            return decidePlayerBlackjack(dealer);
        }
        if (isBust(dealer)) {
            return WIN;
        }
        return compare(dealer, player);
    }

    private Outcome decidePlayerBlackjack(DealerCards dealer) {
        if (isBlackJack(dealer)) {
            return TIE;
        }
        return BLACKJACK;
    }

    private Outcome compare(DealerCards dealer, PlayerCards playerCards) {
        int dealerSum = dealer.bestSum();
        int playerSum = playerCards.bestSum();

        if (dealerSum < playerSum) {
            return Outcome.WIN;
        }
        if (dealerSum > playerSum) {
            return Outcome.LOSE;
        }
        return Outcome.TIE;
    }

    private boolean isBlackJack(Cards cards) {
        return cards.sumInitCards() == STANDARD;
    }

    private boolean isBust(Cards cards) {
        return cards.bestSum() > STANDARD;
    }
}
