import java.io.Serializable;

public abstract class Driver implements Serializable {
    private String driverName;
    private String driverLocation;
    private int podiumCount;
    private String teamName;

    public Driver(String driverName, String driverLocation, int podiumCount, String teamName) {
        this.driverName = driverName;
        this.driverLocation = driverLocation;
        this.podiumCount = podiumCount;
        this.teamName = teamName;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public int getPodiumCount() {
        return podiumCount;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
