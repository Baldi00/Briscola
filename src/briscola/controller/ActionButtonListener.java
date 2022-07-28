package briscola.controller;

import briscola.model.GameManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionButtonListener implements ActionListener {
    private final GameManager gameManager;
    private final Controller controller;

    public ActionButtonListener(GameManager gameManager, Controller controller) {
        this.gameManager = gameManager;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = ((JButton) e.getSource()).getName();
        if (action.equals("proceed")) {
            gameManager.executeMove(gameManager.getCpuPlayer(), gameManager.getCpuPlayer().getLastPlayedCard());
            controller.updateView();

            System.out.println("ACTIONBUTTON: " + gameManager.isGameOver());

            if(gameManager.isGameOver()){
                gameManager.calculateMatchResults();
                controller.showMatchResults();
                controller.setActionButton("nextGame", "Prossima Partita", true);
            }

//            if (gameManager.isTurnOver()) {
//                if (gameManager.isGameOver()) {
//                    gameManager.calculateMatchResults();
//                    controller.showMatchResults();
//                } else {
//                    gameManager.giveThreeCardsToPlayers();
//                }
//            }
//
//            if(gameManager.isLastTurnOfTheGame()){
//                controller.setActionButton("nextGame", "Prossima Partita", true);
//            }
        } else if (action.equals("nextGame")) {
//            gameManager.nextMatch();
//            gameManager.preparation();
//            controller.setActionButton("proceed", "Prosegui", false);
//
//            if(gameManager.getCurrentPlayer() == gameManager.getCpuPlayer()) {
//                gameManager.cpuPlayerPlayCard();
//            }

            controller.updateView();
        }
    }
}
