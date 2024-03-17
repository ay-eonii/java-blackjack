package domain.score;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RevenueCalculatorTest {

    @Test
    @DisplayName("블랙잭이라면 수익은 베팅금액의 1.5배다.")
    void calculate_Blackjack_isOneAndHalfTimesOfBet() {
        Bet bet = new Bet(20000);

        Revenue revenue = RevenueCalculator.calculate(Outcome.BLACKJACK, bet);

        assertThat(revenue).isEqualTo(new Revenue(30000));
    }

    @Test
    @DisplayName("이기면 수익은 베팅금액의 1배다.")
    void calculate_Win_isSameWithBet() {
        Bet bet = new Bet(20000);

        Revenue revenue = RevenueCalculator.calculate(Outcome.WIN, bet);

        assertThat(revenue).isEqualTo(new Revenue(20000));
    }

    @Test
    @DisplayName("지면 수익은 베팅금액의 -1배이다.")
    void calculate_Lose_isMinus() {
        Bet bet = new Bet(20000);

        Revenue revenue = RevenueCalculator.calculate(Outcome.LOSE, bet);

        assertThat(revenue).isEqualTo(new Revenue(-20000));
    }

    @Test
    @DisplayName("비기면 수익은 0이다.")
    void calculate_Return_isZero() {
        Bet bet = new Bet(20000);

        Revenue revenue = RevenueCalculator.calculate(Outcome.TIE, bet);

        assertThat(revenue).isEqualTo(new Revenue(0));
    }
}