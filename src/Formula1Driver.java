public class Formula1Driver extends Driver{
    private static Formula1Driver f1D;
    //    firstPositionCount, secondPositionCount, thirdPositionCount, raceCountPerSeason, seasonYear
    private int firstPositionCount;
    private int secondPositionCount;
    private int thirdPositionCount;
    private int raceCount;
    private int currentPoints;
    private int seasonCount;

    public Formula1Driver(String driverName, String driverLocation, int driverAge, String teamName) {
        super(driverName, driverLocation, driverAge, teamName);
    }

    private static void SaveArray() {
        int[] personalStatArray = {f1D.firstPositionCount, f1D.secondPositionCount, f1D.thirdPositionCount,f1D.raceCount};
    }

    public int getFirstPositionCount() {
        return firstPositionCount;
    }

    public void setFirstPositionCount(int firstPositionCount) {
        this.firstPositionCount = firstPositionCount;
    }

    public int getSecondPositionCount() {
        return secondPositionCount;
    }

    public void setSecondPositionCount(int secondPositionCount) {
        this.secondPositionCount = secondPositionCount;
    }

    public int getThirdPositionCount() {
        return thirdPositionCount;
    }

    public void setThirdPositionCount(int thirdPositionCount) {
        this.thirdPositionCount = thirdPositionCount;
    }

    public int getRaceCount() {
        return raceCount;
    }

    public void setRaceCount(int raceCount) {
        this.raceCount = raceCount;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public int getSeasonCount() {
        return seasonCount;
    }

    public void setSeasonCount(int seasonCount) {
        this.seasonCount = seasonCount;
    }
}
