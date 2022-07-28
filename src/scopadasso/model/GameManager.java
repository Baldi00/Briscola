package scopadasso.model;

import java.util.*;

public class GameManager {
    private Player humanPlayer;
    private Player cpuPlayer;
    private List<Card> field;
    private Deck deck;
    private boolean hasCpuPlayedCard;
    private Player lastPlayerWhoPickedCards;
    private Player startingPlayer;

    public GameManager() {
        setup();
    }

    private void setup() {
        humanPlayer = new Player();
        cpuPlayer = new Player();
        field = new ArrayList<>();
        deck = new Deck();
        hasCpuPlayedCard = false;
        startingPlayer = humanPlayer;
    }

    public void preparation() {
        deck.shuffleDeck();
        prepareField();
        giveThreeCardsToPlayers();
        calculateAdditionalPoints();
    }

    private void prepareField() {
        for (int i = 0; i < 4; i++)
            field.add(deck.extract());

        if (cardByNameOccurs(field, CardName.ACE) > 1) {
            setup();
            preparation();
        }
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

    public void cpuPlayerPlayCard() {
        Card bestCardToPlay = selectBestCardToPlay(cpuPlayer.getHand());
        if (!cpuPlayer.getHand().isEmpty()) {
            cpuPlayer.playCard(bestCardToPlay);
            hasCpuPlayedCard = true;
        }
    }

    public void executeMove(Player player, Card card) {
        boolean done = false;
        if (card.getCardName().equals(CardName.ACE) && cardByNameOccurs(field, CardName.ACE) == 0 && !field.isEmpty()) {
            grabAll(player);
            addCardToPlayerBankOrMop(player, card);
            done = true;
        }
        if (!done) {
            done = searchAndGrabSingleCard(player, card);
        }
        if (!done) {
            done = searchAndGrabMultipleCards(player, card);
        }
        if (!done) {
            placeCardOnField(card);
        } else {
            lastPlayerWhoPickedCards = player;
        }

        hasCpuPlayedCard = false;
    }

    // MOVE TO PERFORM

    private void grabAll(Player player) {
        for (Card card1 : field) {
            player.addToBank(card1);
        }
        field.clear();
    }

    private boolean searchAndGrabSingleCard(Player player, Card card) {
        for (Card card1 : field) {
            if (card.getCardName().equals(card1.getCardName())) {
                if (field.size() == 1) {
                    if (isTurnOver() && isGameOver()) {
                        player.addToBank(card);
                    } else {
                        addCardToPlayerBankOrMop(player, card);
                    }
                } else {
                    player.addToBank(card);
                }
                player.addToBank(card1);
                field.remove(card1);
                return true;
            }
        }
        return false;
    }

    private boolean searchAndGrabMultipleCards(Player player, Card card) {
        List<List<Card>> possibleCardsToGrab = searchForPossibleCardsToGrab(card);

        if (!possibleCardsToGrab.isEmpty()) {
            List<Card> bestMove = selectBestGroupOfCards(possibleCardsToGrab, card);
            for (Card card1 : bestMove) {
                player.addToBank(card1);
            }

            field.removeAll(bestMove);

            if (field.isEmpty()) {
                addCardToPlayerBankOrMop(player, card);
            } else {
                player.addToBank(card);
            }
            return true;
        }
        return false;
    }

    private List<Card> selectBestGroupOfCards(List<List<Card>> listOfGroups, Card card) {
        Map<List<Card>, Integer> points = new HashMap<>();
        for (List<Card> group : listOfGroups) {
            group.add(card);
            int groupPoints = calculateGroupOfCardsPoints(group);
            group.remove(card);
            points.put(group, groupPoints);
        }
        return groupWithMaxPoints(points);
    }

    private int calculateValueOfCards(List<Card> cards) {
        int counter = 0;
        for (Card card : cards) {
            counter += card.getCardName().getValue();
        }
        return counter;
    }

    private void placeCardOnField(Card card) {
        field.add(card);
    }

    // CPU CARD SELECTION

    private Card selectBestCardToPlay(List<Card> hand) {
        Map<Card, Integer> points = new HashMap<>();
        initializePointsMap(hand, points);
        calculatePointsForAceCards(hand, points);
        calculatePointsForSingleCards(hand, points);
        calculatePointsForMultipleCards(hand, points);

        if (areAllZeroPoints(points)) {
            calculateLeastWorseCardToPlay(hand, points);
        }

        return cardWithMaxPoints(points);
    }

    private void initializePointsMap(List<Card> hand, Map<Card, Integer> points) {
        for (Card card : hand) {
            points.put(card, 0);
        }
    }

    private void calculatePointsForAceCards(List<Card> hand, Map<Card, Integer> points) {
        for (Card card : hand) {
            points.put(card, 0);
            if (card.getCardName().equals(CardName.ACE) && cardByNameOccurs(field, CardName.ACE) == 0 && !field.isEmpty()) {
                points.put(card, 10 + field.size());
            }
        }
    }

    private void calculatePointsForSingleCards(List<Card> hand, Map<Card, Integer> points) {
        for (Card card : hand) {
            for (Card card1 : field) {
                if (card.getCardName().equals(card1.getCardName()) && points.get(card) == 0) {
                    List<Card> move = new ArrayList<>();
                    move.add(card);
                    move.add(card1);
                    points.put(card, calculateGroupOfCardsPoints(move));
                }
            }
        }
    }

    private void calculatePointsForMultipleCards(List<Card> hand, Map<Card, Integer> points) {
        for (Card card : hand) {
            List<List<Card>> possibleCardsToGrab = searchForPossibleCardsToGrab(card);
            if (!possibleCardsToGrab.isEmpty() && points.get(card) == 0) {
                List<Card> bestMove = selectBestGroupOfCards(possibleCardsToGrab, card);
                if (bestMove != null) {
                    bestMove.add(card);
                    int point = calculateGroupOfCardsPoints(bestMove);
                    points.put(card, point);
                }
            }
        }
    }

    private boolean areAllZeroPoints(Map<Card, Integer> points) {
        int zeroCounter = 0;
        for (Map.Entry<Card, Integer> entry : points.entrySet()) {
            if (entry.getValue() == 0) {
                zeroCounter++;
            }
        }
        return zeroCounter == points.size();
    }

    private void calculateLeastWorseCardToPlay(List<Card> hand, Map<Card, Integer> points) {
        for (Card card : hand) {
            if (card.getCardName().equals(CardName.ACE)) {
                points.put(card, points.get(card) - 10);
            }
            if (getTotalValueOf(field) + card.getCardName().getValue() <= 10) {
                points.put(card, points.get(card) - 3);
            }
            if (card.getCardName().equals(CardName.SEVEN) ||
                    card.getCardName().equals(CardName.SIX) ||
                    card.getCardName().equals(CardName.KING)) {
                points.put(card, points.get(card) - 1);
            }
            if (card.getSeed().equals(Seed.MONEY)) {
                points.put(card, points.get(card) - 1);
            }
        }
    }

    // GAME OVER AND MATCH RESULTS

    public boolean isGameOver() {
        return deck.size() == 0;
    }

    public void giveLastFieldCardsToLastPlayerWhoPicked() {
        for (Card card : field)
            lastPlayerWhoPickedCards.addToBank(card);
        field.clear();
    }

    public void calculateMatchResults() {
        calculatePlayerPoints(humanPlayer);
        calculatePlayerPoints(cpuPlayer);
    }

    private void calculatePlayerPoints(Player player) {
        player.setRoundPoints(player.getBank().getResultPoints() + player.getTotalAdditionalPoints());
        player.addGamePoints(player.getBank().getResultPoints() + player.getTotalAdditionalPoints());
    }

    // NEXT MATCH

    public void nextMatch() {
        humanPlayer.clear();
        cpuPlayer.clear();
        deck = new Deck();
        startingPlayer = startingPlayer == humanPlayer ? cpuPlayer : humanPlayer;
    }

    // UTILS

    private int calculateGroupOfCardsPoints(List<Card> cards) {
        int points = 0;

        // Mop
        if (cards.containsAll(field)) {
            points += 10;
        }

        if (cards.contains(new Card(CardName.SEVEN, Seed.MONEY))) {
            points += 10;
        }
        if (cards.contains(new Card(CardName.KING, Seed.MONEY))) {
            points += 10;
        }
        points += 4 * cardByNameOccurs(cards, CardName.SEVEN);
        points += 3 * cardByNameOccurs(cards, CardName.SIX);
        points += 2 * cardBySeedOccurs(cards, Seed.MONEY);
        points += cards.size();

        return points;
    }

    private List<List<Card>> searchForPossibleCardsToGrab(Card card) {
        List<List<Card>> permutations = new ArrayList<>();
        for (int i = 2; i <= field.size(); i++) {
            List<Card> combination = new ArrayList<>();
            createPermutation(permutations, combination, field, i, card.getCardName().getValue());
        }

        deleteDuplications(permutations);
        return permutations;
    }

    private void createPermutation(List<List<Card>> result, List<Card> partial, List<Card> available, int length, int cardValue) {
        if (partial.size() == length) {
            if (calculateValueOfCards(partial) == cardValue)
                result.add(partial);
            return;
        }
        for (Card card : available) {
            List<Card> partial2 = new ArrayList<>(partial);
            partial2.add(card);
            if (calculateValueOfCards(partial2) <= cardValue) {
                List<Card> available2 = new ArrayList<>(available);
                available2.remove(card);
                createPermutation(result, partial2, available2, length, cardValue);
            }
        }
    }

    private void deleteDuplications(List<List<Card>> permutations) {
        for (List<Card> list : permutations) {
            Collections.sort(list);
        }

        List<List<Card>> permutationsWithoutDuplications = new ArrayList<>();
        int permutationsWithoutDuplicationsSize = 0;
        if (!permutations.isEmpty()) {
            permutationsWithoutDuplications.add(permutations.get(0));
            permutationsWithoutDuplicationsSize++;
            for (int i = 1; i < permutations.size(); i++) {
                if (!permutationsWithoutDuplications.get(permutationsWithoutDuplicationsSize - 1).containsAll(permutations.get(i))) {
                    permutationsWithoutDuplications.add(permutations.get(i));
                    permutationsWithoutDuplicationsSize++;
                }
            }
        }
    }

    private List<Card> groupWithMaxPoints(Map<List<Card>, Integer> points) {
        int max = Integer.MIN_VALUE;
        List<Card> maxGroup = null;

        for (Map.Entry<List<Card>, Integer> entry : points.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxGroup = entry.getKey();
            }
        }
        return maxGroup;
    }

    private Card cardWithMaxPoints(Map<Card, Integer> points) {
        int max = Integer.MIN_VALUE;
        Card maxCard = null;

        for (Map.Entry<Card, Integer> entry : points.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maxCard = entry.getKey();
            }
        }
        return maxCard;
    }

    private int cardByNameOccurs(List<Card> cards, CardName cardName) {
        int counter = 0;
        for (Seed seed : Seed.values()) {
            if (cards.contains(new Card(cardName, seed)))
                counter++;
        }
        return counter;
    }

    private int cardBySeedOccurs(List<Card> cards, Seed seed) {
        int counter = 0;
        for (CardName cardName : CardName.values()) {
            if (cards.contains(new Card(cardName, seed)))
                counter++;
        }
        return counter;
    }

    private int getTotalValueOf(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            sum += card.cardName().getValue();
        }
        return sum;
    }

    public boolean isTurnOver() {
        return humanPlayer.getHand().isEmpty() && cpuPlayer.getHand().isEmpty();
    }

    private void addCardToPlayerBankOrMop(Player player, Card card) {
        if (isLastTurnOfTheGame()) {
            player.addToBank(card);
        } else {
            player.addToMop(card);
        }
    }

    public boolean isLastTurnOfTheGame() {
        return isTurnOver() && isGameOver() && !hasCpuPlayedCard;
    }

    public void calculateAdditionalPoints() {
        calculateAdditionalPointsFor(humanPlayer);
        calculateAdditionalPointsFor(cpuPlayer);
    }

    public void calculateAdditionalPointsFor(Player player) {
        List<Card> hand = player.getHand();
        if(hand.size() == 3) {
            if(hand.get(0).cardName().equals(hand.get(1).getCardName()) &&
                    hand.get(1).cardName().equals(hand.get(2).getCardName())){
                player.addAdditionalPoints(7);
            } else if (getTotalValueOf(hand) < 10) {
                if (hand.get(0).cardName().equals(hand.get(1).getCardName()) ||
                        hand.get(1).cardName().equals(hand.get(2).getCardName()) ||
                        hand.get(0).cardName().equals(hand.get(2).getCardName())) {
                    player.addAdditionalPoints(3);
                } else {
                    player.addAdditionalPoints(2);
                }
            }
        }
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

    public List<Card> getField() {
        return field;
    }

    public boolean hasCpuPlayedCard() {
        return hasCpuPlayedCard;
    }

    public Player getStartingPlayer() {
        return startingPlayer;
    }
}
