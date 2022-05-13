package IslandPackage.items.animals;

import IslandPackage.items.Entity;
import IslandPackage.items.Location;

import java.util.*;

public abstract class Animal extends Entity implements AnimalInterface {

    private double  countNeddedFood;
    private int     countHungryMove;
    private int     countMoveForHungry;
    private double  satietyStep;

    public Animal() {

    }

    @Override
    public String toString() {
        return Entity.getUniCode(getClass());
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

            if(!entrySetForEat.isEmpty()&&entrySetForEat.get().getValue()>0){
                Optional<Entity> food = this.getLocation().getEntities().stream().filter(entity -> entity.getClass().equals(entrySetForEat.get().getKey())&&!entity.isDead()).findFirst();
                if(!food.isEmpty()){
                    food.get().setDead(true);
                }
            }

//
        }
        else{
            if(!entrySetForEat.isEmpty()&&entrySetForEat.get().getValue()>0){

            }
       }
    }

    @Override
    public void die() {
        setDead(true);
    }

    @Override
    public void initializeEntity() {
        super.initializeEntity();
        Map<Class<? extends Entity>, Map<Characteristics, ? extends Number>> mapOfCharacteristics = this.getIsland().getMapOfCharacteristics();
        Map<Characteristics, ? extends Number> characteristics = mapOfCharacteristics.get(this.getClass());
        setCountNeddedFood((double) characteristics.get(Characteristics.COUNTNEDDEDFOOD));
        setCountHungryMove((int) characteristics.get(Characteristics.COUNTHUNGRYMOVE));
        countMoveForHungry = (int) (getWeight()/getCountNeddedFood());
        satietyStep = countNeddedFood/countMoveForHungry;
    }

    public double getCountNeddedFood() {
        return countNeddedFood;
    }

    public void setCountNeddedFood(double countNeddedFood) {
        this.countNeddedFood = countNeddedFood;
    }

    public int getCountHungryMove() {
        return countHungryMove;
    }

    public void setCountHungryMove(int countHungryMove) {
        this.countHungryMove = countHungryMove;
    }

    public void loseSatietyPoints(){
        if(countNeddedFood > 0.0)
            countNeddedFood-=satietyStep;
        else {
            if (countHungryMove>0){
            countHungryMove--;
            } else{
                die();
            }
        }
    }
}
