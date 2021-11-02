import java.io.Serializable;

public class Formula1Driver extends Driver implements Serializable {
    private static Formula1Driver f1D;
    //    firstPositionCount, secondPositionCount, thirdPositionCount, raceCountPerSeason
    private final int firstPositionCount;
    private final int secondPositionCount;
    private final int thirdPositionCount;
    private final int raceCount;
    private final float currentPoints;

    public Formula1Driver(String driverName, String driverLocation, String teamName, int firstPositionCount, int secondPositionCount, int thirdPositionCount, int raceCount, float currentPoints) {
        super(driverName, driverLocation, teamName);
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
