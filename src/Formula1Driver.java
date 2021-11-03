import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Formula1Driver extends Driver implements Serializable, Comparable<Formula1Driver> {
    //    firstPositionCount, secondPositionCount, thirdPositionCount, raceCountPerSeason
    private int firstPositionCount;
    private int secondPositionCount;
    private int thirdPositionCount;
    private int raceCount;
    private float currentPoints;

    public Formula1Driver(String driverName, String driverLocation, String teamName, int firstPositionCount, int secondPositionCount, int thirdPositionCount, int raceCount, float currentPoints) {
        super(driverName, driverLocation, teamName);
        this.firstPositionCount = firstPositionCount;
        this.secondPositionCount = secondPositionCount;
        this.thirdPositionCount = thirdPositionCount;
        this.raceCount = raceCount;
        this.currentPoints = currentPoints;
    }

    public float getCurrentPoints() {
        return currentPoints;
    }

    public void setFirstPositionCount(int firstPositionCount) {
        this.firstPositionCount += firstPositionCount;
    }

    public void setSecondPositionCount(int secondPositionCount) {
        this.secondPositionCount += secondPositionCount;
    }

    public void setThirdPositionCount(int thirdPositionCount) {
        this.thirdPositionCount += thirdPositionCount;
    }

    public void setRaceCount(int raceCount) {
        this.raceCount += raceCount;
    }

    public void setCurrentPoints(float currentPoints) {
        this.currentPoints += currentPoints;
    }

    @Override
    public int compareTo(@NotNull Formula1Driver temp) {
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
                .format("|(PRC = Participated Race Count)    |%n")
                .format("|(Pts = Current Points)             |%n")
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
