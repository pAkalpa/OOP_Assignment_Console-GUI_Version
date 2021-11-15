import java.util.Comparator;

public class SortByFPS implements Comparator<Formula1Driver> {
    @Override
    public int compare(Formula1Driver o1, Formula1Driver o2) {
        return o2.getFirstPositionCount() - o1.getFirstPositionCount();
    }
}
