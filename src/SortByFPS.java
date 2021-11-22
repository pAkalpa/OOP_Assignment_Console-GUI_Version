import java.util.Comparator;

public class SortByFPS implements Comparator<Formula1Driver> {
    /**
     * This Overridden Method compare driver's first position count
     *
     * @param o1 - Driver 1 First Position Count
     * @param o2 - Driver 2 First Position Count
     * @return - Largest First Position Count
     */
    @Override
    public int compare(Formula1Driver o1, Formula1Driver o2) {
        if (o2.getFirstPositionCount() == o1.getFirstPositionCount()) {
            return (int) (o2.getCurrentPoints() - o1.getCurrentPoints());
        }
        return o2.getFirstPositionCount() - o1.getFirstPositionCount();
    }
}
