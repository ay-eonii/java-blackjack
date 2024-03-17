package domain.player;

import domain.player.card.Card;
import domain.player.card.DealerCards;
import domain.player.card.Deck;
import domain.player.card.PlayerCards;
import domain.score.Bet;
import domain.score.Outcome;
import domain.score.Referee;
import domain.score.ScoreBoard;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Players {

    private final Map<Name, PlayerCards> players;
    private final Map<Name, Bet> bets;

    private Players(Map<Name, PlayerCards> players, Map<Name, Bet> bets) {
        this.players = players;
        this.bets = bets;
    }

    public static Players from(Names names, Map<Name, Bet> bets, Deck deck) {
        Map<Name, PlayerCards> players = names.getNames().stream()
                .collect(Collectors.toMap(
                        name -> name,
                        name -> new PlayerCards(deck.drawTwoCards())
                ));
        return new Players(players, bets);
    }

    public void updateScore(DealerCards dealer, ScoreBoard scoreBoard) {
        Referee referee = new Referee();
        players.forEach((name, player) -> {
            Outcome outcome = referee.decideResult(dealer, player);
            scoreBoard.updatePlayerScore(name, bets.get(name), outcome);
        });
    }

    public void drawPlayerCards(Name name, Card card) {
        PlayerCards player = playerCards(name);
        if (player.canDraw()) {
            player.receive(card);
        }
    }

    public boolean canDraw(Name name) {
        PlayerCards player = playerCards(name);
        return player.canDraw();
    }

    public PlayerCards playerCards(Name name) {
        return players.get(name);
    }

    public Map<Name, PlayerCards> players() {
        return Collections.unmodifiableMap(players);
    }
}
