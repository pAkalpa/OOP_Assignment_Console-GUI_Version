public interface ChampionshipManager {
    String[] teamNameStringArray = {"Mercedes", "Red Bull Racing", "McLaren", "Ferrari", "AlphaTauri", "Aston Martin", "Williams", "Alfa Romeo Racing", "Haas F1 Team"}; // String Array with F1 Teams.
    // Declared all Method Signature's mentioned in Problem
    void mainMenu();
    void CreateNewDriver();
    void DeleteDriver();
    void ChangeTeam();
    void DisplayStatistics();
    void DisplayDriverTable();
    void AddRace();
    void SaveFileOnExit();
    void LoadFileOnStart();
}
