package IslandPackage.items;

import IslandPackage.Island;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Entity {

    private static long countEntities;
    private static long countDeadEntities;
    private static final ConcurrentHashMap<Class<? extends Entity>, Integer> statistic =  new ConcurrentHashMap();
    private static final Object lockForCountEntities = new Object();
    private static final Object lockForCountDeadEntities = new Object();
    private boolean isDead = false;
    private double weight;
    private Location location;
    private Island island;

    public Entity() {
        synchronized (lockForCountEntities){
            countEntities++;
            if (statistic.containsKey(this.getClass())) {
                statistic.put(this.getClass(), statistic.get(this.getClass()) + 1);
            } else {
                statistic.put(this.getClass(), 1);
            }
        }
    }

    public static long getCountEntities(){
        return countEntities;
    }

    public static long getCountDeadEntities() {
        return countDeadEntities;
    }

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

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
        if (isDead){
            synchronized (lockForCountEntities){
                countEntities--;
                if(statistic.containsKey(this.getClass())&&statistic.get(this.getClass())>0)
                    statistic.put(this.getClass(), statistic.get(this.getClass()) - 1);
            }
            synchronized (lockForCountDeadEntities){
                countDeadEntities++;
            }
        }
    }

    public void initializeEntity(){
        Map<Class<? extends Entity>, Map<Characteristics, ? extends Number>> mapOfCharacteristics = island.getMapOfCharacteristics();
        Map<Characteristics, ? extends Number> characteristics = mapOfCharacteristics.get(this.getClass());
        setWeight((double) characteristics.get(Characteristics.WEIGHT));
    }

}
