import java.io.Serializable;
import java.util.ArrayList;

public class RaceData implements Serializable {
    private final String raceDate; // Race Date Storing Field
    private final ArrayList<String> driverNames = new ArrayList<>(); // Driver Name Storing ArrayList
    private final ArrayList<Integer> driverFinishPositions = new ArrayList<>(); // Driver Finish Positions Storing ArrayList
    private final ArrayList<Integer> driverStartPositions = new ArrayList<>(); // Driver Start Positions Storing ArrayList

    /**
     * This Is RaceData Class Main Constructor
     *
     * @param raceDate - race date as a String
     */
    public RaceData(String raceDate) {
        this.raceDate = raceDate;
    }

    /**
     * This Method Set Driver Name and Finish Position
     *
     * @param driverName - Driver name as a String
     * @param position   - Driver Finish position as an integer
     */
    public void setDriverNameAndPosition(String driverName, int position) {
        driverNames.add(driverName);
        driverFinishPositions.add(position);
    }

    /**
     * This Method Set Driver Start Position
     *
     * @param position - Driver Start position as an integer
     */
    public void setDriverStartPositions(int position) {
        driverStartPositions.add(position);
    }

    /**
     * This Method Returns Stored Race Date as a String
     *
     * @return - Race Date as String
     */
    public String getRaceDate() {
        return raceDate;
    }

    /**
     * This Method Returns Driver Name ArrayList
     *
     * @return - Driver Name ArrayList
     */
    public ArrayList<String> getDriverNames() {
        return driverNames;
    }

    /**
     * This Method Returns Driver Finish Position ArrayList
     *
     * @return - Driver Finish Position ArrayList
     */
    public ArrayList<Integer> getDriverFinishPositions() {
        return driverFinishPositions;
    }

    /**
     * This Method Returns Driver Start Position ArrayList
     *
     * @return - Driver Start Position ArrayList
     */
    public ArrayList<Integer> getDriverStartPositions() {
        return driverStartPositions;
    }

    /**
     * This Overridden toString Method Returns Race Date
     *
     * @return - race Date
     */
    @Override
    public String toString() {
        return raceDate;
    }
}
