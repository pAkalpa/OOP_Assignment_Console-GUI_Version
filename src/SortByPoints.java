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
        return Float.compare(o2.getCurrentPoints(), o1.getCurrentPoints());
    }
}
