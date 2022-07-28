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
        view.setHumanBank(gameManager.getHumanPlayer().getBankWithoutMop(), gameManager.getHumanPlayer().getMop());
        view.setHumanAdditionalPoints(gameManager.getHumanPlayer().getAdditionalPoints());
        view.setHumanGamePoints(gameManager.getHumanPlayer().getGamePoints());
        view.setCpuCards(gameManager.getCpuPlayer().getHand(), gameManager.getCpuPlayer().getLastPlayedCard(), gameManager.hasCpuPlayedCard());
        view.setCpuBank(gameManager.getCpuPlayer().getBankWithoutMop(), gameManager.getCpuPlayer().getMop());
        view.setCpuAdditionalPoints(gameManager.getCpuPlayer().getAdditionalPoints());
        view.setCpuGamePoints(gameManager.getCpuPlayer().getGamePoints());
        view.setDeck(gameManager.getDeck());
        view.setField(gameManager.getField());
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
        resultsView.setHumanPoints(
                humanPlayer.getRoundPoints(),
                humanPlayer.getBank().searchForBeautifulKing(),
                humanPlayer.getBank().searchForBeautifulSeven(),
                humanPlayer.getBank().searchForCoinsMajority(),
                humanPlayer.getBank().searchForPrimiera(),
                humanPlayer.getBank().searchForCardsNumberMajority(),
                humanPlayer.getMop(),
                humanPlayer.getAdditionalPoints());
        resultsView.setHumanCards(humanPlayer.getBankWithoutMop());
        resultsView.setHumanMops(humanPlayer.getMop());
        resultsView.setCpuPoints(
                cpuPlayer.getRoundPoints(),
                cpuPlayer.getBank().searchForBeautifulKing(),
                cpuPlayer.getBank().searchForBeautifulSeven(),
                cpuPlayer.getBank().searchForCoinsMajority(),
                cpuPlayer.getBank().searchForPrimiera(),
                cpuPlayer.getBank().searchForCardsNumberMajority(),
                cpuPlayer.getMop(),
                cpuPlayer.getAdditionalPoints());
        resultsView.setCpuCards(cpuPlayer.getBankWithoutMop());
        resultsView.setCpuMops(cpuPlayer.getMop());
    }

    public void setActionButton(String name, String text, boolean enabled){
        view.setActionButton(name, text, enabled);
    }
}
