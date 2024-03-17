package domain.score;

import domain.player.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreBoardTest {

    @Test
    @DisplayName("딜러의 수익을 계산한다.")
    void calculateDealerRevenue() {
        Name tenny = new Name("tenny");
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.updatePlayerScore(tenny, new Bet(20000), Outcome.LOSE);

        assertThat(scoreBoard.calculateDealerRevenue()).isEqualTo(new Revenue(20000));
    }
}
