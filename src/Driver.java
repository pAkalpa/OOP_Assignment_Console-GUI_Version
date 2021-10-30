public abstract class Driver {
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

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(String driverLocation) {
        this.driverLocation = driverLocation;
    }

    public int getPodiumCount() {
        return podiumCount;
    }

    public void setPodiumCount(int podiumCount) {
        this.podiumCount = podiumCount;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
