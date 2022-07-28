package scopadasso.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        fillDeck();
    }

    private void fillDeck() {
        for (CardName cardName : CardName.values()) {
            for (Seed seed : Seed.values()) {
                cards.add(new Card(cardName, seed));
            }
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
     * Extract a card and removes it from deck
     *
     * @return the extracted card
     * @throws IllegalStateException if deck is empty
     */
    public Card extract() {
        if (cards.isEmpty())
            throw new IllegalStateException("Cannot extract card, the deck is empty");

        Card extracted = cards.get(0);
        cards.remove(0);
        return extracted;
    }

    public int size() {
        return cards.size();
    }
}
