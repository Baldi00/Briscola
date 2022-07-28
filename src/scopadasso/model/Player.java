package scopadasso.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private Bank bank;
    private List<Integer> additionalPoints;
    private Card lastPlayedCard;
    private int roundPoints;
    private int gamePoints;

    public Player() {
        hand = new ArrayList<>();
        bank = new Bank();
        additionalPoints = new ArrayList<>();
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

    public List<Card> getBankWithoutMop() {
        return bank.getBankWithoutMop();
    }

    public List<Card> getMop() {
        return bank.getMop();
    }

    public void addToMop(Card card) {
        bank.addToMop(card);
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

    public List<Integer> getAdditionalPoints() {
        return additionalPoints;
    }

    public void addAdditionalPoints(int points) {
        additionalPoints.add(points);
    }

    public int getTotalAdditionalPoints() {
        int sum = 0;
        for(Integer i : additionalPoints){
            sum += i;
        }
        return sum;
    }

    public void clear() {
        hand = new ArrayList<>();
        bank = new Bank();
        additionalPoints = new ArrayList<>();
        roundPoints = 0;
    }

    public void addGamePoints(int points){
        gamePoints += points;
    }

    public int getGamePoints() {
        return gamePoints;
    }
}
