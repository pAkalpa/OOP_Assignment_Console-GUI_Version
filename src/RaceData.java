import java.io.Serializable;
import java.util.ArrayList;

public class RaceData implements Serializable {
    private final String raceDate;
    private final ArrayList<String> driverNames = new ArrayList<>();
    private final ArrayList<Integer> driverPositions = new ArrayList<>();
    private final ArrayList<Integer> driverStartPositions = new ArrayList<>();

    public RaceData(String raceDate) {
        this.raceDate = raceDate;
    }

    public void setDriverNameAndPosition(String driverName, int position) {
        driverNames.add(driverName);
        driverPositions.add(position);
    }

    public void setStartPosition(int startPosition) {
        driverStartPositions.add(startPosition);
    }

    public String getRaceDate() {
        return raceDate;
    }

    public ArrayList<String> getDriverNames() {
        return driverNames;
    }

    public ArrayList<Integer> getDriverPositions() {
        return driverPositions;
    }

    public ArrayList<Integer> getDriverStartPositions() {
        return driverStartPositions;
    }

    public void removeDriverAndStats(String driverName) {
        if (driverNames.contains(driverName)) {
            int index = 0;
            for (String name : driverNames) {
                if (name.equals(driverName)) {
                    index = driverNames.indexOf(name);
                }
            }
            driverNames.remove(index);
            driverPositions.remove(index);
//            driverStartPositions.remove(index);
        }
    }
}
