package IslandPackage.items;

import IslandPackage.Island;

import java.util.concurrent.ConcurrentHashMap;

public abstract class Entity {

    private static long countEntities;
    private static final ConcurrentHashMap<Class<? extends Entity>, Integer> statistic =  new ConcurrentHashMap();
    private final Object lockForCoutEntities = new Object();
    private boolean isDead = false;
    private double weight;
    private Location location;
    private Island island;

    public Entity() {
        synchronized (lockForCoutEntities){
            countEntities++;
            if(statistic.containsKey(this.getClass())){
                statistic.put(this.getClass(),statistic.get(this.getClass())+1);
            }
            else{
                statistic.put(this.getClass(), 1);
            }
        }
    }

    public static long getCoutEntities(){
        return countEntities;
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
            synchronized (lockForCoutEntities){
                countEntities--;
            }
        }
    }
}
