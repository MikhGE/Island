package items;

public class Animal extends Entity{
    private long speed;
    private long maximumAmountOfFood;
    private int maxMovesHungry;

    public Animal(String name) {
        super(name);
    }
}
