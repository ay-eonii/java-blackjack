package domain.game;

import domain.player.Name;
import domain.player.Names;
import domain.player.Players;
import domain.player.card.DealerCards;
import domain.player.card.Deck;
import domain.player.card.PlayerCards;
import domain.score.ScoreBoard;

import java.util.Map;

public class BlackjackGame {

    private static final Deck deck = new Deck();

    private final Players players;
    private final DealerCards dealer;

    private BlackjackGame(Players players, DealerCards dealer) {
        this.players = players;
        this.dealer = dealer;
    }

    public static BlackjackGame from(Names names) {
        DealerCards dealer = new DealerCards(deck.drawTwoCards());
        Players players = Players.from(names, deck);
        return new BlackjackGame(players, dealer);
    }

    public void payout(ScoreBoard scoreBoard) {
        players.updateScore(dealer, scoreBoard);
    }

    public void drawPlayerCards(Name name) {
        players.drawPlayerCards(name, deck.draw());
    }

    public void drawDealerCards() {
        dealer.receive(deck.draw());
    }

    public boolean dealerCanDraw() {
        return dealer.canDraw();
    }

    public boolean playerCanDraw(Name name) {
        return players.canDraw(name);
    }

    public PlayerCards player(Name name) {
        return players.playerCards(name);
    }

    public Map<Name, PlayerCards> players() {
        return players.players();
    }

    public DealerCards dealer() {
        return dealer;
    }

    public String dealerFirstCard() {
        return dealer.getFirstCard();
    }
}
