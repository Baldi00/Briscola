package scopadasso.view;

import scopadasso.model.Card;
import scopadasso.model.Deck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View {

    private static final String CARDS_STRING = " carte";
    private static final int CARD_WIDTH = 150;
    private static final int CARD_HEIGHT = 250;

    private final JLabel humanInfoLabel;
    private final JLabel cpuInfoLabel;
    private final JLabel[] humanCardsLabels;
    private final JLabel[] cpuCardsLabels;
    private final List<JLabel> humanMopsLabels;
    private final List<JLabel> cpuMopsLabels;
    private final JLabel[] fieldLabels;
    private final JLabel deckLabel;
    private final JLabel humanBankLabel;
    private final JLabel cpuBankLabel;
    private final JLabel humanAdditionalPointsLabel;
    private final JLabel cpuAdditionalPointsLabel;
    private final JPanel humanMopsPanel;
    private final JPanel cpuMopsPanel;
    private final JButton actionButton;

    public View() {
        //INITIALIZATIONS
        JFrame frame = new JFrame("Scopa d'Asso");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel fieldPanel = new JPanel(new BorderLayout());
        JPanel fieldCardPanel = new JPanel(new GridLayout(2, 4, 0, 0));
        JPanel humanPanel = new JPanel(new GridLayout(3, 1, 0, 0));
        JPanel cpuPanel = new JPanel(new GridLayout(3, 1, 0, 0));
        JPanel humanCardsPanel = new JPanel(new BorderLayout());
        JPanel cpuCardsPanel = new JPanel(new BorderLayout());
        humanMopsPanel = new JPanel(new GridLayout(1, 20, 0, 0));
        cpuMopsPanel = new JPanel(new GridLayout(1, 20, 0, 0));
        actionButton = new JButton("Prosegui");

        humanInfoLabel = new JLabel("TU: 0 punti");
        cpuInfoLabel = new JLabel("CPU: 0 punti");
        humanCardsLabels = new JLabel[3];
        cpuCardsLabels = new JLabel[3];
        fieldLabels = new JLabel[8];
        deckLabel = new JLabel();

        humanBankLabel = new JLabel();
        cpuBankLabel = new JLabel();
        humanAdditionalPointsLabel = new JLabel();
        cpuAdditionalPointsLabel = new JLabel();
        humanMopsLabels = new ArrayList<>();
        cpuMopsLabels = new ArrayList<>();

        actionButton.setEnabled(false);
        actionButton.setName("proceed");
        actionButton.setPreferredSize(new Dimension(200, 100));
        actionButton.setFont(actionButton.getFont().deriveFont(18f));

        humanCardsPanel.add(humanInfoLabel, BorderLayout.NORTH);
        cpuCardsPanel.add(cpuInfoLabel, BorderLayout.NORTH);
        String [] coordinates = new String[]{BorderLayout.WEST, BorderLayout.CENTER, BorderLayout.EAST};
        for (int i = 0; i < 3; i++) {
            humanCardsLabels[i] = new JLabel();
            cpuCardsLabels[i] = new JLabel();
            humanCardsLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            cpuCardsLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            humanCardsPanel.add(humanCardsLabels[i], coordinates[i]);
            cpuCardsPanel.add(cpuCardsLabels[i], coordinates[i]);
        }
        humanCardsPanel.add(humanAdditionalPointsLabel, BorderLayout.SOUTH);
        cpuCardsPanel.add(cpuAdditionalPointsLabel, BorderLayout.SOUTH);

        humanMopsPanel.setPreferredSize(new Dimension(300, 500));
        cpuMopsPanel.setPreferredSize(new Dimension(300, 500));
        humanInfoLabel.setFont(humanInfoLabel.getFont().deriveFont(25f));
        cpuInfoLabel.setFont(cpuInfoLabel.getFont().deriveFont(25f));
        humanBankLabel.setFont(humanBankLabel.getFont().deriveFont(18f));
        cpuBankLabel.setFont(cpuBankLabel.getFont().deriveFont(18f));
        humanAdditionalPointsLabel.setFont(humanAdditionalPointsLabel.getFont().deriveFont(27f));
        cpuAdditionalPointsLabel.setFont(cpuAdditionalPointsLabel.getFont().deriveFont(27f));
        humanAdditionalPointsLabel.setForeground(Color.RED);
        cpuAdditionalPointsLabel.setForeground(Color.RED);
        humanInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        humanBankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuBankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        humanAdditionalPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuAdditionalPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < 8; i++) {
            fieldLabels[i] = new JLabel();
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            fieldCardPanel.add(fieldLabels[i]);
        }
        fieldCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        deckLabel.setFont(deckLabel.getFont().deriveFont(18f));
        deckLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //ASSEMBLY
        fieldPanel.add(actionButton, BorderLayout.NORTH);
        fieldPanel.add(fieldCardPanel, BorderLayout.CENTER);
        fieldPanel.add(deckLabel, BorderLayout.SOUTH);

        humanPanel.add(humanCardsPanel);
        humanPanel.add(humanMopsPanel);
        humanPanel.add(humanBankLabel);
        cpuPanel.add(cpuCardsPanel);
        cpuPanel.add(cpuMopsPanel);
        cpuPanel.add(cpuBankLabel);

        mainPanel.add(humanPanel, BorderLayout.WEST);
        mainPanel.add(fieldPanel, BorderLayout.CENTER);
        mainPanel.add(cpuPanel, BorderLayout.EAST);

        frame.add(mainPanel);

        //LAST SETTINGS AND SHOW
        frame.setSize(1366, 768);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setHumanCards(List<Card> hand) {
        for (int i = 0; i < 3; i++) {
            humanCardsLabels[i].setIcon(null);
        }
        for (int i = 0; i < hand.size(); i++) {
            humanCardsLabels[i].setIcon(new ImageIcon(Utils.assetFromCard(hand.get(i)).getSprite(CARD_WIDTH, CARD_HEIGHT)));
        }
    }

    public void setHumanBank(List<Card> bankWithoutMop, List<Card> mop) {
        humanMopsLabels.clear();
        humanMopsPanel.removeAll();
        for (Card card : mop) {
            humanMopsLabels.add(new JLabel(new ImageIcon(Utils.assetFromCard(card).getSprite(CARD_WIDTH, CARD_HEIGHT))));
        }
        for (JLabel label : humanMopsLabels) {
            humanMopsPanel.add(label);
        }

        if (bankWithoutMop.isEmpty()) {
            humanBankLabel.setText("");
            humanBankLabel.setIcon(null);
        } else {
            humanBankLabel.setText(bankWithoutMop.size() + CARDS_STRING);
            humanBankLabel.setIcon(new ImageIcon(Asset.BACK_ROTATED.getSprite(CARD_HEIGHT, CARD_WIDTH)));
        }
    }

    public void setCpuCards(List<Card> hand, Card lastPlayedCard, boolean hasCpuPlayedCard) {
        for (int i = 0; i < 3; i++) {
            cpuCardsLabels[i].setIcon(null);
        }
        for (int i = 0; i < hand.size(); i++) {
            cpuCardsLabels[i].setIcon(new ImageIcon(Asset.BACK.getSprite(CARD_WIDTH, CARD_HEIGHT)));
        }

        if (hasCpuPlayedCard) {
            cpuCardsLabels[hand.size()].setIcon(new ImageIcon(Utils.assetFromCard(lastPlayedCard).getSprite(CARD_WIDTH, CARD_HEIGHT)));
            actionButton.setEnabled(true);
        } else {
            actionButton.setEnabled(false);
        }
    }

    public void setCpuBank(List<Card> bankWithoutMop, List<Card> mop) {
        cpuMopsLabels.clear();
        cpuMopsPanel.removeAll();
        for (Card card : mop) {
            cpuMopsLabels.add(new JLabel(new ImageIcon(Utils.assetFromCard(card).getSprite(CARD_WIDTH, CARD_HEIGHT))));
        }
        for (JLabel label : cpuMopsLabels) {
            cpuMopsPanel.add(label);
        }

        if (bankWithoutMop.isEmpty()) {
            cpuBankLabel.setText("");
            cpuBankLabel.setIcon(null);
        } else {
            cpuBankLabel.setText(bankWithoutMop.size() + CARDS_STRING);
            cpuBankLabel.setIcon(new ImageIcon(Asset.BACK_ROTATED.getSprite(CARD_HEIGHT, CARD_WIDTH)));
        }
    }

    public void setDeck(Deck deck) {
        if (deck.size() != 0) {
            deckLabel.setText(deck.size() + CARDS_STRING);
            deckLabel.setIcon(new ImageIcon(Asset.BACK_ROTATED.getSprite(CARD_HEIGHT, CARD_WIDTH)));
        } else {
            deckLabel.setText("Mazzo vuoto");
            deckLabel.setIcon(null);
        }
    }

    public void setField(List<Card> field) {
        for (int i = 0; i < 8; i++) {
            fieldLabels[i].setIcon(null);
        }
        for (int i = 0; i < field.size(); i++) {
            fieldLabels[i].setIcon(new ImageIcon(Utils.assetFromCard(field.get(i)).getSprite(CARD_WIDTH, CARD_HEIGHT)));
        }
    }

    public void setHumanAdditionalPoints(List<Integer> points) {
        if(!points.isEmpty()) {
            humanAdditionalPointsLabel.setText("CUSA: ");
            for (Integer i : points) {
                humanAdditionalPointsLabel.setText(humanAdditionalPointsLabel.getText() + i + " ");
            }
        }else{
            humanAdditionalPointsLabel.setText("");
        }
    }

    public void setCpuAdditionalPoints(List<Integer> points) {
        if(!points.isEmpty()) {
            cpuAdditionalPointsLabel.setText("CUSA: ");
            for (Integer i : points) {
                cpuAdditionalPointsLabel.setText(cpuAdditionalPointsLabel.getText() + i + " ");
            }
        }else{
            cpuAdditionalPointsLabel.setText("");
        }
    }

    public void setHumanGamePoints(int points) {
        humanInfoLabel.setText("TU: " + points + " punti");
    }

    public void setCpuGamePoints(int points) {
        cpuInfoLabel.setText("CPU: " + points + " punti");
    }

    public JLabel[] getHumanCardsLabels() {
        return humanCardsLabels;
    }

    public JButton getActionButton() {
        return actionButton;
    }

    public void setActionButton(String name, String text, boolean enabled){
        actionButton.setName(name);
        actionButton.setText(text);
        actionButton.setEnabled(enabled);
    }
}
