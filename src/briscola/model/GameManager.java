package briscola.model;

import java.util.*;

public class GameManager {
    private final Player humanPlayer;
    private final Player cpuPlayer;
    private Card fieldCard;
    private Deck deck;
    private Card trump;
    private Seed trumpSeed;
    private boolean hasCpuSelectedCard;
    private Player currentPlayer;
    private Player oppositeToCurrentPlayer;
    private Player firstPlayer;

    public GameManager() {
        humanPlayer = new Player();
        cpuPlayer = new Player();
        deck = new Deck();
        hasCpuSelectedCard = false;
        firstPlayer = humanPlayer;
        currentPlayer = humanPlayer;
        oppositeToCurrentPlayer = cpuPlayer;
    }

    public void preparation() {
        deck.shuffleDeck();
        giveThreeCardsToPlayers();
        extractTrump();

        if(firstPlayer == cpuPlayer) {
            cpuPlayerSelectNextCard();
        }
    }

    private void extractTrump() {
        trump = deck.extract();
        trumpSeed = trump.getSeed();
    }

    public void giveThreeCardsToPlayers() {
        for (int i = 0; i < 3; i++) {
            humanPlayer.receiveCard(deck.extract());
            cpuPlayer.receiveCard(deck.extract());
        }
    }

    // CARD PLAY

    public void humanPlayerPlayCard(Card card) {
        if (!humanPlayer.getHand().isEmpty()) {
            humanPlayer.playCard(card);
            executeMove(humanPlayer, card);
        }
    }

    public void cpuPlayerSelectNextCard() {
        Card bestCardToPlay = selectBestCardToPlay(cpuPlayer.getHand());
        if (!cpuPlayer.getHand().isEmpty()) {
            cpuPlayer.playCard(bestCardToPlay);
            hasCpuSelectedCard = true;
        }
    }

    public void executeMove(Player player, Card card) {
        if(fieldCard == null){
            fieldCard = card;
            selectNextPlayer(player, null);
        }else{
            Player playerWhoPicked = giveCardsToTurnWinnerBank(player, card);
            fieldCard = null;
            selectNextPlayer(player, playerWhoPicked);
            if(deck.size() != 0) {
                giveCardsFromDeckToPlayers();
            }
        }

        hasCpuSelectedCard = false;

        if(currentPlayer == cpuPlayer && !isGameOver()) {
            cpuPlayerSelectNextCard();
        }
    }

    private void giveCardsFromDeckToPlayers() {
        currentPlayer.getHand().add(deck.extract());
        if(deck.size() == 0) {
            oppositeToCurrentPlayer.getHand().add(trump);
            trump = null;
        }else{
            oppositeToCurrentPlayer.getHand().add(deck.extract());
        }
    }

    private Player giveCardsToTurnWinnerBank(Player player, Card card) {
        Player otherPlayer = player == humanPlayer ? cpuPlayer : humanPlayer;

        // Same seed, check value
        if(fieldCard.seed().equals(card.seed())) {
            if (card.cardName().equals(CardName.ACE) ||
                    (card.cardName().equals(CardName.THREE) && !fieldCard.cardName().equals(CardName.ACE)) ||
                    (card.cardName().getValue() > fieldCard.cardName().getValue() && !card.cardName().equals(CardName.THREE))) {
                player.addToBank(fieldCard);
                player.addToBank(card);
                return player;
            }

            otherPlayer.addToBank(fieldCard);
            otherPlayer.addToBank(card);
            return otherPlayer;
        }

        // Different seed, but not trump played
        if (!card.seed().equals(trumpSeed)) {
            otherPlayer.addToBank(fieldCard);
            otherPlayer.addToBank(card);
            return otherPlayer;
        }

        //Different seed, trump played
        player.addToBank(fieldCard);
        player.addToBank(card);
        return player;

    }

    public void selectNextPlayer(Player player, Player playerWhoPicked){
        currentPlayer = Objects.requireNonNullElseGet(playerWhoPicked, () -> player == humanPlayer ? cpuPlayer : humanPlayer);
        oppositeToCurrentPlayer = currentPlayer == humanPlayer ? cpuPlayer : humanPlayer;
    }

    // CPU CARD SELECTION

    private Card selectBestCardToPlay(List<Card> hand) {
        if(hand.size() == 1){
            return hand.get(0);
        }

        int [] points = new int[hand.size()];

        if(fieldCard != null) {
            List<Card> toPick = new ArrayList<>(2);
            toPick.add(fieldCard);
            for (int i = 0; i < hand.size(); i++) {
                Card current = hand.get(i);
                if ((current.seed().equals(fieldCard.seed()) &&
                        (current.cardName().equals(CardName.ACE) || current.cardName().equals(CardName.THREE) ||
                                current.cardName().getValue() > fieldCard.cardName().getValue()))
                        || (current.seed().equals(trumpSeed) && !fieldCard.seed().equals(trumpSeed))) {
                    toPick.add(current);
                    points[i] = calculatePoints(toPick);
                    toPick.remove(current);
                } else {
                    points[i] -= getCardPoints(current);
                    if(current.seed().equals(trumpSeed)){
                        points[i] -= 3;
                    }
                    points[i] -= current.cardName().getValue()/2;
                }
            }
        } else {
            for(int i=0; i<hand.size(); i++){
                Card current = hand.get(i);
                points[i] -= getCardPoints(current);
                if(current.seed().equals(trumpSeed)){
                    points[i] -= 3;
                }
                points[i] -= current.cardName().getValue()/2;
            }
        }

        int max = points[0];
        int maxPosition = 0;
        for (int i = 1; i < hand.size(); i++) {
            if (points[i] > max) {
                max = points[i];
                maxPosition = i;
            }
        }
        return hand.get(maxPosition);
    }

    // GAME OVER AND MATCH RESULTS

    public boolean isGameOver() {
        return deck.size() == 0 && humanPlayer.getHand().isEmpty() && cpuPlayer.getHand().isEmpty() && !hasCpuSelectedCard;
    }

    public void calculateMatchResults() {
        calculatePlayerPoints(humanPlayer);
        calculatePlayerPoints(cpuPlayer);
    }

    private void calculatePlayerPoints(Player player) {
        player.setRoundPoints(player.getBank().getResultPoints());
        if(player.hasWon())
            player.addGamePoint();
    }

    // NEXT MATCH

    public void nextMatch() {
        humanPlayer.clear();
        cpuPlayer.clear();
        deck = new Deck();

        firstPlayer = firstPlayer == humanPlayer ? cpuPlayer : humanPlayer;
        currentPlayer = firstPlayer;
        oppositeToCurrentPlayer = currentPlayer == humanPlayer ? cpuPlayer : humanPlayer;
    }

    // UTILS

    public static List<Card> getSortedCards(List<Card> cards) {
        List<Card> sorted = new ArrayList<>(cards);
        Collections.sort(sorted);
        return sorted;
    }

    public static int calculatePoints(List<Card> cards){
        int points = 0;
        for(Card card : cards) {
            points += getCardPoints(card);
        }
        return points;
    }

    public static int getCardPoints(Card card){
        return switch (card.cardName()) {
            case JACK -> 2;
            case HORSE -> 3;
            case KING -> 4;
            case THREE -> 10;
            case ACE -> 11;
            default -> 0;
        };
    }

    // GETTERS

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public Player getCpuPlayer() {
        return cpuPlayer;
    }

    public Deck getDeck() {
        return deck;
    }

    public Card getFieldCard() {
        return fieldCard;
    }

    public boolean hasCpuSelectedCard() {
        return hasCpuSelectedCard;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Card getTrump() {
        return trump;
    }

}
