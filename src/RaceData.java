import java.io.Serializable;
import java.util.ArrayList;

public class RaceData implements Serializable {
    private final String raceDate;
    private final ArrayList<String> driverNames = new ArrayList<>();
    private final ArrayList<Integer> driverFinishPositions = new ArrayList<>();
    private final ArrayList<Integer> driverStartPositions = new ArrayList<>();

    public RaceData(String raceDate) {
        this.raceDate = raceDate;
    }

    public void setDriverNameAndPosition(String driverName, int position) {
        driverNames.add(driverName);
        driverFinishPositions.add(position);
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

    public ArrayList<Integer> getDriverFinishPositions() {
        return driverFinishPositions;
    }

    public ArrayList<Integer> getDriverStartPositions() {
        return driverStartPositions;
    }

    @Override
    public String toString() {
        return raceDate;
    }
}
