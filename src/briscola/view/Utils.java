package briscola.view;

import briscola.model.Card;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Utils {

    private Utils() {

    }

    public static Image resize(BufferedImage img, int width, int height) {
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage bi = new BufferedImage(width, height, img.getType());

        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();

        return bi;
    }

    public static Asset assetFromCard(Card card) {
        return Asset.valueOf(card.getCardName() + "_" + card.getSeed());
    }
}
