package domain.score;

import domain.player.card.Card;
import domain.player.card.DealerCards;
import domain.player.card.PlayerCards;
import domain.player.card.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Nested
class RefereeTest {

    private PlayerCards playerCards;

    @Nested
    @DisplayName("딜러가 블랙잭이 아니다.")
    class DealerNotBlackjackTest {

        private final DealerCards dealerCards = new DealerCards(List.of(
                new Card(9, Shape.CLUB),
                new Card(10, Shape.SPADE)
        ));
        private final Referee referee = new Referee();

        @Test
        @DisplayName("플레이어만 블랙잭이면 블랙잭이다.")
        void decideResult_PlayerBlackjack_Blackjack() {
            playerCards = new PlayerCards(List.of(
                    new Card(1, Shape.HEART),
                    new Card(10, Shape.DIAMONDS)
            ));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.BLACKJACK);
        }

        @Test
        @DisplayName("플레이어 카드의 합이 딜러 카드의 합보다 크다면 승리다.")
        void decideResult_HigherThanDealer_Win() {
            playerCards = new PlayerCards(List.of(
                    new Card(10, Shape.HEART),
                    new Card(10, Shape.DIAMONDS)
            ));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.WIN);
        }

        @Test
        @DisplayName("플레이어 카드 합과 딜러 카드 합이 같다면 무승부다.")
        void decideResult_SameDealer_Tie() {
            playerCards = new PlayerCards(List.of(
                    new Card(9, Shape.HEART),
                    new Card(10, Shape.DIAMONDS)
            ));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.TIE);
        }

        @Test
        @DisplayName("플레이어 카드 합이 딜러 카드 합보다 작다면 진다.")
        void decideResult_LessThanDealer_Lose() {
            playerCards = new PlayerCards(List.of(
                    new Card(8, Shape.HEART),
                    new Card(10, Shape.DIAMONDS)
            ));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.LOSE);
        }

        @Test
        @DisplayName("플레이어 카드 합이 21을 초과하면 플레이어는 베팅금액을 잃는다.")
        void decideResult_Bust_Lose() {
            playerCards = new PlayerCards(List.of(
                    new Card(8, Shape.HEART),
                    new Card(10, Shape.DIAMONDS)
            ));
            playerCards.receive(new Card(4, Shape.CLUB));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.LOSE);
        }

        @Test
        @DisplayName("플레이어가 블랙잭이면서 21을 초과한 경우는 베팅금액을 잃는다.")
        void decideResult_BlackjackAndBust_Lose() {
            PlayerCards playerCards = new PlayerCards(List.of(
                    new Card(1, Shape.HEART),
                    new Card(10, Shape.CLUB)
            ));
            playerCards.receive(new Card(5, Shape.SPADE));
            playerCards.receive(new Card(6, Shape.SPADE));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.LOSE);
        }
    }

    @Nested
    @DisplayName("딜러가 블랙잭이다.")
    class DealerBlackjackTest {
        private final DealerCards dealerCards = new DealerCards(List.of(
                new Card(1, Shape.CLUB),
                new Card(10, Shape.SPADE)
        ));
        private final Referee referee = new Referee();

        @Test
        @DisplayName("딜러와 플레이어가 모두 블랙잭이라면 무승부다.")
        void decideResult_BothBlackJack_Tie() {
            playerCards = new PlayerCards(List.of(
                    new Card(1, Shape.HEART),
                    new Card(10, Shape.DIAMONDS)
            ));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.TIE);
        }

        @Test
        @DisplayName("플레이어 카드 합이 딜러와 같으면 무승부다.")
        void decideResult_SameDealer_Tie() {
            PlayerCards playerCards = new PlayerCards(List.of(
                    new Card(9, Shape.HEART),
                    new Card(10, Shape.CLUB)
            ));
            playerCards.receive(new Card(2, Shape.SPADE));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.TIE);
        }
    }

    @Nested
    @DisplayName("딜러 카드의 합이 21을 초과한다.")
    class DealerBustTest {

        private final DealerCards dealerCards = new DealerCards(List.of(
                new Card(9, Shape.HEART),
                new Card(10, Shape.CLUB)
        ));
        private final Referee referee = new Referee();

        @BeforeEach
        void setUp() {
            dealerCards.receive(new Card(3, Shape.SPADE));
        }

        @Test
        @DisplayName("플레이어 카드 합이 21을 초과하지 않으면 승리다.")
        void decideResult_LessThan21_Win() {
            PlayerCards playerCards = new PlayerCards(List.of(
                    new Card(9, Shape.HEART),
                    new Card(10, Shape.CLUB)
            ));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.WIN);
        }

        @Test
        @DisplayName("플레이어 카드 합이 21을 초과하면 베팅금액을 잃는다.")
        void decideResult_Bust_Lose() {
            PlayerCards playerCards = new PlayerCards(List.of(
                    new Card(9, Shape.HEART),
                    new Card(10, Shape.CLUB)
            ));
            playerCards.receive(new Card(3, Shape.SPADE));

            Outcome outcome = referee.decideResult(dealerCards, playerCards);

            assertThat(outcome).isEqualTo(Outcome.LOSE);
        }
    }
}
