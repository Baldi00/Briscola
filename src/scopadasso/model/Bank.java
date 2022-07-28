package scopadasso.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Card> cards;
    private final List<Card> mop;

    public Bank() {
        cards = new ArrayList<>();
        mop = new ArrayList<>();
    }

    public void addToBank(Card card) {
        cards.add(card);
    }

    public void addToMop(Card card) {
        cards.add(card);
        mop.add(card);
    }

    public int getResultPoints() {
        return getBankResultPoints() + mop.size();
    }

    public int getBankResultPoints() {
        int beautifulKing = searchForBeautifulKing();
        int beautifulSeven = searchForBeautifulSeven();
        int coins = searchForCoinsMajority();
        int primiera = searchForPrimiera();
        int cardsNumber = searchForCardsNumberMajority();

        return beautifulKing + beautifulSeven + coins + primiera + cardsNumber;
    }

    public int searchForBeautifulKing() {
        return searchForCard(new Card(CardName.KING, Seed.MONEY));
    }

    public int searchForBeautifulSeven() {
        return searchForCard(new Card(CardName.SEVEN, Seed.MONEY));
    }

    private int searchForCard(Card card) {
        return cards.contains(card) ? 1 : 0;
    }

    public int searchForCoinsMajority() {
        int coinsCounter = countCoins();
        return coinsCounter > 5 ? 1 : 0;
    }

    private int countCoins() {
        int counter = 0;
        for (CardName cardName : CardName.values()) {
            if (cards.contains(new Card(cardName, Seed.MONEY))) {
                counter++;
            }
        }
        return counter;
    }

    public int searchForPrimiera() {
        int sevenNumber = countSeven();
        int sixNumber = countSix();

        if (sevenNumber == 0) return 0;
        if (sevenNumber == 1) return sixNumber < 4 ? 0 : 1;
        if (sevenNumber == 2) return sixNumber < 3 ? 0 : 1;
        if (sevenNumber == 3) return sixNumber < 1 ? 0 : 1;
        if (sevenNumber == 4) return 1;

        throw new IllegalStateException("Bank: There are too many seven");
    }

    private int countSeven() {
        int counter = 0;
        for (Seed seed : Seed.values()) {
            if (cards.contains(new Card(CardName.SEVEN, seed))) {
                counter++;
            }
        }
        return counter;
    }

    private int countSix() {
        int counter = 0;
        for (Seed seed : Seed.values()) {
            if (cards.contains(new Card(CardName.SIX, seed))) {
                counter++;
            }
        }
        return counter;
    }

    public int searchForCardsNumberMajority() {
        return cards.size() > 20 ? 1 : 0;
    }

    public List<Card> getBankWithoutMop() {
        List<Card> bankWithoutMop = new ArrayList<>();

        for (Card card : cards) {
            if (!mop.contains(card)) {
                bankWithoutMop.add(card);
            }
        }

        return bankWithoutMop;
    }

    public List<Card> getMop() {
        return mop;
    }
}
