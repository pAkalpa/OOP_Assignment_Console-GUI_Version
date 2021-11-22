import java.util.Comparator;

public class SortByPoints implements Comparator<Formula1Driver> {
    /**
     * This Overridden Method compare Driver's Current Points
     *
     * @param o1 - Driver 1 Current Points
     * @param o2 - Driver 2 Current Points
     * @return - Largest Current Point
     */
    @Override
    public int compare(Formula1Driver o1, Formula1Driver o2) {
        if (o2.getCurrentPoints() == o1.getCurrentPoints()) {
            return o2.getFirstPositionCount() - o1.getFirstPositionCount();
        }
        return Float.compare(o2.getCurrentPoints(), o1.getCurrentPoints());
    }
}
