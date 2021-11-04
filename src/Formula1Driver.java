import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Formula1Driver extends Driver implements Serializable, Comparable<Formula1Driver> {
    private int firstPositionCount; // First Position Count Storing Field
    private int secondPositionCount; // Second Position Count Storing Field
    private int thirdPositionCount; // Third Position Count Storing Field
    private int raceCount; // Race Count storing Field
    private float currentPoints; // Driver Points Storing Field

    /**
     * Formula1Driver Class Constructor
     * @param driverName - Driver Name String for Super Class Constructor
     * @param driverLocation - Driver Location String for Super Class Constructor
     * @param teamName - Team Name String for Super Class Constructor
     * @param firstPositionCount - First Position Count integer
     * @param secondPositionCount - Second Position Count integer
     * @param thirdPositionCount - Third Position Count integer
     * @param raceCount - Race count integer
     * @param currentPoints - Driver's Current Points float
     */
    public Formula1Driver(String driverName, String driverLocation, String teamName, int firstPositionCount, int secondPositionCount, int thirdPositionCount, int raceCount, float currentPoints) {
        super(driverName, driverLocation, teamName);
        this.firstPositionCount = firstPositionCount;
        this.secondPositionCount = secondPositionCount;
        this.thirdPositionCount = thirdPositionCount;
        this.raceCount = raceCount;
        this.currentPoints = currentPoints;
    }

    /**
     * This Method Return Driver's Current Points
     * @return - Current Point float
     */
    public float getCurrentPoints() {
        return currentPoints;
    }

    /**
     * This Method Increment First Position Count by 1
     * @param firstPositionCount - Integer Value always 1
     */
    public void setFirstPositionCount(int firstPositionCount) {
        this.firstPositionCount += firstPositionCount;
    }

    /**
     * This Method Increment Second Positions Count by 1
     * @param secondPositionCount - Integer Value always 1
     */
    public void setSecondPositionCount(int secondPositionCount) {
        this.secondPositionCount += secondPositionCount;
    }

    /**
     * This Method Increment Third Position Count by 1
     * @param thirdPositionCount - Integer Value always 1
     */
    public void setThirdPositionCount(int thirdPositionCount) {
        this.thirdPositionCount += thirdPositionCount;
    }

    /**
     * This Method Increment Race Count by 1
     * @param raceCount - Integer Value always 1
     */
    public void setRaceCount(int raceCount) {
        this.raceCount += raceCount;
    }

    /**
     * This Method Adds Newly Calculated Points
     * @param currentPoints - Integer Value changes according to position Driver take in race
     */
    public void setCurrentPoints(float currentPoints) {
        this.currentPoints += currentPoints;
    }

    /**
     * This Overridden Method compare Points and First Position Count
     * @param temp - Formula1Driver Object
     * @return - Integer Value
     */
    @Override
    public int compareTo(@NotNull Formula1Driver temp) {
        if (temp.getCurrentPoints() == this.currentPoints) {
            return temp.firstPositionCount - this.firstPositionCount;
        } else {
            return (int) ((int) temp.getCurrentPoints() - this.currentPoints);
        }
    }

    /**
     * This Method Print Driver Details as a Table
     */
    public void PrintDriverTable() {
        int podiumCount = firstPositionCount + secondPositionCount + thirdPositionCount; // Sum of first three positions
        // Format of Each Data in the table
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
