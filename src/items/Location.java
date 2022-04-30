package items;

import java.util.ArrayList;

public class Location {
    private ArrayList<Entity> entities;

    public Location(int maxEntityInLocation) {
        this.entities = new ArrayList<>(maxEntityInLocation);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity entity){
        this.entities.add(entity);
    }

    public void deleteEntity(Entity entity){
        this.entities.remove(entity);
    }
    public long getSizeEntities(){
        return this.entities.size();
    }
}
