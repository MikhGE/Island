package IslandPackage.items;

import java.util.Comparator;
import java.util.Map;

public class StatisticComporator implements Comparator<Map.Entry<Class<? extends Entity>, Integer>> {
    @Override
    public int compare(Map.Entry<Class<? extends Entity>, Integer> o1, Map.Entry<Class<? extends Entity>, Integer> o2) {
        return o1.getValue()>o2.getValue()?1:-1;
    }
}
