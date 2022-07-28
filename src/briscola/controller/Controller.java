package briscola.controller;

import briscola.model.Card;
import briscola.model.GameManager;
import briscola.model.Player;
import briscola.view.ResultsView;
import briscola.view.View;

import javax.swing.*;
import java.util.List;

public class Controller {
    private final GameManager gameManager;
    private final View view;
    private final CardClickListener[] cardClickListeners;

    public Controller(GameManager gameManager, View view) {
        this.gameManager = gameManager;
        this.view = view;
        cardClickListeners = new CardClickListener[3];
        updateView();
        addConfirmCpuActionButtonListener();
    }

    public void updateView() {
        view.setHumanCards(gameManager.getHumanPlayer().getHand());
        view.setHumanBank(gameManager.getHumanPlayer().getBankCards());
        view.setHumanGamePoints(gameManager.getHumanPlayer().getGamePoints());
        view.setCpuCards(gameManager.getCpuPlayer().getHand(), gameManager.getCpuPlayer().getLastPlayedCard(), gameManager.hasCpuSelectedCard());
        view.setCpuBank(gameManager.getCpuPlayer().getBankCards());
        view.setCpuGamePoints(gameManager.getCpuPlayer().getGamePoints());
        view.setDeck(gameManager.getDeck());
        view.setFieldCard(gameManager.getFieldCard());
        view.setTrump(gameManager.getTrump());
        view.setTurn(gameManager.getCurrentPlayer() == gameManager.getHumanPlayer());
        updateHumanPlayerCardClickListeners();
    }

    private void updateHumanPlayerCardClickListeners() {
        JLabel[] humanPlayerCardsLabels = view.getHumanCardsLabels();
        List<Card> humanPlayerCards = gameManager.getHumanPlayer().getHand();
        for (int i = 0; i < humanPlayerCards.size(); i++) {
            humanPlayerCardsLabels[i].removeMouseListener(cardClickListeners[i]);

            cardClickListeners[i] = new CardClickListener(humanPlayerCards.get(i), gameManager, this);
            humanPlayerCardsLabels[i].addMouseListener(cardClickListeners[i]);
        }
        for (int i = humanPlayerCards.size(); i < 3; i++) {
            humanPlayerCardsLabels[i].removeMouseListener(cardClickListeners[i]);
        }
    }

    private void addConfirmCpuActionButtonListener() {
        view.getActionButton().addActionListener(new ActionButtonListener(gameManager, this));
    }

    public void showMatchResults() {
        ResultsView resultsView = new ResultsView();
        Player humanPlayer = gameManager.getHumanPlayer();
        Player cpuPlayer = gameManager.getCpuPlayer();
        resultsView.setHumanPoints(humanPlayer.getRoundPoints());
        resultsView.setHumanCards(humanPlayer.getBankCards());
        resultsView.setCpuPoints(cpuPlayer.getRoundPoints());
        resultsView.setCpuCards(cpuPlayer.getBankCards());
        resultsView.setWinner(humanPlayer.getRoundPoints(), cpuPlayer.getRoundPoints());
    }

    public void setActionButton(String name, String text, boolean enabled){
        view.setActionButton(name, text, enabled);
    }
}
