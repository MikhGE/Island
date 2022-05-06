import IslandPackage.Island;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IslandApplication {

    public static void main(String[] args) {

        ScheduledExecutorService sheduledService = Executors.newScheduledThreadPool(1);
        Island island = new Island(2, 2);
        sheduledService.scheduleAtFixedRate(island,5, 5, TimeUnit.SECONDS);

    }

}
