package briscola;

import briscola.controller.Controller;
import briscola.model.GameManager;
import briscola.view.View;

public class Briscola {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.preparation();
        View view = new View();
        new Controller(gameManager, view);
    }

}
