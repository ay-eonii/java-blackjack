package domain.score;

import domain.player.Name;

import java.util.*;

public class ScoreBoard {

    private final Map<Name, Revenue> playersRevenues = new HashMap<>();

    public Revenue calculateDealerRevenue() {
        List<Revenue> revenues = new ArrayList<>(playersRevenues.values());
        int sum = revenues.stream()
                .mapToInt(Revenue::getAmount)
                .sum();
        return new Revenue(sum * -1);
    }

    public Map<Name, Revenue> getPlayersRevenues() {
        return Collections.unmodifiableMap(playersRevenues);
    }

    public void updatePlayerScore(Name name, Bet bet, Outcome outcome) {
        Revenue revenue = RevenueCalculator.calculate(outcome, bet);
        playersRevenues.put(name, revenue);
    }
}
