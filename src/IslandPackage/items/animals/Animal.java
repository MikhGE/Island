package IslandPackage.items.animals;

import IslandPackage.items.Entity;
import IslandPackage.items.Location;

import java.util.*;

public abstract class Animal extends Entity implements AnimalInterface {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean move(Location newLocation) {
        boolean moveSuccessful = false;
        Location selfLocation   = this.getLocation();
        Location firstLocation  = (selfLocation.compareTo(newLocation)>0)?newLocation:selfLocation;
        Location secondLocation = (selfLocation.compareTo(newLocation)>0)?selfLocation:newLocation;
        synchronized (firstLocation.entitiesLock) {
            synchronized (secondLocation.entitiesLock) {
                if (newLocation.getEntitiesSize() < newLocation.getMaxEntityInLocation()) {
                    newLocation.moveEntity(this);
                    this.setLocation(newLocation);
                    selfLocation.leaveLocation(this);
                    moveSuccessful = true;
                }
            }
        }
        return moveSuccessful;
    }

    @Override
    public void eat() {

        //Получаем текущую статистику локации
        Set<Class<? extends Entity>> setOfClassStatistic = this.getLocation()
                                                                .getStatisticOfLocation()
                                                                .keySet();
        //Получаем карту вероятностей потребления
        Map<Class<? extends Entity>, Integer> probabilityOfConsumption = this.getIsland()
                                                                            .getMapProbabilityOfConsumption()
                                                                            .get(this.getClass());

        //Получаем максимально вероятную жертву
        Optional<Map.Entry<Class<? extends Entity>, Integer>> entrySetForEat = probabilityOfConsumption.entrySet().stream()
                .filter(e->!e.getKey().equals(this.getClass()))
                .filter(e->e.getValue()!=0)
                .filter(e->setOfClassStatistic.contains(e.getKey()))
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).findFirst();

        //Если животное хищник, то нужно убить жертву, поглатить его плоть и восполнить уровень сытости
        if(Predator.class.isAssignableFrom(this.getClass()))
        {

            if(!entrySetForEat.isEmpty()&&entrySetForEat.get().getValue()>0)
                System.out.println(this.getClass().getSimpleName() + " поел " + entrySetForEat.get().getKey().getSimpleName() + "!");
//
        }
        else{
            if(!entrySetForEat.isEmpty()&&entrySetForEat.get().getValue()>0)
                System.out.println(this.getClass().getSimpleName() + " поел " + entrySetForEat.get().getKey().getSimpleName() + "!");
//            else
//                System.out.println(this.getClass().getSimpleName() + " не ест " + entrySetForEat.get().getKey().getSimpleName() + "!");
        }
    }
}
