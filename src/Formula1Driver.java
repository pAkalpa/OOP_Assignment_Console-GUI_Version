import java.io.Serializable;

public class Formula1Driver extends Driver implements Serializable {
    private static Formula1Driver f1D;
    //    firstPositionCount, secondPositionCount, thirdPositionCount, raceCountPerSeason
    private int firstPositionCount;
    private int secondPositionCount;
    private int thirdPositionCount;
    private int raceCount;
    private float currentPoints;

    public Formula1Driver(String driverName, String driverLocation, int driverAge, String teamName, int firstPositionCount, int secondPositionCount, int thirdPositionCount, int raceCount, float currentPoints) {
        super(driverName, driverLocation, driverAge, teamName);
        this.firstPositionCount = firstPositionCount;
        this.secondPositionCount = secondPositionCount;
        this.thirdPositionCount = thirdPositionCount;
        this.raceCount = raceCount;
        this.currentPoints = currentPoints;
    }

    public int getFirstPositionCount() {
        return firstPositionCount;
    }

    public int getSecondPositionCount() {
        return secondPositionCount;
    }

    public int getThirdPositionCount() {
        return thirdPositionCount;
    }

    public int getRaceCount() {
        return raceCount;
    }

    public float getCurrentPoints() {
        return currentPoints;
    }
}
