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
        return hand.get(0);
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
