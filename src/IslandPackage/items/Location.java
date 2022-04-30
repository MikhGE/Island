package IslandPackage.items;

import IslandPackage.Island;
import IslandPackage.items.animals.Animal;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class Location implements Callable<String> {

    private final Island island;
    private ArrayList<Entity> entities;

    public Location(int maxEntityInLocation, Island island) {
        this.island     = island;
        this.entities   = new ArrayList<>(maxEntityInLocation);

    }

    @Override
    public String call() throws Exception {
        for (Entity entity : entities) {
            if(Animal.class.isAssignableFrom(entity.getClass())){
                int randomInt = ThreadLocalRandom.current().nextInt();

            }
        }
        return "Все нормально";
    }
}
