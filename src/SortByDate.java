import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class SortByDate implements Comparator<RaceData> {
    /**
     * This Overridden compare method compares race dates
     *
     * @param o1 - race date 1
     * @param o2 - race date 2
     * @return - smallest date
     */
    @Override
    public int compare(RaceData o1, RaceData o2) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(o1.getRaceDate(), format).compareTo(LocalDate.parse(o2.getRaceDate(), format));
    }
}
