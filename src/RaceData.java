import java.util.ArrayList;

public class RaceData {
    private String raceDate;
    private ArrayList<String> driverNames = new ArrayList<>();
    private ArrayList<Integer> driverPositions = new ArrayList<>();
    private ArrayList<Integer> driverStartPositions = new ArrayList<>();

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
}
