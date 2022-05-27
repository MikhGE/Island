package IslandPackage;

import IslandPackage.items.Entity;
import IslandPackage.items.Location;
import IslandPackage.items.animals.*;
import IslandPackage.items.plants.Grass;

import java.util.*;
import java.util.concurrent.*;

public class Island implements Runnable{


    public static int SecondsToUpdate = 10;

    //Количество пройденных дней
    private long days   = 1L;
    //Высота и ширина острова
    private final int height;
    private final int width;
    //Матрица локаций для организации вывода и перемещения сущностей
    private List<List<Location>> locations;
    //Список локаций для запуска пула потоков
    private List<Location> listOfLocation = new ArrayList<>();
    //Список задач
    private List<Future<Location>> listOfFuture;
    //Пул потоков
    private ExecutorService cachedThreadPool;
    //Максимальное количество сущностей в локации
    private int maxEntityInLocation = 1000;
    //Список классов сущностей
    private List<Class<? extends Entity>> classes = new ArrayList<>();
    //Карта характеристик классов
    private Map<Class<? extends Entity>, Map<Entity.Characteristics, ? extends Number>> mapOfCharacteristics = new HashMap<>();
    //Карта вероятностей классов
    private Map<Class<? extends Entity>, Map<Class<? extends Entity>, Integer>> MapProbabilityOfConsumption = new HashMap<>();

    public Island(int height, int width) {

        this.height = height;
        this.width  = width;
        initializeClasses();
        setDefaultMapOfCharacteristics();
        setDefaultMapOfProbabilityOfConsumption();
        cachedThreadPool = Executors.newCachedThreadPool();
        this.locations = new ArrayList<>(this.height);
        for (int h = 0; h < this.height; h++) {
            this.locations.add(new ArrayList<>(width));
            for (int w = 0; w < this.width; w++) {
                Location newLocation = new Location(maxEntityInLocation, this, h, w);
                this.locations.get(h).add(newLocation);
                listOfLocation.add(newLocation);
            }
        }
        showIsland();
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

    public void setDefaultMapOfCharacteristics(){

        Map characteristics;

        mapOfCharacteristics.put(Wolf.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Wolf.class);
        setDefaultcharacteristics(characteristics, 50, 30, 3, 8, 10);

        mapOfCharacteristics.put(Snake.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Snake.class);
        setDefaultcharacteristics(characteristics, 2, 123, 1, 0.3, 15);

        mapOfCharacteristics.put(Fox.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Fox.class);
        setDefaultcharacteristics(characteristics, 4, 50, 3, 1, 8);

        mapOfCharacteristics.put(Bear.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Bear.class);
        setDefaultcharacteristics(characteristics, 250, 7, 2, 38, 15);

        mapOfCharacteristics.put(Eagle.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Eagle.class);
        setDefaultcharacteristics(characteristics, 6, 166, 4, 1, 5);

        mapOfCharacteristics.put(Horse.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Horse.class);
        setDefaultcharacteristics(characteristics, 300, 23, 3, 45, 5);

        mapOfCharacteristics.put(Deer.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Deer.class);
        setDefaultcharacteristics(characteristics, 170, 41, 3, 26, 4);

        mapOfCharacteristics.put(Rabbit.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Rabbit.class);
        setDefaultcharacteristics(characteristics, 3, 750, 3, 26, 4);

        mapOfCharacteristics.put(Hamster.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Hamster.class);
        setDefaultcharacteristics(characteristics, 0.03, 10000, 1, 0.0075, 3);

        mapOfCharacteristics.put(Goat.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Goat.class);
        setDefaultcharacteristics(characteristics, 65, 107, 1, 10, 5);

        mapOfCharacteristics.put(Sheep.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Sheep.class);
        setDefaultcharacteristics(characteristics, 45, 156, 1, 7, 5);

        mapOfCharacteristics.put(Kangaroo.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Kangaroo.class);
        setDefaultcharacteristics(characteristics, 47, 149, 2, 7, 8);

        mapOfCharacteristics.put(Cow.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Cow.class);
        setDefaultcharacteristics(characteristics, 350, 20, 1, 53, 4);

        mapOfCharacteristics.put(Duck.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Duck.class);
        setDefaultcharacteristics(characteristics, 1, 500, 1, 0.15, 4);

        mapOfCharacteristics.put(Caterpillar.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Caterpillar.class);
        setDefaultcharacteristics(characteristics, 0.01,10000, 1, 0.0025,  1);

        mapOfCharacteristics.put(Grass.class, new HashMap<>());
        characteristics = mapOfCharacteristics.get(Grass.class);
        setDefaultcharacteristics(characteristics, 1, 10000, 0, 0, 0);

    }

    private void setDefaultcharacteristics(Map characteristics, double weight, int maxCountInLocation, int speed, double countNeddedFood, int countHungryMove){
        characteristics.put(Entity.Characteristics.WEIGHT,              weight);
        characteristics.put(Entity.Characteristics.MAXCOUNTINLOCATION,  maxCountInLocation);
        characteristics.put(Entity.Characteristics.SPEED,               speed);
        characteristics.put(Entity.Characteristics.COUNTNEDDEDFOOD,     countNeddedFood);
        characteristics.put(Entity.Characteristics.COUNTHUNGRYMOVE,     countHungryMove);
    }

    public void setDefaultMapOfProbabilityOfConsumption(){

        Map probabilityOfConsumption;

        MapProbabilityOfConsumption.put(Wolf.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Wolf.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,10,10,0,10,30,40,70,90,60,70,20,30,80,0,0);

        MapProbabilityOfConsumption.put(Snake.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Snake.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,50,90,0,0,0,0,50,90,0);

        MapProbabilityOfConsumption.put(Fox.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Fox.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,20,0,0,10,0,5,70,90,20,20,5,0,80,0,0);

        MapProbabilityOfConsumption.put(Bear.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Bear.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 20,30,20,0,30,70,80,80,90,70,70,60,75,80,0,0);

        MapProbabilityOfConsumption.put(Eagle.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Eagle.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,50,0,0,0,0,0,90,90,0,0,0,0,85,20,0);

        MapProbabilityOfConsumption.put(Horse.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Horse.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100);

        MapProbabilityOfConsumption.put(Deer.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Deer.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100);

        MapProbabilityOfConsumption.put(Rabbit.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Rabbit.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100);

        MapProbabilityOfConsumption.put(Hamster.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Hamster.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100);

        MapProbabilityOfConsumption.put(Goat.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Goat.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100);

        MapProbabilityOfConsumption.put(Sheep.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Sheep.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100);

        MapProbabilityOfConsumption.put(Kangaroo.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Kangaroo.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100);

        MapProbabilityOfConsumption.put(Cow.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Cow.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100);

        MapProbabilityOfConsumption.put(Duck.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Duck.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,50,100);

        MapProbabilityOfConsumption.put(Caterpillar.class, new HashMap<>());
        probabilityOfConsumption = MapProbabilityOfConsumption.get(Caterpillar.class);
        setDefaultProbabilityOfConsumption(probabilityOfConsumption, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,50,100);

    }

    private void setDefaultProbabilityOfConsumption(Map probabilityOfConsumption, int...probability){
        probabilityOfConsumption.put(Wolf.class,        probability[0]);
        probabilityOfConsumption.put(Snake.class,       probability[1]);
        probabilityOfConsumption.put(Fox.class,         probability[2]);
        probabilityOfConsumption.put(Bear.class,        probability[3]);
        probabilityOfConsumption.put(Eagle.class,       probability[4]);
        probabilityOfConsumption.put(Horse.class,       probability[5]);
        probabilityOfConsumption.put(Deer.class,        probability[6]);
        probabilityOfConsumption.put(Rabbit.class,      probability[7]);
        probabilityOfConsumption.put(Hamster.class,     probability[8]);
        probabilityOfConsumption.put(Goat.class,        probability[9]);
        probabilityOfConsumption.put(Sheep.class,       probability[10]);
        probabilityOfConsumption.put(Kangaroo.class,    probability[11]);
        probabilityOfConsumption.put(Cow.class,         probability[12]);
        probabilityOfConsumption.put(Duck.class,        probability[13]);
        probabilityOfConsumption.put(Caterpillar.class, probability[14]);
        probabilityOfConsumption.put(Grass.class,       probability[15]);
    }

    public void setMapOfProbabilityOfConsumption(Map MapProbabilityOfConsumption){
        this.MapProbabilityOfConsumption = MapProbabilityOfConsumption;
    }

    public Map<Class<? extends Entity>, Map<Class<? extends Entity>, Integer>> getMapProbabilityOfConsumption(){
        return this.MapProbabilityOfConsumption;
    }
    public void setMapOfCharacteristics(Map mapOfCharacteristics){
       this.mapOfCharacteristics = mapOfCharacteristics;
    }

    public void setCharacteristicsForClass(Class<? extends Entity> entityClass, Map characteristics){
        mapOfCharacteristics.put(entityClass, characteristics);
    }

    public Map getMapOfCharacteristics(){
        return mapOfCharacteristics;
    }

    public Map getEntryCharacteristicsForClass(Class<? extends Entity> entityClass){
        return mapOfCharacteristics.get(entityClass);
    }

    public long getDays(){
        return days;
    }

    public Location getLocation(int locationH, int locationW){
        return locations.get(locationH).get(locationW);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void run() {
        days++;
        showIsland();
        try {
            listOfFuture = cachedThreadPool.invokeAll(listOfLocation);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showIsland(){
        String statisticString = "День: " + days + " Общее количество сущностей: " + Entity.getCountEntities() + " Общее количество умерших сущностей: " + Entity.getCountDeadEntities() + " Общее количество родившихся сущностей:" + Entity.getCountBornEntities();
        System.out.println(statisticString);
        statisticString = "";
        for (List<Location> stringlocations : locations) {
            String statisticOfLocations = "";
            for (Location location : stringlocations) {
                statisticOfLocations+=location.toString();
            }
            statisticString+=statisticOfLocations + "\n";
        }
        System.out.println(statisticString);
    }
}
