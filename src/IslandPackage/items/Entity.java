package IslandPackage.items;

public abstract class Entity {
    private double weight;
    private Location location;

    public enum Characteristics {WEIGHT, MAXCOUNTINLOCATION, SPEED, COUNTNEDDEDFOOD, COUNTHUNGRYMOVE}

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
