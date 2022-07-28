package briscola.model;

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
        return GameManager.calculatePoints(cards);
    }

    public List<Card> getCards() {
        return cards;
    }
}
