import java.io.Serializable;

public abstract class Driver implements Serializable {
    String driverName; // Driver Name storing field
    String driverLocation; // Driver Location storing field
    String teamName; // Team Name Storing field

    /**
     * This Method Return Driver Name String
     *
     * @return - Driver Name String
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * This Method Return Driver Location String
     *
     * @return - Driver Location String
     */
    public String getDriverLocation() {
        return driverLocation;
    }

    /**
     * This Method Return Team Name String
     *
     * @return - Team Name String
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * This Method Set Team Name
     *
     * @param teamName - New Team Name String
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
