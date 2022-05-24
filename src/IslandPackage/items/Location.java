package IslandPackage.items;

import IslandPackage.Island;
import IslandPackage.items.animals.Animal;
import IslandPackage.items.plants.Plant;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Location implements Callable<Location>, Comparable {

    private final Island island;
    private final int locationH;
    private final int locationW;
    private final long maxEntityInLocation;
    private final CopyOnWriteArrayList<Entity> entities;
    public final Object entitiesLock = new Object();
    private Map<Class<? extends Entity>, Integer> statisticOfLocation = new HashMap<>();

    public Location(int maxEntityInLocation, Island island, int locationH, int locationW) {
        this.island                 = island;
        this.locationH              = locationH;
        this.locationW              = locationW;
        this.maxEntityInLocation    = maxEntityInLocation;
        this.entities               = new CopyOnWriteArrayList<>();
        initializeLocation();
    }

    @Override
    public Location call() throws Exception {

        setStatisticOfLocation();

        for (Entity entity : entities) {
            if(!entity.isDead()){
            if(Animal.class.isAssignableFrom(entity.getClass())){
                int whatToDo = ThreadLocalRandom.current().nextInt(4);
                switch (whatToDo){
                    case (0):
                        ((Animal) entity).eat();
                        break;
                    case (1):
                        Location newLocation = calculateNewLocation(entity.getClass());
                        ((Animal) entity).move(newLocation);
                        break;
                    case (2):
                        ((Animal) entity).multiply();
                        break;
                    case (3):
                        ((Animal) entity).loseSatietyPoints();
                        break;
                    default:
                        continue;
                }
            }
            else if(Plant.class.isAssignableFrom(entity.getClass())){
                ((Plant) entity).growUp();
            }
            }
        }

        return this;
    }

    public void setStatisticOfLocation(){
        statisticOfLocation = new HashMap<>();
        for (Entity entity : entities) {
            if (!entity.isDead()) {
                if (statisticOfLocation.containsKey(entity.getClass())) {
                    statisticOfLocation.put(entity.getClass(), statisticOfLocation.get(entity.getClass()) + 1);
                } else {
                    statisticOfLocation.put(entity.getClass(), 1);
                }
            }
        }
    }

    public Map<Class<? extends Entity>, Integer> getStatisticOfLocation(){
        return statisticOfLocation;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Location))
            throw new NullPointerException("Переданный объект не явяется локацией");
        Location location = (Location) o;
        if(this.getLocationH()>location.getLocationH())
            return 1;
        if(this.getLocationW()>location.getLocationW())
            return 1;

        return -1;
    }

    private void initializeLocation() {
        List<Class<? extends Entity>> classes = island.getClasses();
        Map<Class<? extends Entity>, Map<Entity.Characteristics, ? extends Number>> mapCountEntityInLocation = island.getMapOfCharacteristics();
        int countClasses = classes.size();
        Random randomIndex = new Random();
        for (int i = 0; i < 3; i++) {
            if(entities.size() == maxEntityInLocation)
                break;
            int index = randomIndex.nextInt(countClasses);
            Class entityClass       = classes.get(index);
            Map characteristics     = mapCountEntityInLocation.get(entityClass);
            Integer countEntityInLocation   = (Integer) characteristics.get(Entity.Characteristics.MAXCOUNTINLOCATION);
            Random randomCountInLocation    = new Random();
            int countInLocation = randomCountInLocation.nextInt(countEntityInLocation);
            for (int j = 0; j < countInLocation; j++) {
                if(entities.size() == maxEntityInLocation)
                    break;
                try {
                    Entity entity = (Entity) Class.forName(entityClass.getName()).getDeclaredConstructor().newInstance();
                    entity.setLocation(this);
                    entity.setIsland(island);
                    entity.initializeEntity();
                    entities.add(entity);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public long getEntitiesSize(){
        return entities.stream().filter(entity->!entity.isDead()).count();
    }

    public long getMaxEntityInLocation(){
        return maxEntityInLocation;
    }

    public int getLocationH() {
        return locationH;
    }

    public int getLocationW() {
        return locationW;
    }

    private Location calculateNewLocation(Class clazz){
        Map<Class<? extends Entity>, Map<Entity.Characteristics, ? extends Number>> mapCountEntityInLocation = island.getMapOfCharacteristics();
        Map<Entity.Characteristics, ? extends Number> characteristicsMap = mapCountEntityInLocation.get(clazz);
        int speed = (int) characteristicsMap.get(Entity.Characteristics.SPEED);
        int way = ThreadLocalRandom.current().nextInt(4);
        int currentMoveSpeed = ThreadLocalRandom.current().nextInt(speed) + 1;
        int newLocationW;
        int newLocationH;
        switch (way){
            case(0):
                //Лево
                newLocationW = getLocationW() - currentMoveSpeed;
                newLocationW = (newLocationW<0)?0:newLocationW;
                return island.getLocation(getLocationH(), newLocationW);
            case(1):
                //Вверх
                newLocationH = getLocationH() - currentMoveSpeed;
                newLocationH = (newLocationH<0)?0:newLocationH;
                return island.getLocation(newLocationH, getLocationW());
            case (2):
                //Право
                newLocationW = getLocationW() + currentMoveSpeed;
                newLocationW = (newLocationW>island.getWidth()-1)?island.getWidth()-1:newLocationW;
                return island.getLocation(getLocationH(), newLocationW);
            case (3):
                //Вниз
                newLocationH = getLocationH() + currentMoveSpeed;
                newLocationH = (newLocationH>island.getHeight()-1)?island.getHeight()-1:newLocationH;
                return island.getLocation(newLocationH, getLocationW());
            default:
                break;
        }
        return null;
    }

    public void moveEntity(Entity entity){
        entities.add(entity);
    }

    public void leaveLocation(Entity entity){
        if(entities.contains(entity))
            entities.remove(entity);
    }

    public CopyOnWriteArrayList<Entity> getEntities(){
        return entities;
    }
    @Override
    public String toString() {
        Optional<Map.Entry<Class<? extends Entity>, Integer>> max = statisticOfLocation.entrySet().stream().max(new StatisticComporator());
        String result = "U+2014";
        if (!max.isEmpty()){
            result = Entity.getUniCode(max.get().getKey());
        }
        return result;
    }


}
