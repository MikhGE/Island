package IslandPackage.items.animals;

import IslandPackage.items.Location;

public interface AnimalInterface {
    void eat();
    boolean move(Location location);
    void multiply();
    void die();
}
