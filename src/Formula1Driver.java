import java.io.Serializable;

public class Formula1Driver extends Driver implements Serializable, Comparable<Formula1Driver> {
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

    @Override
    public int compareTo(Formula1Driver temp) {
        if (temp.getCurrentPoints() == this.currentPoints) {
            return temp.firstPositionCount - this.firstPositionCount;
        } else {
            return (int) ((int) temp.getCurrentPoints() - this.currentPoints);
        }
    }

    public void PrintDriverTable() {
        int podiumCount = firstPositionCount + secondPositionCount + thirdPositionCount;
        String tableDName = "| Name         | %-18s |%n";
        String tableNationality = "| Nationality  | %-18s |%n";
        String tableTName = "| Team Name    | %-18s |%n";
        String tableFPC = "| FPC          | %-18d |%n";
        String tableSPC = "| SPC          | %-18d |%n";
        String tableTPC = "| TPC          | %-18d |%n";
        String tablePodium = "| Podium Count | %-18d |%n";
        String tablePRC = "| PRC          | %-18d |%n";
        String tablePts = "| Pts          | %-18.1f |%n";

        System.out.format("+-----------------------------------+%n")
                .format("|           Driver Details          |%n")
                .format("|(FPC = First Position Count)       |%n")
                .format("|(SPC = Second Position Count)      |%n")
                .format("|(TPC = Third Position Count)       |%n")
                .format("|(PRC = Participated Race Count     |%n")
                .format("|(Pts = Current Points              |%n")
                .format("+--------------+--------------------+%n")
                .format(tableDName, super.getDriverName())
                .format(tableNationality, super.getDriverLocation())
                .format(tableTName, super.getTeamName())
                .format(tableFPC, firstPositionCount)
                .format(tableSPC, secondPositionCount)
                .format(tableTPC, thirdPositionCount)
                .format(tablePodium, podiumCount)
                .format(tablePRC, raceCount)
                .format(tablePts, currentPoints)
                .format("+-----------------------------------+%n\n");

    }
}
