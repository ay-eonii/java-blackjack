package view;

import domain.card.Cards;
import domain.card.DealerCards;
import domain.card.PlayerCards;
import domain.player.Name;
import domain.score.Revenue;

import java.util.List;
import java.util.Map;

public class OutputView {

    public void printInitialCards(Map<Name, PlayerCards> players, String dealerFirstCard) {
        printDrawNotice(players);

        System.out.print("딜러: " + dealerFirstCard);
        players.forEach((name, player) -> {
            System.out.println();
            printPlayerCards(name, player);
        });
        System.out.println();
    }

    public void printPlayerCards(Name name, PlayerCards player) {
        System.out.print(name + "카드: " + formatCards(player));
    }

    private void printDrawNotice(Map<Name, PlayerCards> players) {
        List<String> names = players.keySet().stream()
                .map(Name::toString)
                .toList();
        System.out.println();
        System.out.println("딜러와 " + String.join(", ", names) + "에게 2장을 나누었습니다.");
    }

    private String formatCards(Cards cards) {
        return String.join(", ", cards.getCards());
    }

    public void printResults(DealerCards dealer, Map<Name, PlayerCards> players) {
        System.out.println();
        printDealerCards(dealer);
        printSumOfCards(dealer);
        players.forEach((name, player) -> {
            printPlayerCards(name, player);
            printSumOfCards(player);
        });
    }

    private void printDealerCards(DealerCards cards) {
        System.out.print("딜러 카드: ");
        System.out.print(formatCards(cards));
    }

    private void printSumOfCards(Cards cards) {
        System.out.println(" - 결과: " + cards.bestSum());
    }

    public void printScores(Revenue dealerRevenue, Map<Name, Revenue> playersRevenues) {
        System.out.println();
        System.out.println("## 최종 승패");

        System.out.print("딜러: ");
        System.out.println(dealerRevenue.getAmount());
        playersRevenues.forEach((name, revenue) ->
                System.out.println(name + ": " + revenue.getAmount())
        );
    }

    public void printDealerGivenCard() {
        System.out.println();
        System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다.");
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
