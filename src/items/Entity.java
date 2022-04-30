package items;

public abstract class Entity {
    private final String name;
    private long weight;
    private int maximumNumberOfEntityOfSpeciesInLocation;

    public Entity(String name) {
        this.name = name;
    }
}
