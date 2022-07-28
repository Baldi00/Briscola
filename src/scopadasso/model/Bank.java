package scopadasso.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Card> cards;

    public Bank() {
        cards = new ArrayList<>();
    }

    public void addToBank(Card card) {
        cards.add(card);
    }

    public int getResultPoints() {
        int points = 0;
        for(Card card : cards) {
            points += switch (card.cardName()) {
                case JACK -> 2;
                case HORSE -> 3;
                case KING -> 4;
                case THREE -> 10;
                case ACE -> 11;
                default -> 0;
            };
        }
        return points;
    }
}
