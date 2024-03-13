package view;

import domain.card.Cards;
import domain.card.DealerCards;
import domain.card.PlayerCards;
import domain.player.Name;
import domain.score.Revenue;
import domain.score.ScoreBoard;

import java.util.List;
import java.util.Map;

public class OutputView {

    public void printInitialCards(DealerCards dealerCards, List<PlayerCards> playerCards) {
        List<String> names = playerCards.stream()
                .map(playerCard -> playerCard.getPlayerName().toString())
                .toList();

        System.out.println();
        System.out.println("딜러와 " + String.join(", ", names) + "에게 2장을 나누었습니다.");

        String firstCard = dealerCards.getFirstCard();
        System.out.print("딜러: " + firstCard);

        for (PlayerCards playerCard : playerCards) {
            System.out.println();
            printPlayerCards(playerCard);
        }
        System.out.println();
    }

    public void printPlayerCards(PlayerCards cards) {
        Name playerName = cards.getPlayerName();
        System.out.print(playerName + "카드: " + formatCards(cards));
    }

    private String formatCards(Cards cards) {
        return String.join(", ", cards.getCards());
    }

    public void printResults(DealerCards dealerCards, List<PlayerCards> playerCards) {
        System.out.println();
        printDealerCards(dealerCards);
        printSumOfCards(dealerCards);
        for (PlayerCards playerCard : playerCards) {
            printPlayerCards(playerCard);
            printSumOfCards(playerCard);
        }
    }

    private void printDealerCards(DealerCards cards) {
        System.out.print("딜러 카드: ");
        System.out.print(formatCards(cards));
    }

    private void printSumOfCards(Cards cards) {
        System.out.println(" - 결과: " + cards.bestSum());
    }

    public void printScores(ScoreBoard scoreBoard) {
        System.out.println();
        System.out.println("## 최종 승패");
        System.out.print("딜러: ");
        printDealerScore(scoreBoard);

        Map<Name, Revenue> playerScore = scoreBoard.getPlayersRevenues();
        playerScore.forEach((name, revenue) -> System.out.println(name + ": " + revenue.getAmount()));
    }

    private void printDealerScore(ScoreBoard scoreBoard) {
        Revenue dealerRevenue = scoreBoard.calculateDealerRevenue();
        System.out.println(dealerRevenue.getAmount());
    }

    public void printDealerGivenCard() {
        System.out.println();
        System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다.");
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
