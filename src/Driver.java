public abstract class Driver {
    private String driverName;
    private int driverLocation;
    private int driverAge;
    private String teamName;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(int driverLocation) {
        this.driverLocation = driverLocation;
    }

    public int getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(int driverAge) {
        this.driverAge = driverAge;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
