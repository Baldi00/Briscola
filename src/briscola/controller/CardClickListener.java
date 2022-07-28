package briscola.controller;

import briscola.model.Card;
import briscola.model.GameManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CardClickListener implements MouseListener {
    private final Card card;
    private final GameManager gameManager;
    private final Controller controller;

    public CardClickListener(Card card, GameManager gameManager, Controller controller) {
        this.card = card;
        this.gameManager = gameManager;
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gameManager.getCurrentPlayer() == gameManager.getHumanPlayer()) {
            gameManager.humanPlayerPlayCard(card);
            controller.updateView();

            System.out.println("CARDCLICK: " + gameManager.isGameOver());

            if(gameManager.isGameOver()){
                gameManager.calculateMatchResults();
                controller.showMatchResults();
                controller.setActionButton("nextGame", "Prossima Partita", true);
            }

//            if (gameManager.isTurnOver()) {
//                if (gameManager.isGameOver()) {
//                    gameManager.giveLastFieldCardsToLastPlayerWhoPicked();
//                    gameManager.calculateMatchResults();
//                    controller.showMatchResults();
//                } else {
//                    gameManager.giveThreeCardsToPlayers();
//                    if(gameManager.getCurrentPlayer() == gameManager.getCpuPlayer()) {
//                        gameManager.cpuPlayerPlayCard();
//                    }
//                }
//            } else {
//                gameManager.cpuPlayerPlayCard();
//            }


//            if(gameManager.isLastTurnOfTheGame()){
//                controller.setActionButton("nextGame", "Prossima Partita", true);
//            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //unused
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //unused
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //unused
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //unused
    }
}
