package briscola.model;

import java.util.Objects;

public record Card(CardName cardName, Seed seed) implements Comparable<Card> {

    public CardName getCardName() {
        return cardName;
    }

    public Seed getSeed() {
        return seed;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.cardName);
        hash = 17 * hash + Objects.hashCode(this.seed);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.cardName != other.cardName) {
            return false;
        }
        return this.seed == other.seed;
    }

    @Override
    public String toString() {
        return "Card{" + "value=" + cardName + ", seed=" + seed + '}';
    }

    @Override
    public int compareTo(Card o) {
        int compareValue = cardName.compareTo(o.cardName);
        if (compareValue == 0) {
            return seed.compareTo(o.seed);
        }
        return compareValue;
    }
}
