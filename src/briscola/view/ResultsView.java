package briscola.view;

import briscola.model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ResultsView {

    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 130;

    private final JLabel humanPointsLabel;
    private final JLabel cpuPointsLabel;
    private final JLabel winnerLabel;
    private final List<JLabel> humanCardsLabels;
    private final List<JLabel> cpuCardsLabels;
    private final JPanel humanCardsPanel;
    private final JPanel cpuCardsPanel;

    public ResultsView() {
        //INITIALIZATIONS
        JFrame frame = new JFrame("Risultati");
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 0, 0));
        humanPointsLabel = new JLabel("TU: 0 punti");
        cpuPointsLabel = new JLabel("CPU: 0 punti");
        winnerLabel = new JLabel();
        humanCardsPanel = new JPanel(new GridLayout(1, 40, 0, 0));
        cpuCardsPanel = new JPanel(new GridLayout(1, 40, 0, 0));

        humanCardsLabels = new ArrayList<>();
        cpuCardsLabels = new ArrayList<>();

        humanCardsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cpuCardsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        humanPointsLabel.setFont(humanPointsLabel.getFont().deriveFont(20f));
        cpuPointsLabel.setFont(cpuPointsLabel.getFont().deriveFont(20f));
        winnerLabel.setFont(cpuPointsLabel.getFont().deriveFont(35f));
        humanPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //ASSEMBLY

        mainPanel.add(humanPointsLabel);
        mainPanel.add(humanCardsPanel);
        mainPanel.add(cpuPointsLabel);
        mainPanel.add(cpuCardsPanel);
        mainPanel.add(winnerLabel);

        frame.add(mainPanel);

        //LAST SETTINGS AND SHOW
        frame.setSize(1366, 768);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setHumanPoints(int points) {
        humanPointsLabel.setText("TU: " + points + " punti");
    }

    public void setCpuPoints(int points) {
        cpuPointsLabel.setText("CPU: " + points + " punti");
    }

    public void setWinner(int humanPoints, int cpuPoints) {
        if(humanPoints > cpuPoints) {
            winnerLabel.setText("HAI VINTO QUESTO ROUND");
            winnerLabel.setForeground(new Color(0, 138, 14));
        }else if (humanPoints < cpuPoints){
            winnerLabel.setText("HAI PERSO QUESTO ROUND");
            winnerLabel.setForeground(Color.RED);
        }else {
            winnerLabel.setText("PAREGGIO");
            winnerLabel.setForeground(Color.BLUE);
        }
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
}
