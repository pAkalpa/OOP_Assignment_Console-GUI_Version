import java.util.Comparator;

public class SortByPoints implements Comparator<Formula1Driver> {
    @Override
    public int compare(Formula1Driver o1, Formula1Driver o2) {
        return Float.compare(o2.getCurrentPoints(), o1.getCurrentPoints());
    }
}
