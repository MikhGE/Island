package IslandPackage.items.animals;

import IslandPackage.items.Entity;
import IslandPackage.items.Location;

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
        if(Predator.class.isAssignableFrom(this.getClass()))
        {
            System.out.println(this.getClass().getSimpleName() + " поел!");
        }
        else{
            System.out.println(this.getClass().getSimpleName() + " поел!");
        }
    }
}
