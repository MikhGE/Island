package IslandPackage.items;

import IslandPackage.Island;
import IslandPackage.items.animals.Animal;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class Location implements Callable<String> {

    private final Island island;
    private final int maxEntityInLocation;
    private ArrayList<Entity> entities;

    public Location(int maxEntityInLocation, Island island) {
        this.island                 = island;
        this.maxEntityInLocation    = maxEntityInLocation;
        this.entities               = new ArrayList<>();
        initializeLocation();
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

    private void initializeLocation() {
        List<Class<? extends Entity>> classes = island.getClasses();
        Map<Class<? extends Entity>, Map<Entity.Characteristics, ? extends Number>> mapCountEntityInLocation = island.getMapCountEntityInLocation();
        int countClasses = classes.size();
        Random randomIndex = new Random();
        for (int i = 0; i < 3; i++) {
            if(entities.size() == maxEntityInLocation)
                break;
            int index = randomIndex.nextInt(countClasses);
            Class entityClass = classes.get(index);
            Map characteristics  = mapCountEntityInLocation.get(entityClass);
            Integer countEntityInLocation = (Integer) characteristics.get(Entity.Characteristics.MAXCOUNTINLOCATION);
            Random randomCountInLocation = new Random();
            int countInLocation = randomCountInLocation.nextInt(countEntityInLocation);
            for (int j = 0; j < countInLocation; j++) {
                if(entities.size() == maxEntityInLocation)
                    break;
                try {
                    Entity entity = (Entity) Class.forName(entityClass.getName()).getDeclaredConstructor().newInstance();
                    entities.add(entity);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
