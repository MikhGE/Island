package IslandPackage;

import IslandPackage.items.Entity;
import IslandPackage.items.Location;
import IslandPackage.items.animals.*;
import IslandPackage.items.plants.Grass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Island {

    private final int height;
    private final int width;
    private final ArrayList<ArrayList<Location>> locations;
    private int SecondsToUpdate = 10;
    private int maxEntityInLocation = 5;
    private Map<Class<? extends Entity>, Map<Entity.Characteristics, ? extends Number>> mapCountEntityInLocation = new HashMap<>();
    private Map<Class<? extends Entity>, Map<Class<? extends Entity>, Integer>> probabilityOfConsumption;

    public Island(int height, int width) {
        this.height = height;
        this.width = width;
        this.locations = new ArrayList<>(this.height);
        for (int i = 0; i < this.height; i++) {
            this.locations.add(new ArrayList<Location>(width));
            for (int j = 0; j < this.width; j++) {
                    this.locations.get(i).add(new Location(maxEntityInLocation, this));
            }

        }

    }
    public void setDefaultMapCountEntityInLocation(){
        mapCountEntityInLocation.put(Wolf.class,        30);
        mapCountEntityInLocation.put(Snake.class,       123);
        mapCountEntityInLocation.put(Fox.class,         50);
        mapCountEntityInLocation.put(Bear.class,        7);
        mapCountEntityInLocation.put(Eagle.class,       166);
        mapCountEntityInLocation.put(Horse.class,       23);
        mapCountEntityInLocation.put(Deer.class,        41);
        mapCountEntityInLocation.put(Rabbit.class,      750);
        mapCountEntityInLocation.put(Hamster.class,     10000);
        mapCountEntityInLocation.put(Goat.class,        107);
        mapCountEntityInLocation.put(Sheep.class,       156);
        mapCountEntityInLocation.put(Kangaroo.class,    149);
        mapCountEntityInLocation.put(Cow.class,         20);
        mapCountEntityInLocation.put(Duck.class,        500);
        mapCountEntityInLocation.put(Caterpillar.class, 10000);
        mapCountEntityInLocation.put(Grass.class,       10000);

        Map characteristics;

        //Wolf characteristics
        mapCountEntityInLocation.put(Wolf.class,       new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Wolf.class);
        characteristics.put(Entity.Characteristics.WEIGHT,              50);
        characteristics.put(Entity.Characteristics.MAXCOUNTINLOCATION,  30);
        characteristics.put(Entity.Characteristics.SPEED,               3);
        characteristics.put(Entity.Characteristics.COUNTNEDDEDFOOD,     8);
        characteristics.put(Entity.Characteristics.COUNTHUNGRYMOVE,     10);

        //Snake characteristics
        mapCountEntityInLocation.put(Snake.class,       new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Snake.class);
        characteristics.put(Entity.Characteristics.WEIGHT,              2);
        characteristics.put(Entity.Characteristics.MAXCOUNTINLOCATION,  123);
        characteristics.put(Entity.Characteristics.SPEED,               1);
        characteristics.put(Entity.Characteristics.COUNTNEDDEDFOOD,     0.3);
        characteristics.put(Entity.Characteristics.COUNTHUNGRYMOVE,     15);

        //Fox characteristics
        mapCountEntityInLocation.put(Fox.class,       new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Fox.class);
        characteristics.put(Entity.Characteristics.WEIGHT,              4);
        characteristics.put(Entity.Characteristics.MAXCOUNTINLOCATION,  50);
        characteristics.put(Entity.Characteristics.SPEED,               3);
        characteristics.put(Entity.Characteristics.COUNTNEDDEDFOOD,     1);
        characteristics.put(Entity.Characteristics.COUNTHUNGRYMOVE,     8);

        //Bear characteristics
        mapCountEntityInLocation.put(Bear.class,       new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Bear.class);
        characteristics.put(Entity.Characteristics.WEIGHT,              250);
        characteristics.put(Entity.Characteristics.MAXCOUNTINLOCATION,  7);
        characteristics.put(Entity.Characteristics.SPEED,               2);
        characteristics.put(Entity.Characteristics.COUNTNEDDEDFOOD,     38);
        characteristics.put(Entity.Characteristics.COUNTHUNGRYMOVE,     15);

        //Eagle characteristics
        mapCountEntityInLocation.put(Eagle.class,       new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Eagle.class);
        characteristics.put(Entity.Characteristics.WEIGHT,              6);
        characteristics.put(Entity.Characteristics.MAXCOUNTINLOCATION,  166);
        characteristics.put(Entity.Characteristics.SPEED,               4);
        characteristics.put(Entity.Characteristics.COUNTNEDDEDFOOD,     1);

        //Horse characteristics
        mapCountEntityInLocation.put(Horse.class,       new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Horse.class);
        characteristics.put(Entity.Characteristics.WEIGHT,              6);
        characteristics.put(Entity.Characteristics.MAXCOUNTINLOCATION,  166);
        characteristics.put(Entity.Characteristics.SPEED,               4);
        characteristics.put(Entity.Characteristics.COUNTNEDDEDFOOD,     1);
        characteristics.put(Entity.Characteristics.COUNTHUNGRYMOVE,     5);


    }

    public void setMapCountEntityInLocation(Map mapCountEntityInLocation){
       this.mapCountEntityInLocation = mapCountEntityInLocation;
    }

    public void setCountEntityInLocationForClass(Class<? extends Entity> entityClass, Integer count){
        mapCountEntityInLocation.put(entityClass, count);
    }

    public Map getMapCountEntityInLocation(){
        return mapCountEntityInLocation;
    }

    public Integer getEntryCountEntityInLocationForClass(Class<? extends Entity> entityClass){
        return mapCountEntityInLocation.get(entityClass);
    }
}
