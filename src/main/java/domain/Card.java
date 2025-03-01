package domain;

public class Card {

    private final CardNumber cardNumber;
    private final Shape shape;

    public Card(int number, Shape shape) {
        this.cardNumber = CardNumber.find(number);
        this.shape = shape;
    }

    public int getCardNumber() {
        return cardNumber.getNumber();
    }

    @Override
    public String toString() {
        return cardNumber.getNumber() + shape.getShape();
    }
}
