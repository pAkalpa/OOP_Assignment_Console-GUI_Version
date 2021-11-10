import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager {
    private static boolean isValid = true; // loop control variable of mainMenu method
    private static final Scanner scanner = new Scanner(System.in); // Create scanner object from Scanner Class
    private static ArrayList<Formula1Driver> driver = new ArrayList<>(); // Empty ArrayList declared for store Formula1Driver class
    private static List<String> teamNameList = new ArrayList<>(); // Empty ArrayList declared for store F1 Team Names
    private static List<String> raceDates = new ArrayList<>(); // Empty Arraylist declared for store Previous Race Dates
    private final ArrayList<Integer> positionList = new ArrayList<>(); // Empty ArrayList declared for store Race positions assigned to drivers

    /**
     * Main Method Invoke four methods and Create Object
     *
     * @param args - None
     */
    public static void main(String[] args) {
        Formula1ChampionshipManager F1CM = new Formula1ChampionshipManager(); // Create Formula1ChampionshipManager object to access non-static methods
        Collections.addAll(teamNameList, teamNameStringArray); // Add Team Names to Arraylist from Team Name String Array
        PrintAsciiArt(true); // Print Ascii Art on Program Load
        F1CM.LoadFileOnStart(); // Load save file on Program Load
        F1CM.mainMenu(); // Invoke mainMenu Method
        F1CM.SaveFileOnExit(); // Save data to file on Program Exit
        PrintAsciiArt(false); // Print Ascii Art on Program Exit
    }

    /**
     * This Method Display Formula 1 Championship Manager Program Console Main Menu
     */
    @Override
    public void mainMenu() {
        while (isValid) { //
            String menuItems = "\n------------------------------------------------------------"
                    .concat("\n|  _    _                                                  |")
                    .concat("\n|  \\`../ |o_..__   Formula1 Championship Manager Program   |")
                    .concat("\n|`.,(_)______(_).>                                         |")
                    .concat("\n|----------------------------------------------------------|")
                    .concat("\n| 100 or CND |\tCreate a New Driver                        |")
                    .concat("\n| 101 or DAD |\tDelete a Driver                            |")
                    .concat("\n| 102 or CTT |\tChange the Team(Manufacturer)              |")
                    .concat("\n| 103 or DAS |\tDisplay Statistics                         |")
                    .concat("\n| 104 or DDT |\tDisplay F1 Driver Table                    |")
                    .concat("\n| 105 or ANR |\tAdd Race                                   |")
                    .concat("\n| 999 or EXT |\tExit the Program                           |")
                    .concat("\n------------------------------------------------------------")
                    .concat("\nChoose Option: "); // Console Main Menu String
            System.out.print(menuItems);
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                String code = scanner.next().toUpperCase(); // get user input and store in code variable
                mainMenuInputValidation(code); // Invoke menuInputValidation method and parse code as argument
            }
        }
        System.out.println("Exiting Program...");
    }

    /**
     * This Method Validates and Invoke methods according to Main Menu input
     *
     * @param code - user input taking from the main menu
     */
    private void mainMenuInputValidation(String code) {
        String[] validInputArray = {"100", "CND", "101", "DAD", "102", "CTT", "103", "DAS", "104", "DDT", "105", "ANR", "999", "EXT"};
        int index;
        List<String> validInputList = Arrays.asList(validInputArray);

        if (validInputList.contains(code)) {
            index = validInputList.indexOf(code);
            switch (index) { // switch case of valid input index
                // 100 or CND
                case 0, 1 -> CreateNewDriver();
                //101 or DAD
                case 2, 3 -> DeleteDriver();
                //102 or CTT
                case 4, 5 -> ChangeTeam();
                //103 or DAS
                case 6, 7 -> DisplayStatistics();
                //104 or DDT
                case 8, 9 -> DisplayDriverTable();
                //105 or ANR
                case 10, 11 -> AddRace();
                //999 or EXT
                case 12, 13 -> isValid = false;
            }
        } else {
            isValid = true;
            System.out.println("Invalid Input! Try Again.");
        }
    }

    /**
     * This Method Creates a new Driver and save it in driver arraylist
     */
    @Override
    public void CreateNewDriver() {
        String driverName, teamName, driverLocation, option; // Declare String Variables to store user inputs
        int firstPositionCount, secondPositionCount, thirdPositionCount, raceCount; // Declare Integer Variables to store user inputs
        float currentPoints; // Declare Float Variable to store Points
        boolean flag; // Declare boolean Variable for validation
        int totalPositionsPerSeason = 30;

        System.out.println("\nCreate New Driver");

        if (driver.size() != 0) { // Validate for Driver's Availability
            DisplayDriverNames(); // Invoke DisplayDriverNames for display all driver's added
        }

        do {
            driverName = DriverNameValidator(); // Invoke DriverNameValidator for take driver name from user and validate it
            driverLocation = DriverLocationValidator(); // Invoke DriverLocationValidator for take driver Location and validate it
            teamName = CarConstructorValidator(); // Invoke CarConstructorValidator for take Team Name and validate it
            firstPositionCount = PositionCountValidator(1, totalPositionsPerSeason); // Invoke PositionCountValidator for take First Position Count and validate it
            secondPositionCount = PositionCountValidator(2, (totalPositionsPerSeason - firstPositionCount)); // Invoke PositionCountValidator for take Second Position Count and validate it
            thirdPositionCount = PositionCountValidator(3, (totalPositionsPerSeason - (firstPositionCount + secondPositionCount)));// Invoke PositionCountValidator for take Third Position Count and validate it
            int oldRaceCount = firstPositionCount + secondPositionCount + thirdPositionCount; // Minimum race count can have for Driver
            raceCount = DriverRaceCountValidator(oldRaceCount); // Invoke DriverRaceCountValidator for take Race Count and validate it;
            int oldRacePoint = (firstPositionCount * 25) + (secondPositionCount * 18) + (thirdPositionCount * 15); // Minimum points can have for Driver
            currentPoints = DriverPointsValidator(oldRacePoint); // Invoke DriverPointsValidator for take Points and validate it;

            driver.add(new Formula1Driver(driverName, driverLocation, teamName, firstPositionCount, secondPositionCount, thirdPositionCount, raceCount, currentPoints)); // save Formula1Driver object in driver Arraylist

            do { // Validate for exit function
                String inputRegexPattern = "[YN]+";
                System.out.print("\nDo You Want to create Another Driver?(Y/n) ");
                option = scanner.next().toUpperCase();
                flag = option.matches(inputRegexPattern);
                if (!flag) System.out.println("Invalid Input! Try Again.");
            } while (!flag);
        } while (!option.equals("N"));

        BackToMainMenu();
    }

    /**
     * This Method Deletes Driver and Team
     */
    @Override
    public void DeleteDriver() {
        int index; // Declared Integer variable for store user input
        String option; // Declared String variable for store user input on exit validation
        boolean flag; // Declared Boolean variable for store valid user input
        DisplayDriverNames(); // Invoke DisplayDriverMethod for show drivers added
        if (driver.size() != 0) {
            do {
                do { // Driver Selection and validation
                    System.out.print("\nEnter Driver Index to Delete Driver(Also Deletes Driver Team) or Enter " + driver.size() + " to Go Back to Menu : ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid Input! Try Again.\nEnter Driver Index to Delete Driver(Also Deletes Driver Team) or Enter " + driver.size() + " to Go Back to Menu : ");
                        scanner.next();
                    }
                    index = scanner.nextInt();
                    if ((index > driver.size()) || (index < 0)) {
                        System.out.println("Invalid Input! Try Again.");
                    }
                } while ((index > driver.size()) || (index < 0));
                if (index == driver.size()) {
                    break;
                }
                teamNameList.add(driver.get(index).getTeamName()); // add team name of the selected driver
                driver.remove(index); // Remove driver from arraylist
                System.out.println("Driver and Team Successfully Deleted.");
                do { // Exit validation
                    String inputRegexPattern = "[YN]+";
                    System.out.print("Do You Want to Delete Another Driver?(Y/n) ");
                    option = scanner.next().toUpperCase();
                    flag = option.matches(inputRegexPattern);
                    if (!flag) System.out.println("Invalid Input! Try Again.");
                    if (option.equals("Y")) {
                        DisplayDriverNames();
                    }
                } while (!flag);
            } while (!option.equals("N"));
        } else {
            System.out.println("Try Adding Driver using Option in Main Menu -> Create A New Driver");
        }
        BackToMainMenu(); // Invoke BackToMainMenu to print back to menu message
    }

    /**
     * This Method Changes Team
     */
    @Override
    public void ChangeTeam() {
        int index;
        int teamIndex;
        String option;
        boolean flag;
        DisplayDriverNames();
        if (!driver.isEmpty()) {
            do {
                do { // Select Driver validation
                    System.out.print("\nEnter Driver Index to Change Team or Enter " + driver.size() + " to Go Back to Menu : ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid Input! Try Again.\nEnter Driver Index to Change Team or Enter " + driver.size() + " to Go Back to Menu : ");
                        scanner.next();
                    }
                    index = scanner.nextInt();
                    if ((index > driver.size()) || (index < 0)) {
                        System.out.println("Invalid Input! Try Again.");
                    }
                } while ((index > driver.size()) || (index < 0));
                if (index == driver.size()) { // Exit function
                    break;
                }

                System.out.println("Please Select Car Constructor/Team from the List");
                DisplayCarConstructorNames();
                do { // Select Team Name Validation
                    System.out.print("Select Preferred Team(index): ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid Input! Try Again.\nSelect Preferred Team(index): ");
                        scanner.next();
                    }
                    teamIndex = scanner.nextInt();
                    if ((teamIndex < 0) || (teamIndex > teamNameList.size() - 1)) {
                        System.out.println("Invalid Input! Try Again.");
                    }
                } while ((teamIndex < 0) || (teamIndex > teamNameList.size() - 1));

                String selectedTeamName = teamNameList.get(teamIndex); // store team name to variable from team name array list
                teamNameList.remove(selectedTeamName); // remove selected team name from list
                teamNameList.add(driver.get(index).getTeamName()); // add selected driver team to team name arraylist
                driver.get(index).setTeamName(selectedTeamName); // set selected team name to selected driver

                do { // Exit validation
                    String inputRegexPattern = "[YN]+";
                    System.out.print("Do You Want to Change Team of Another Driver?(Y/n) ");
                    option = scanner.next().toUpperCase();
                    flag = option.matches(inputRegexPattern);
                    if (!flag) System.out.println("Invalid Input! Try Again.");
                    if (flag) {
                        DisplayDriverNames();
                    }
                } while (!flag);
            } while (!option.equals("N"));
        } else {
            System.out.println("Try Adding Driver using Option in Main Menu -> Create A New Driver");
        }
        BackToMainMenu();
    }

    /**
     * This Method Display Driver's Statistics
     */
    public void DisplayStatistics() {
        int index;
        String option;
        boolean flag;
        System.out.println("Driver Statistics");
        if (driver.isEmpty()) { // Display Empty message if driver arrayList empty
            System.out.println("No Driver Details Found!. Enter Driver Details from Menu \n\t\t\t¯\\_(ツ)_/¯");
        } else {
            do {
                DisplayDriverNames();
                do { // Driver Selection and validation
                    System.out.print("\nEnter Driver Index to View Driver Details or Enter " + driver.size() + " to Go Back to Menu : ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid Input! Try Again.\nEnter Driver Index to View Driver Details or Enter " + driver.size() + " to Go Back to Menu : ");
                        scanner.next();
                    }
                    index = scanner.nextInt();
                    if ((index > driver.size()) || (index < 0)) {
                        System.out.println("Invalid Input! Try Again.");
                    }
                } while ((index > driver.size()) || (index < 0));
                if (index == driver.size()) {
                    break;
                }

                driver.get(index).PrintDriverTable(); // Invoke PrintDriverTable method for selected driver

                do { // Exit validation
                    String inputRegexPattern = "[YN]+";
                    System.out.print("Do You Want to View Details of Another Driver?(Y/n) ");
                    option = scanner.next().toUpperCase();
                    flag = option.matches(inputRegexPattern);
                    if (!flag) System.out.println("Invalid Input! Try Again.");
                } while (!flag);
            } while (!option.equals("N"));
        }
        BackToMainMenu();
    }

    /**
     * This Method Display Driver Table
     */
    @Override
    public void DisplayDriverTable() {
        if (!driver.isEmpty()) { // Display Table if driver arrayList not empty
            Collections.sort(driver);
            String tableData = "| %3d | %-18s |     %-3s     | %-17s |  %-4.1f |%n";
            System.out.format("+----------------------------------------------------------------------+%n")
                    .format("|                         Formula 1 Driver Table                       |%n")
                    .format("+-----+--------------------+-------------+-------------------+---------+%n")
                    .format("| POS |       DRIVER       | NATIONALITY |        CAR        |   PTS   |%n")
                    .format("+-----+--------------------+-------------+-------------------+---------|%n");
            for (Formula1Driver fDriver : driver) {
                System.out.format(tableData, (driver.indexOf(fDriver) + 1), fDriver.getDriverName(), fDriver.getDriverLocation(), fDriver.getTeamName(), fDriver.getCurrentPoints());
            }
            System.out.format("+----------------------------------------------------------------------+%n");
        } else {
            System.out.println("Try Adding Driver using Option in Main Menu -> Create A New Driver");
        }
        BackToMainMenu();
    }

    /**
     * This Method Adds Race to Program
     */
    @Override
    public void AddRace() {
        if (raceDates.size() <= 30) {
            if (!driver.isEmpty()) {
                String option;
                String dateString; // store user input date
                boolean flag;
                boolean isValidDate; // boolean variable to store validity of date
                boolean isPreviousRace = false; // boolean variable to store previous date validity
                do {
                    DisplayOldRaceDates();
                    do { // Race Date Validation
                        int season = Calendar.getInstance().get(Calendar.YEAR);
                        System.out.print("\nEnter Date of the Race for Season " + season + " yyyy/MM/dd(Ex " + season + "/02/23): ");
                        dateString = scanner.next();
                        isValidDate = DateValidator(dateString, season);
                        if (isValid) { // if valid check for previous entries
                            isPreviousRace = raceDates.contains(dateString);
                            if (isPreviousRace) {
                                System.out.println("Race Already Added! Try Again.");
                            }
                        }
                        if (dateString.length() == 10 && !isValidDate) { // validation for invalid dates
                            System.out.println("Invalid Date! Try Again.");
                        }
                        if (dateString.length() != 10) { // validation for invalid length of user inputs
                            System.out.println("Invalid Input! Try Again.");
                        }
                    } while (dateString.length() != 10 || !isValidDate || isPreviousRace);

                    raceDates.add(dateString); // store race date in raceDate arraylist

                    for (Formula1Driver formula1Driver : driver) { // Invoke RaceDataUpdater for each driver
                        RaceDataUpdater(formula1Driver);
                    }
                    positionList.clear();

                    do { // Exit validation
                        String inputRegexPattern = "[YN]+";
                        System.out.print("Do You Want to Add another Race?(Y/n) ");
                        option = scanner.next().toUpperCase();
                        flag = option.matches(inputRegexPattern);
                        if (!flag) System.out.println("Invalid Input! Try Again.");
                    } while (!flag);
                } while (!option.equals("N"));
            } else {
                System.out.println("Try Adding Driver using Option in Main Menu -> Create A New Driver");
            }
        } else {
            System.out.println("Maximum Races per Season is 30");
        }
        BackToMainMenu();
    }

    /**
     * This Method save ArrayLists, Lists to file
     */
    @Override
    public void SaveFileOnExit() {
        try {
//            System.out.println("File Saving....");
            File file = new File("./saveData/"); // create new File object

            if (!file.exists()) {
                Files.createDirectory(Path.of("./saveData/"));
            }

            String saveFileName = "saveData.dat";
            String saveFilePath = "./saveData/" + saveFileName;

            FileOutputStream saveDataFile = new FileOutputStream(saveFilePath);
            ObjectOutputStream saveFile = new ObjectOutputStream(saveDataFile);

            // Save all lists as objects on save file
            saveFile.writeObject(teamNameList);
            saveFile.writeObject(driver);
            saveFile.writeObject(raceDates);

//            System.out.println("File Saved Successfully!");

        } catch (IOException e) {
            System.out.println("Oops! Something went Wrong.");
        }
    }

    /**
     * This Method Load Saved Data to Program
     */
    @Override
    public void LoadFileOnStart() {
        try {
            File file = new File("./saveData/saveData.dat");

            if (!file.exists()) {
                System.out.println("No Save Data Found!\n\t¯\\_(ツ)_/¯");
            } else {
//                System.out.println("File Loading....");
                String savedFileName = "./saveData/saveData.dat";

                FileInputStream savedDataFile = new FileInputStream(savedFileName);
                ObjectInputStream savedFile = new ObjectInputStream(savedDataFile);

                // Empty All the Lists before load save file
                driver.clear();
                teamNameList.clear();
                raceDates.clear();

                // Read Saved Objects from file
                Object list1 = savedFile.readObject();
                teamNameList = CastList(list1);
                Collections.sort(teamNameList);
                Object list2 = savedFile.readObject();
                driver = CastArrayList(list2);
                Object list3 = savedFile.readObject();
                raceDates = CastList(list3);
            }
        } catch (Exception e) {
            System.out.println("Oops! Something went Wrong.");
        }
    }

    /**
     * This Method Cast Lists
     *
     * @param obj - List
     * @return - Object cast into List
     */
    @SuppressWarnings("unchecked")
    private List<String> CastList(Object obj) {
        return (List<String>) obj;
    }

    /**
     * This Method Cast ArrayLists
     *
     * @param obj - ArrayList
     * @return - Object cast into ArrayList
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Formula1Driver> CastArrayList(Object obj) {
        return (ArrayList<Formula1Driver>) obj;
    }

    /**
     * This Method Display Names of the Driver
     */
    private void DisplayDriverNames() {
        if (driver.isEmpty()) { // Validate for empty driver's
            System.out.println("No Driver's Found!\n\t¯\\_(ツ)_/¯");
        } else { // Print Driver List
            String tableData = "|  %2d   | %-18s | %-18s|%n";
            System.out.format("+------------------------------------------------+%n")
                    .format("|                List of Driver's                |%n")
                    .format("+------------------------------------------------+%n")
                    .format("| Index |     DRIVER NAME    |       TEAM        |%n")
                    .format("+------------------------------------------------+%n");
            for (Formula1Driver fDriver : driver) {
                System.out.format(tableData, driver.indexOf(fDriver), fDriver.getDriverName(), fDriver.getTeamName());
            }
            System.out.format("+------------------------------------------------+%n");
        }
    }

    /**
     * This Method Display Car Constructor/Team Name according to parsed parameter
     */
    private void DisplayCarConstructorNames() {
        Collections.sort(teamNameList); // Sort Team Name list in alphabetical order
        for (String teamNameElement : teamNameList) {
            System.out.println("[" + teamNameList.indexOf(teamNameElement) + "] : " + teamNameElement);
        }
    }

    /**
     * This Method Prints Back to Main Menu Text
     */
    private void BackToMainMenu() {
        System.out.println("Back to Main Menu....");
    }

    /**
     * This Method takes Car Name user Input and Validate
     *
     * @return - Validated Car Constructor/Team Name
     */
    private String CarConstructorValidator() {
        String teamName = "";
        boolean isValidOption;
        System.out.println("Please Select Car Constructor/Team from the List or Add Custom Team: ");
        DisplayCarConstructorNames();
        System.out.println("[A] : Add a Custom Team");

        String[] nameListIndex = new String[teamNameList.size() + 1];

        for (int i = 0; i <= teamNameList.size(); i++) {
            if (i <= teamNameList.size() - 1) {
                nameListIndex[i] = String.valueOf(i);
            } else {
                nameListIndex[i] = "A";
            }
        }
        List<String> validInputs = Arrays.asList(nameListIndex);
        do {
            System.out.print("Choose Option: ");
            String option = scanner.next().toUpperCase();
            isValidOption = validInputs.contains(option);
            if (isValidOption) {
                if (option.equals("A")) {
                    teamName = AddNewTeam();
                } else {
                    teamName = teamNameList.get(Integer.parseInt(option));
                    teamNameList.remove(teamName);
                }
            } else {
                System.out.println("Invalid Input! Try Again.");
            }
        } while (!isValidOption);
        return teamName;
    }

    /**
     * This Method take user Inputs for custom team name
     *
     * @return - Return Custom Team Name
     */
    private String AddNewTeam() {
        Scanner sScan = new Scanner(System.in).useDelimiter("\n"); // New Scanner object created for take user inputs with spaces
        System.out.print("Enter Custom Team Name: ");
        return sScan.next();
    }

    /**
     * @param position - This says which position need to take user input
     * @param positionPerSeason - This is total number of races per season
     * @return - Position of Races driver previous participated
     */
    private int PositionCountValidator(int position, int positionPerSeason) {
        int positionCount;
        String[] positionArray = {"First", "Second", "Third"};
        String promptMessage = "Enter Driver's no of " + positionArray[position - 1] + " Positions: ";
        String errorMessage = "Invalid Input! Try Again.";
        if (positionPerSeason != 0) {
            do { // validate use inputs
                System.out.print(promptMessage);
                while (!scanner.hasNextInt()) {
                    System.out.print(errorMessage + "\n" + promptMessage);
                    scanner.next();
                }
                positionCount = scanner.nextInt();
                if (positionCount < 0 || positionCount > 30) {
                    System.out.println(errorMessage);
                }
            } while (positionCount < 0 || positionCount > 30);
            return positionCount;
        } else {
            System.out.println("Maximum Races Per Season is 30");
            return 0;
        }
    }

    /**
     * This Method take and validate user input of Driver Name
     *
     * @return - Driver Name as String
     */
    private String DriverNameValidator() {
        String driverName;
        do { // Driver Name Validation
            Scanner sScan = new Scanner(System.in).useDelimiter("\n");
            System.out.print("\nEnter Driver's Name: ");
            driverName = sScan.next();
            if (driverName.length() < 3 || driverName.length() > 26) {
                System.out.println("Invalid Input! Try Again.");
            }
        } while (driverName.length() < 3 || driverName.length() > 26);
        return driverName;
    }

    /**
     * This method Updates each driver's Data
     *
     * @param formula1Driver - Formula1Driver Object
     */
    private void RaceDataUpdater(Formula1Driver formula1Driver) {
        int racePosition;
        formula1Driver.setRaceCount(1);
        do { // User Input Validation
            String message = "Enter Driver " + formula1Driver.getDriverName() + "'s Team " + formula1Driver.getTeamName() + " Race Completed Position: ";
            System.out.print(message);
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid Input! Try Again.\n" + message);
                scanner.next();
            }
            racePosition = scanner.nextInt();
            if (racePosition < 1 || racePosition > 22) {
                System.out.println("Position is Out of Range! Try Again.");
            } else if (!positionList.contains(racePosition)) {
                positionList.add(racePosition);
                break;
            } else {
                System.out.println("Position Already Assigned! Try Again.");
            }
        } while (racePosition < 1 || racePosition > 22 || positionList.contains(racePosition));
        switch (racePosition) { // switch case to add points to each driver according to position
            case 1 -> {
                formula1Driver.setCurrentPoints(25);
                formula1Driver.setFirstPositionCount(1);
            }
            case 2 -> {
                formula1Driver.setCurrentPoints(18);
                formula1Driver.setSecondPositionCount(1);
            }
            case 3 -> {
                formula1Driver.setCurrentPoints(15);
                formula1Driver.setThirdPositionCount(1);
            }
            case 4 -> formula1Driver.setCurrentPoints(12);
            case 5 -> formula1Driver.setCurrentPoints(10);
            case 6 -> formula1Driver.setCurrentPoints(8);
            case 7 -> formula1Driver.setCurrentPoints(6);
            case 8 -> formula1Driver.setCurrentPoints(4);
            case 9 -> formula1Driver.setCurrentPoints(2);
            case 10 -> formula1Driver.setCurrentPoints(1);
        }
    }

    /**
     * This Method Validates the Date
     *
     * @param dateString - User Input Date as String
     * @param season - This Season/Year as Integer
     * @return - inputted date validation
     */
    private boolean DateValidator(String dateString, int season) {
        String validDateFormat = "yyyy/MM/dd"; // Valid date format
        try {
            DateFormat dateFormat = new SimpleDateFormat(validDateFormat);
            Date seasonEnd = dateFormat.parse((season + 1) + "/01/01");
            Date seasonStart = dateFormat.parse((season - 1) + "/12/31");
            dateFormat.setLenient(false);
            Date parsedDate = dateFormat.parse(dateString);
            return parsedDate.after(seasonStart) && parsedDate.before(seasonEnd);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * This method validate Driver Location/Nationality
     *
     * @return - Validated Driver Location in ISO-3166 ALPHA-3 Format
     */
    private String DriverLocationValidator() {
        String driverLocation;
        String countryCodeTable = "\n+------------------------+------------------------+------------------------+------------------------+"
                .concat("\n|     CODE - COUNTRY     |     CODE - COUNTRY     |     CODE - COUNTRY     |     CODE - COUNTRY     |")
                .concat("\n+------------------------+------------------------+------------------------+------------------------+")
                .concat("\n|  ARG  - Argentina      |  FIN  - Finland        |  MYS  - Malaysia       |  SWE  - Sweden         |")
                .concat("\n|  AUS  - Australia      |  FRA  - France         |  MEX  - Mexico         |  CHE  - Switzerland    |")
                .concat("\n|  AUT  - Austria        |  DEU  - Germany        |  MCO  - Monaco         |  THA  - Thailand       |")
                .concat("\n|  BEL  - Belgium        |  HUN  - Hungary        |  MAR  - Morocco        |  GBR  - United Kingdom |")
                .concat("\n|  BRA  - Brazil         |  IND  - India          |  NZL  - New Zealand    |  USA  - United States  |")
                .concat("\n|  CAN  - Canada         |  IDN  - Indonesia      |  NLD  - Netherlands    |  URY  - Uruguay        |")
                .concat("\n|  CHL  - Chile          |  IRL  - Ireland        |  PRT  - Portugal       |  VEN  - Venezuela      |")
                .concat("\n|  COL  - Colombia       |  ITA  - Italy          |  RUS  - Russia         |                        |")
                .concat("\n|  CZE  - Czech Republic |  JPN  - Japan          |  ZAF  - South Africa   |                        |")
                .concat("\n|  DNK  - Denmark        |  LIE  - Liechtenstein  |  ESP  - Spain          |                        |")
                .concat("\n+------------------------+------------------------+------------------------+------------------------+\n");
        System.out.println(countryCodeTable);
        boolean isValidCountryCodeLength;
        boolean isValidCountryCode;
        do { // User input validation
            System.out.print("Enter Driver's Location(Country Code Ex:Japan as JPN): ");
            driverLocation = scanner.next().toUpperCase();
            isValidCountryCode = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3).contains(driverLocation);
            isValidCountryCodeLength = driverLocation.length() != 3;
        } while ((!isValidCountryCode) || isValidCountryCodeLength);
        return driverLocation;
    }

    /**
     * This Method take and validate Race Count
     *
     * @return - Return Race Count as Integer
     */
    private int DriverRaceCountValidator(int oldRaceCount) {
        int raceCount;
        do { // validates Driver Race Count is valid
            System.out.print("Enter Driver's Race Count: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid Input! Try Again.\nEnter Driver's Race Count: ");
                scanner.next();
            }
            raceCount = scanner.nextInt();
            if (raceCount < 0) {
                System.out.println("Invalid Input! Try Again.");
            }
            if (raceCount < oldRaceCount) {
                System.out.println("Impossible Race Count (Min:" + oldRaceCount + ") Try Again.");
            }
        } while (raceCount < 0 || raceCount < oldRaceCount);
        return raceCount;
    }

    /**
     * This Method take and Validate Driver Points
     *
     * @return - Return Point driver got as float
     */
    private float DriverPointsValidator(int oldRacePoint) {
        float currentPoints;
        do { // validates Driver current Points is valid
            System.out.print("Enter Driver's current Points: ");
            while (!scanner.hasNextFloat()) {
                System.out.print("Invalid Input! Try Again.\nEnter Driver's current Points: ");
                scanner.next();
            }
            currentPoints = scanner.nextFloat();
            if (currentPoints < 0.0) {
                System.out.println("Invalid Input! Try Again.");
            }
            if (currentPoints < oldRacePoint) {
                System.out.println("Impossible Race Points (Min:" + oldRacePoint + ") Try Again.");
            }
        } while (currentPoints < 0.0 || currentPoints < oldRacePoint);
        return currentPoints;
    }

    /**
     * This Method Prints Previous Added Race Dates
     */
    private void DisplayOldRaceDates() {
        if (!raceDates.isEmpty()) { // Print Previous Race Dates in table
            String tableData = "|     %-10s      |%n";
            System.out.format("+---------------------+%n")
                    .format("| Previous Race Dates |%n")
                    .format("+---------------------+%n");
            for (String raceDate : raceDates) {
                System.out.format(tableData, raceDate);
            }
            System.out.format("-----------------------%n");
        }
    }

    /**
     * This Method Prints Cool Ascii Arts
     *
     * @param val - This Value set which ascii art to print
     */
    private static void PrintAsciiArt(boolean val) {
        String art1 = "\t\t\t█───█─▄▀▀─█───▄▀▀─▄▀▀▄─█▄─▄█─▄▀▀"
                .concat("\n\t\t\t█───█─█───█───█───█──█─█▀▄▀█─█──")
                .concat("\n\t\t\t█───█─█▀▀─█───█───█──█─█─▀─█─█▀▀")
                .concat("\n\t\t\t█▄█▄█─█───█───█───█──█─█───█─█──")
                .concat("\n\t\t\t─▀─▀───▀▀──▀▀──▀▀──▀▀──▀───▀──▀▀");

        String art2 = "\t\t█▀██▀█─▀██▀─▀█▀────█─────▀██▄─▀█▀─▀██▀▄█▀"
                .concat("\n\t\t──██────██▄▄▄█────▄██─────██▀▄─█───███▀")
                .concat("\n\t\t──██────██───█───▄█▄██────██─▀▄█───██▀█")
                .concat("\n\t\t─▄██▄──▄██▄─▄█▄─▄█▄─▄██▄─▄██▄─▀█──▄██▄▀█▄ YOU!!! ");

        String printArt = val ? art1 : art2; // ternary condition for choose which art to print
        System.out.println(printArt);
    }
}
