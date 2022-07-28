package scopadasso;

import scopadasso.controller.Controller;
import scopadasso.model.GameManager;
import scopadasso.view.View;

public class ScopaDAsso {

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
