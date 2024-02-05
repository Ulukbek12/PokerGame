package Collections.challenge;

public enum Ranking {
    NONE,ONE_PAIR,TWO_PAIR,THREE_OF_KIND,FULL_HOUSE,FOUR_OF_KIND;

    @Override
    public String toString() {
        return this.name().replace('_',' ');
    }
}
