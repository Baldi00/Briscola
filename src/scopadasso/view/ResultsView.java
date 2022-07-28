package scopadasso.view;

import scopadasso.model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResultsView {

    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 130;

    private final JLabel humanPointsLabel;
    private final JLabel cpuPointsLabel;
    private final List<JLabel> humanMopsLabels;
    private final List<JLabel> cpuMopsLabels;
    private final List<JLabel> humanCardsLabels;
    private final List<JLabel> cpuCardsLabels;
    private final JPanel humanMopsPanel;
    private final JPanel cpuMopsPanel;
    private final JPanel humanCardsPanel;
    private final JPanel cpuCardsPanel;

    public ResultsView() {
        //INITIALIZATIONS
        JFrame frame = new JFrame("Risultati");
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        JPanel humanPanel = new JPanel(new BorderLayout());
        JPanel cpuPanel = new JPanel(new BorderLayout());
        humanPointsLabel = new JLabel("TU: 0 punti");
        cpuPointsLabel = new JLabel("CPU: 0 punti");
        humanMopsPanel = new JPanel(new GridLayout(1, 40, 0, 0));
        cpuMopsPanel = new JPanel(new GridLayout(1, 40, 0, 0));
        humanCardsPanel = new JPanel(new GridLayout(1, 40, 0, 0));
        cpuCardsPanel = new JPanel(new GridLayout(1, 40, 0, 0));

        humanMopsLabels = new ArrayList<>();
        cpuMopsLabels = new ArrayList<>();
        humanCardsLabels = new ArrayList<>();
        cpuCardsLabels = new ArrayList<>();

        humanCardsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cpuCardsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        humanPointsLabel.setFont(humanPointsLabel.getFont().deriveFont(20f));
        cpuPointsLabel.setFont(cpuPointsLabel.getFont().deriveFont(20f));

        //ASSEMBLY

        humanPanel.add(humanPointsLabel, BorderLayout.WEST);
        humanPanel.add(humanMopsPanel, BorderLayout.CENTER);
        humanPanel.add(humanCardsPanel, BorderLayout.SOUTH);

        cpuPanel.add(cpuPointsLabel, BorderLayout.WEST);
        cpuPanel.add(cpuMopsPanel, BorderLayout.CENTER);
        cpuPanel.add(cpuCardsPanel, BorderLayout.SOUTH);

        mainPanel.add(humanPanel);
        mainPanel.add(cpuPanel);

        frame.add(mainPanel);

        //LAST SETTINGS AND SHOW
        frame.setSize(1366, 768);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setHumanPoints(int points, int beautifulKing, int beautifulSeven, int coins, int primiera, int cardsNumber, List<Card> mop, List<Integer> additional) {
        humanPointsLabel.setText("<html>TU: " + points + " punti<br>");
        if (beautifulKing == 1) humanPointsLabel.setText(humanPointsLabel.getText() + "| Re Bello |");
        if (beautifulSeven == 1) humanPointsLabel.setText(humanPointsLabel.getText() + "| Sette Bello |");
        if (coins == 1) humanPointsLabel.setText(humanPointsLabel.getText() + "| Denari |");
        if (primiera == 1) humanPointsLabel.setText(humanPointsLabel.getText() + "| Primiera |");
        if (cardsNumber == 1) humanPointsLabel.setText(humanPointsLabel.getText() + "| Carte |");

        humanPointsLabel.setText(humanPointsLabel.getText() + "<br>" + mop.size() + " scope");

        if(!additional.isEmpty()) {
            humanPointsLabel.setText(humanPointsLabel.getText() + "<br>Cusa: ");
            for (Integer i : additional) {
                humanPointsLabel.setText(humanPointsLabel.getText() + i + " ");
            }
        }

        humanPointsLabel.setText(humanPointsLabel.getText() + "</html>");
    }

    public void setCpuPoints(int points, int beautifulKing, int beautifulSeven, int coins, int primiera, int cardsNumber, List<Card> mop, List<Integer> additional) {
        cpuPointsLabel.setText("<html>CPU: " + points + " punti<br>");
        if (beautifulKing == 1) cpuPointsLabel.setText(cpuPointsLabel.getText() + "| Re Bello |");
        if (beautifulSeven == 1) cpuPointsLabel.setText(cpuPointsLabel.getText() + "| Sette Bello |");
        if (coins == 1) cpuPointsLabel.setText(cpuPointsLabel.getText() + "| Denari |");
        if (primiera == 1) cpuPointsLabel.setText(cpuPointsLabel.getText() + "| Primiera |");
        if (cardsNumber == 1) cpuPointsLabel.setText(cpuPointsLabel.getText() + "| Carte |");

        cpuPointsLabel.setText(cpuPointsLabel.getText() + "<br>" + mop.size() + " scope");

        if(!additional.isEmpty()) {
            cpuPointsLabel.setText(cpuPointsLabel.getText() + "<br>Cusa: ");
            for (Integer i : additional) {
                cpuPointsLabel.setText(cpuPointsLabel.getText() + i + " ");
            }
        }

        cpuPointsLabel.setText(cpuPointsLabel.getText() + "</html>");
    }

    public void setHumanCards(List<Card> cards) {
        humanCardsLabels.clear();
        humanCardsPanel.removeAll();
        for (Card card : cards) {
            humanCardsLabels.add(new JLabel(new ImageIcon(Utils.assetFromCard(card).getSprite(CARD_WIDTH, CARD_HEIGHT))));
        }
        for (JLabel label : humanCardsLabels) {
            humanCardsPanel.add(label);
        }
    }

    public void setCpuCards(List<Card> cards) {
        cpuCardsLabels.clear();
        cpuCardsPanel.removeAll();
        for (Card card : cards) {
            cpuCardsLabels.add(new JLabel(new ImageIcon(Utils.assetFromCard(card).getSprite(CARD_WIDTH, CARD_HEIGHT))));
        }
        for (JLabel label : cpuCardsLabels) {
            cpuCardsPanel.add(label);
        }
    }

    public void setHumanMops(List<Card> cards) {
        humanMopsLabels.clear();
        humanMopsPanel.removeAll();
        for (Card card : cards) {
            humanMopsLabels.add(new JLabel(new ImageIcon(Utils.assetFromCard(card).getSprite(CARD_WIDTH, CARD_HEIGHT))));
        }
        for (JLabel label : humanMopsLabels) {
            humanMopsPanel.add(label);
        }
    }

    public void setCpuMops(List<Card> cards) {
        cpuMopsLabels.clear();
        cpuMopsPanel.removeAll();
        for (Card card : cards) {
            cpuMopsLabels.add(new JLabel(new ImageIcon(Utils.assetFromCard(card).getSprite(CARD_WIDTH, CARD_HEIGHT))));
        }
        for (JLabel label : cpuMopsLabels) {
            cpuMopsPanel.add(label);
        }
    }
}
