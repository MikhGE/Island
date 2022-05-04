package IslandPackage;

import IslandPackage.items.Entity;
import IslandPackage.items.Location;
import IslandPackage.items.animals.*;
import IslandPackage.items.plants.Grass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Island implements Runnable{


    public static int SecondsToUpdate = 10;

    private long days = 1L;
    private final int height;
    private final int width;
    private List<List<Location>> locations;
    private List<Location> listOfLocation = new ArrayList<>();
    private List<Future<String>> listOfFuture;
    private int maxEntityInLocation = 1000;
    private List<Class<? extends Entity>> classes = new ArrayList<>();
    private Map<Class<? extends Entity>, Map<Entity.Characteristics, ? extends Number>> mapCountEntityInLocation = new HashMap<>();
    private Map<Class<? extends Entity>, Map<Class<? extends Entity>, Integer>> probabilityOfConsumption;

    public Island(int height, int width) {

        this.height = height;
        this.width  = width;
        initializeClasses();
        setDefaultMapCountEntityInLocation();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        this.locations = new ArrayList<>(this.height);
        for (int h = 0; h < this.height; h++) {
            this.locations.add(new ArrayList<Location>(width));
            for (int w = 0; w < this.width; w++) {
                Location newLocation = new Location(maxEntityInLocation, this, h, w);
                    this.locations.get(h).add(newLocation);
                listOfLocation.add(newLocation);
            }
        }
        try {
            listOfFuture = cachedThreadPool.invokeAll(listOfLocation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initializeClasses(){
        classes.add(Wolf.class);
        classes.add(Snake.class);
        classes.add(Fox.class);
        classes.add(Bear.class);
        classes.add(Eagle.class);
        classes.add(Horse.class);
        classes.add(Deer.class);
        classes.add(Rabbit.class);
        classes.add(Hamster.class);
        classes.add(Goat.class);
        classes.add(Sheep.class);
        classes.add(Kangaroo.class);
        classes.add(Cow.class);
        classes.add(Duck.class);
        classes.add(Caterpillar.class);
        classes.add(Grass.class);
    }

    public List getClasses(){
        return classes;
    }

    public void setDefaultMapCountEntityInLocation(){

        Map characteristics;

        mapCountEntityInLocation.put(Wolf.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Wolf.class);
        setDefaultcharacteristics(characteristics, 50, 30, 8, 3, 10);

        mapCountEntityInLocation.put(Snake.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Snake.class);
        setDefaultcharacteristics(characteristics, 2, 123, 1, 0.3, 15);

        mapCountEntityInLocation.put(Fox.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Fox.class);
        setDefaultcharacteristics(characteristics, 4, 50, 3, 1, 8);

        mapCountEntityInLocation.put(Bear.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Bear.class);
        setDefaultcharacteristics(characteristics, 250, 7, 2, 38, 15);

        mapCountEntityInLocation.put(Eagle.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Eagle.class);
        setDefaultcharacteristics(characteristics, 6, 166, 4, 1, 5);

        mapCountEntityInLocation.put(Horse.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Horse.class);
        setDefaultcharacteristics(characteristics, 300, 23, 3, 45, 5);

        mapCountEntityInLocation.put(Deer.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Deer.class);
        setDefaultcharacteristics(characteristics, 170, 41, 3, 45, 5);

        mapCountEntityInLocation.put(Rabbit.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Rabbit.class);
        setDefaultcharacteristics(characteristics, 3, 750, 3, 26, 4);

        mapCountEntityInLocation.put(Hamster.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Hamster.class);
        setDefaultcharacteristics(characteristics, 0.03, 10000, 1, 0.0075, 3);

        mapCountEntityInLocation.put(Goat.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Goat.class);
        setDefaultcharacteristics(characteristics, 65, 107, 1, 10, 5);

        mapCountEntityInLocation.put(Sheep.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Sheep.class);
        setDefaultcharacteristics(characteristics, 45, 156, 1, 7, 5);

        mapCountEntityInLocation.put(Kangaroo.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Kangaroo.class);
        setDefaultcharacteristics(characteristics, 47, 149, 2, 7, 8);

        mapCountEntityInLocation.put(Cow.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Cow.class);
        setDefaultcharacteristics(characteristics, 350, 20, 1, 53, 4);

        mapCountEntityInLocation.put(Duck.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Duck.class);
        setDefaultcharacteristics(characteristics, 1, 500, 1, 0.15, 4);

        mapCountEntityInLocation.put(Caterpillar.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Caterpillar.class);
        setDefaultcharacteristics(characteristics, 0.01,10000, 1, 0.0025,  1);

        mapCountEntityInLocation.put(Grass.class, new HashMap<>());
        characteristics = mapCountEntityInLocation.get(Grass.class);
        setDefaultcharacteristics(characteristics, 1, 10000, 0, 0, 0);


    }

    private void setDefaultcharacteristics(Map characteristics, double weight, int maxCountInLocation, double speed, double countNeddedFood, double countHungryMove){
        characteristics.put(Entity.Characteristics.WEIGHT,              weight);
        characteristics.put(Entity.Characteristics.MAXCOUNTINLOCATION,  maxCountInLocation);
        characteristics.put(Entity.Characteristics.SPEED,               speed);
        characteristics.put(Entity.Characteristics.COUNTNEDDEDFOOD,     countNeddedFood);
        characteristics.put(Entity.Characteristics.COUNTHUNGRYMOVE,     countHungryMove);
    }

    public void setMapCountEntityInLocation(Map mapCountEntityInLocation){
       this.mapCountEntityInLocation = mapCountEntityInLocation;
    }

    public void setCountEntityInLocationForClass(Class<? extends Entity> entityClass, Map characteristics){
        mapCountEntityInLocation.put(entityClass, characteristics);
    }

    public Map getMapCountEntityInLocation(){
        return mapCountEntityInLocation;
    }

    public Map getEntryCountEntityInLocationForClass(Class<? extends Entity> entityClass){
        return mapCountEntityInLocation.get(entityClass);
    }

    public long getDays(){
        return days;
    }

    @Override
    public void run() {
        days++;
        for (Future<String> stringFuture : listOfFuture) {
            try {
                System.out.println(stringFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
}
