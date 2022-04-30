import items.Location;

import java.util.ArrayList;

public class Island {
    private final int height;
    private final int width;
    private final ArrayList<ArrayList<Location>> locations;
    private int SecondsToUpdate = 10;
    private int maxEntityInLocation = 5;

    public Island(int height, int width) {
        this.height = height;
        this.width = width;
        this.locations = new ArrayList<>(this.height);
        for (int i = 0; i < this.height; i++) {
            this.locations.add(new ArrayList<Location>(width));
            for (int j = 0; j < this.width; j++) {
                    this.locations.get(i).add(new Location(maxEntityInLocation));
            }

        }
    }
}
