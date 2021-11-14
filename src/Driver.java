import java.io.Serializable;

public abstract class Driver implements Serializable {
    String driverName; // Driver Name storing field
    String driverLocation; // Driver Location storing field
    String teamName; // Team Name Storing field
    int firstPositionCount; // First Position Count Storing Field
    int secondPositionCount; // Second Position Count Storing Field
    int thirdPositionCount; // Third Position Count Storing Field
    int raceCount; // Race Count storing Field
    float currentPoints; // Driver Points Storing Field
}
