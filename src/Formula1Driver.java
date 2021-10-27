import java.util.ArrayList;

public class Formula1Driver extends Driver{
    private static Formula1Driver f1D;
    //    firstPositionCount, secondPositionCount, thirdPositionCount, raceCountPerSeason, seasonYear
    private int firstPositionCount;
    private int secondPositionCount;
    private int thirdPositionCount;
    private int seasonYear;
    private int raceCount;
    private int currentPoints;
    private int seasonCount;

    private static void SaveArray() {
        ArrayList <int[]> f1 = new ArrayList<>();
        int[] personalStatArray = {f1D.seasonYear,f1D.firstPositionCount, f1D.secondPositionCount, f1D.thirdPositionCount,f1D.raceCount};
        f1.add(personalStatArray);
    }
}
