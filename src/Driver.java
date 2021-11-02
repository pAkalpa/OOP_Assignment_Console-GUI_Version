import java.io.Serializable;

public abstract class Driver implements Serializable {
    private final String driverName;
    private final String driverLocation;
    private String teamName;

    public Driver(String driverName, String driverLocation, String teamName) {
        this.driverName = driverName;
        this.driverLocation = driverLocation;
        this.teamName = teamName;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
