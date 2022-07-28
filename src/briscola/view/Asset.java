package briscola.view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public enum Asset {
    ACE_MONEY("img1.jpg"),
    TWO_MONEY("img2.jpg"),
    THREE_MONEY("img3.jpg"),
    FOUR_MONEY("img4.jpg"),
    FIVE_MONEY("img5.jpg"),
    SIX_MONEY("img6.jpg"),
    SEVEN_MONEY("img7.jpg"),
    JACK_MONEY("img8.jpg"),
    HORSE_MONEY("img9.jpg"),
    KING_MONEY("img10.jpg"),
    ACE_CUP("img11.jpg"),
    TWO_CUP("img12.jpg"),
    THREE_CUP("img13.jpg"),
    FOUR_CUP("img14.jpg"),
    FIVE_CUP("img15.jpg"),
    SIX_CUP("img16.jpg"),
    SEVEN_CUP("img17.jpg"),
    JACK_CUP("img18.jpg"),
    HORSE_CUP("img19.jpg"),
    KING_CUP("img20.jpg"),
    ACE_STICK("img21.jpg"),
    TWO_STICK("img22.jpg"),
    THREE_STICK("img23.jpg"),
    FOUR_STICK("img24.jpg"),
    FIVE_STICK("img25.jpg"),
    SIX_STICK("img26.jpg"),
    SEVEN_STICK("img27.jpg"),
    JACK_STICK("img28.jpg"),
    HORSE_STICK("img29.jpg"),
    KING_STICK("img30.jpg"),
    ACE_SWORD("img31.jpg"),
    TWO_SWORD("img32.jpg"),
    THREE_SWORD("img33.jpg"),
    FOUR_SWORD("img34.jpg"),
    FIVE_SWORD("img35.jpg"),
    SIX_SWORD("img36.jpg"),
    SEVEN_SWORD("img37.jpg"),
    JACK_SWORD("img38.jpg"),
    HORSE_SWORD("img39.jpg"),
    KING_SWORD("img40.jpg"),
    BACK("img41.jpg"),
    BACK_ROTATED("img42.jpg");

    private final String path;
    private final ClassLoader cl = Asset.class.getClassLoader();

    Asset(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Image getSprite(int width, int height) {
        try (InputStream stream = cl.getResourceAsStream(path)) {
            Image image = ImageIO.read(stream);
            image = Utils.resize((BufferedImage) image, width, height);
            return image;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
