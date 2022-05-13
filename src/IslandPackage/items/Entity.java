package IslandPackage.items;

import IslandPackage.Island;
import IslandPackage.items.animals.*;
import IslandPackage.items.plants.Grass;

import java.util.HashMap;
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
    private static Map<Class<? extends Entity>, String> mapOfUniCodes = new HashMap<>();

    static {
        mapOfUniCodes.put(Wolf.class,           "\uD83D\uDC3A");
        mapOfUniCodes.put(Snake.class,          "\uD83D\uDC0D");
        mapOfUniCodes.put(Fox.class,            "\uD83E\uDD8A");
        mapOfUniCodes.put(Bear.class,           "\uD83D\uDC3B");
        mapOfUniCodes.put(Eagle.class,          "\uD83E\uDD85");
        mapOfUniCodes.put(Horse.class,          "\uD83D\uDC0E");
        mapOfUniCodes.put(Deer.class,           "\uD83E\uDD8C");
        mapOfUniCodes.put(Rabbit.class,         "\uD83D\uDC07");
        mapOfUniCodes.put(Hamster.class,        "\uD83D\uDC39");
        mapOfUniCodes.put(Goat.class,           "\uD83D\uDC10");
        mapOfUniCodes.put(Sheep.class,          "\uD83D\uDC11");
        mapOfUniCodes.put(Kangaroo.class,       "\uD83E\uDD98");
        mapOfUniCodes.put(Cow.class,            "\uD83D\uDC2E");
        mapOfUniCodes.put(Duck.class,           "\uD83E\uDD86");
        mapOfUniCodes.put(Caterpillar.class,    "\uD83D\uDC1B");
        mapOfUniCodes.put(Grass.class,          "\uD83C\uDF3F");
    }

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



    public static String getUniCode(Class<? extends Entity> classOfEntity){
        return mapOfUniCodes.get(classOfEntity);
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
