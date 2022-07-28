package briscola.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private Bank bank;
    private Card lastPlayedCard;
    private int roundPoints;
    private int gamePoints;

    public Player() {
        hand = new ArrayList<>();
        bank = new Bank();
        roundPoints = 0;
        gamePoints = 0;
    }

    /**
     * Removes the card from the hand
     *
     * @param card the card you want to play
     * @throws IllegalStateException if player doesn't have the given card
     */
    public void playCard(Card card) {
        if (!hand.contains(card))
            throw new IllegalStateException("Player doesn't have " + card);

        hand.remove(card);
        lastPlayedCard = card;
    }

    public void receiveCard(Card card) {
        if (hand.size() >= 3)
            throw new IllegalStateException("Trying to add card to a player that has already 3 cards");
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addToBank(Card card) {
        bank.addToBank(card);
    }

    public Card getLastPlayedCard() {
        return lastPlayedCard;
    }

    public int getRoundPoints() {
        return roundPoints;
    }

    public void setRoundPoints(int roundPoints) {
        this.roundPoints = roundPoints;
    }

    public Bank getBank() {
        return bank;
    }

    public List<Card> getBankCards() {
        return bank.getCards();
    }

    public void clear() {
        hand = new ArrayList<>();
        bank = new Bank();
        roundPoints = 0;
    }

    public void addGamePoint(){
        gamePoints++;
    }

    public int getGamePoints() {
        return gamePoints;
    }

    public boolean hasWon() {
        return roundPoints > 60;
    }
}
