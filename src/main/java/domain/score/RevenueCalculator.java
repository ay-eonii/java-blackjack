package domain.score;

import java.util.Arrays;
import java.util.function.Function;

public enum RevenueCalculator {

    EARN(Outcome.WIN, 1),
    LOSE(Outcome.LOSE, -1),
    RETURN(Outcome.TIE, 0),
    BLACKJACK_EARN(Outcome.BLACKJACK, 1.5);

    private final Outcome outcome;
    private final Function<Bet, Revenue> calculate;

    RevenueCalculator(Outcome outcome, double ratio) {
        this.outcome = outcome;
        this.calculate = bet -> new Revenue((int) (bet.getAmount() * ratio));
    }

    public static Revenue calculate(Outcome outcome, Bet bet) {
        return Arrays.stream(values())
                .filter(calculator -> calculator.outcome == outcome)
                .findFirst()
                .map(calculator -> calculator.calculate.apply(bet))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 결과입니다."));
    }
}
