package scopadasso.model;

public enum CardName {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    JACK(8),
    HORSE(9),
    KING(10);

    private final int value;

    CardName(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
