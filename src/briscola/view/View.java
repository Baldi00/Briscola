package briscola.view;

import briscola.model.Card;
import briscola.model.Deck;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class View {

    private static final String CARDS_STRING = " carte";
    private static final int CARD_WIDTH = 150;
    private static final int CARD_HEIGHT = 250;

    private final JLabel humanInfoLabel;
    private final JLabel cpuInfoLabel;
    private final JLabel[] humanCardsLabels;
    private final JLabel[] cpuCardsLabels;
    private final JLabel fieldCardLabel;
    private final JLabel deckLabel;
    private final JLabel trumpLabel;
    private final JLabel humanBankLabel;
    private final JLabel cpuBankLabel;
    private final JButton actionButton;

    public View() {
        //INITIALIZATIONS
        JFrame frame = new JFrame("Briscola");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel fieldPanel = new JPanel(new BorderLayout());
        JPanel humanPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        JPanel cpuPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        JPanel humanCardsPanel = new JPanel(new BorderLayout());
        JPanel cpuCardsPanel = new JPanel(new BorderLayout());
        JPanel deckPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        actionButton = new JButton("Prosegui");

        humanInfoLabel = new JLabel("TU: 0 punti");
        cpuInfoLabel = new JLabel("CPU: 0 punti");
        humanCardsLabels = new JLabel[3];
        cpuCardsLabels = new JLabel[3];
        fieldCardLabel = new JLabel();
        deckLabel = new JLabel();
        trumpLabel = new JLabel();

        humanBankLabel = new JLabel();
        cpuBankLabel = new JLabel();

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

        humanInfoLabel.setFont(humanInfoLabel.getFont().deriveFont(25f));
        cpuInfoLabel.setFont(cpuInfoLabel.getFont().deriveFont(25f));
        humanBankLabel.setFont(humanBankLabel.getFont().deriveFont(18f));
        cpuBankLabel.setFont(cpuBankLabel.getFont().deriveFont(18f));
        deckLabel.setFont(deckLabel.getFont().deriveFont(18f));
        humanInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        humanBankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cpuBankLabel.setHorizontalAlignment(SwingConstants.CENTER);
        deckLabel.setHorizontalAlignment(SwingConstants.CENTER);
        trumpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fieldCardLabel.setHorizontalAlignment(SwingConstants.CENTER);

        fieldPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //ASSEMBLY
        deckPanel.add(trumpLabel);
        deckPanel.add(deckLabel);

        fieldPanel.add(actionButton, BorderLayout.NORTH);
        fieldPanel.add(fieldCardLabel, BorderLayout.CENTER);
        fieldPanel.add(deckPanel, BorderLayout.SOUTH);

        humanPanel.add(humanCardsPanel);
        humanPanel.add(humanBankLabel);
        cpuPanel.add(cpuCardsPanel);
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

    public void setHumanBank(List<Card> bank) {
        if (bank.isEmpty()) {
            humanBankLabel.setText("");
            humanBankLabel.setIcon(null);
        } else {
            humanBankLabel.setText(bank.size() + CARDS_STRING);
            humanBankLabel.setIcon(new ImageIcon(Asset.BACK_ROTATED.getSprite(CARD_HEIGHT, CARD_WIDTH)));
        }
    }

    public void setCpuCards(List<Card> hand, Card lastPlayedCard, boolean hasCpuSelectedCard) {
        for (int i = 0; i < 3; i++) {
            cpuCardsLabels[i].setIcon(null);
        }
        for (int i = 0; i < hand.size(); i++) {
            cpuCardsLabels[i].setIcon(new ImageIcon(Asset.BACK.getSprite(CARD_WIDTH, CARD_HEIGHT)));
        }

        if (hasCpuSelectedCard) {
            cpuCardsLabels[hand.size()].setIcon(new ImageIcon(Utils.assetFromCard(lastPlayedCard).getSprite(CARD_WIDTH, CARD_HEIGHT)));
            actionButton.setEnabled(true);
        } else {
            actionButton.setEnabled(false);
        }
    }

    public void setCpuBank(List<Card> bank) {
        if (bank.isEmpty()) {
            cpuBankLabel.setText("");
            cpuBankLabel.setIcon(null);
        } else {
            cpuBankLabel.setText(bank.size() + CARDS_STRING);
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

    public void setFieldCard(Card card) {
        if(card == null) {
            fieldCardLabel.setIcon(null);
        } else {
            fieldCardLabel.setIcon(new ImageIcon(Utils.assetFromCard(card).getSprite(CARD_WIDTH, CARD_HEIGHT)));
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

    public void setTrump(Card card) {
        if(card == null) {
            trumpLabel.setIcon(null);
        } else {
            trumpLabel.setIcon(new ImageIcon(Utils.assetFromCard(card).getSprite(CARD_WIDTH, CARD_HEIGHT)));
        }
    }

    public void setTurn(boolean isHumanPlayerTurn) {
        if(isHumanPlayerTurn){
            humanInfoLabel.setForeground(Color.RED);
            cpuInfoLabel.setForeground(Color.BLACK);
        } else {
            humanInfoLabel.setForeground(Color.BLACK);
            cpuInfoLabel.setForeground(Color.RED);
        }
    }
}
