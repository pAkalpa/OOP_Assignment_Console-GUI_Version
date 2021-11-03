import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager {
    private static boolean isValid = true;
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Formula1Driver> driver = new ArrayList<>();
    private static final String[] teamNameStringArray = {"Mercedes", "Red Bull Racing", "McLaren", "Ferrari", "AlphaTauri", "Aston Martin", "Williams", "Alfa Romeo Racing", "Haas F1 Team"};
    private static List<String> teamNameList = new ArrayList<>();
    private static List<String> occupiedTeamNameList = new ArrayList<>();
    private static final List<String> fullTeamList = new ArrayList<>();

    /**
     * Main Method Invoke four methods and Create Object
     * @param args - None
     */
    public static void main(String[] args) {
        Formula1ChampionshipManager F1CM = new Formula1ChampionshipManager();
        Collections.addAll(teamNameList, teamNameStringArray);
        fullTeamList.addAll(teamNameList);
        fullTeamList.addAll(occupiedTeamNameList);
        printAsciiArt(true);
        F1CM.LoadFileOnStart();
        F1CM.mainMenu();
        F1CM.SaveFileOnExit();
        printAsciiArt(false);
    }

    /**
     * This Method Display Formula 1 Championship Manager Program Console Main Menu
     */
    @Override
    public void mainMenu() {
        while (isValid) {
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
        String driverName, teamName, driverLocation, option;
        int firstPositionCount, secondPositionCount, thirdPositionCount, raceCount;
        float currentPoints;
        boolean flag;

        System.out.println("\nCreate New Driver");

        if (driver.size() != 0) {
            DisplayDriverNames();
        }

        do {
            driverName = DriverNameValidator();
            driverLocation = DriverLocationValidator();
            teamName = CarConstructorValidator();
            firstPositionCount = PositionCountValidator(1);
            secondPositionCount = PositionCountValidator(2);
            thirdPositionCount = PositionCountValidator(3);
            raceCount = DriverRaceCountValidator();
            currentPoints = DriverPointsValidator();

            driver.add(new Formula1Driver(driverName, driverLocation, teamName, firstPositionCount, secondPositionCount, thirdPositionCount, raceCount, currentPoints));

            do {
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
        int index;
        String option;
        boolean flag;
        DisplayDriverNames();
        if (driver.size() != 0) {
            do {
                do {
                    System.out.print("\nEnter Driver Index to Delete Driver(Also Deletes Driver Team) or " + driver.size() + " to Go Back to Menu : ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid Input! Try Again.\nEnter Driver Index to Delete Driver(Also Deletes Driver Team) or " + driver.size() + " to Go Back to Menu : ");
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
                teamNameList.add(driver.get(index).getTeamName());
                driver.remove(index);
                System.out.println("Driver and Team Successfully Deleted.");
                do {
                    String inputRegexPattern = "[YN]+";
                    System.out.print("Do You Want to Delete Another Driver?(Y/n) ");
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
     * This Method Changes Team
     */
    @Override
    public void ChangeTeam() {
        int index;
        int teamIndex;
        String option;
        boolean flag;
        DisplayDriverNames();
        if (driver.size() != 0) {
            do {
                do {
                    System.out.print("\nEnter Driver Index to Change Team or " + driver.size() + " to Go Back to Menu : ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid Input! Try Again.\nEnter Driver Index to Change Team or " + driver.size() + " to Go Back to Menu : ");
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

                System.out.println("Please Select Car Constructor/Team from the List");
                DisplayCarConstructorNames(2);
                do {
                    System.out.print("Select Preferred Team(index): ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid Input! Try Again.\nSelect Preferred Team(index): ");
                        scanner.next();
                    }
                    teamIndex = scanner.nextInt();
                    if ((teamIndex < 0) || (teamIndex > fullTeamList.size() - 1)) {
                        System.out.println("Invalid Input! Try Again.");
                    }
                } while ((teamIndex < 0) || (teamIndex > fullTeamList.size() - 1));

                String selectedTeamName = fullTeamList.get(teamIndex);
                boolean teamFound = false;
                for (Formula1Driver formula1Driver : driver) {
                    if (formula1Driver.getTeamName().equals(selectedTeamName)) {
                        formula1Driver.setTeamName(driver.get(index).getTeamName());
                        driver.get(index).setTeamName(selectedTeamName);
                        System.out.println(driver.get(index).getDriverName() + " Set to Team -> " + selectedTeamName + "\n(" + formula1Driver.getDriverName() + " automatically set to Team -> " + formula1Driver.getTeamName() + ")");
                        teamFound = true;
                    }
                }
                if (!teamFound) {
                    teamNameList.add(driver.get(index).getTeamName());
                    driver.get(index).setTeamName(selectedTeamName);
                    teamNameList.remove(selectedTeamName);
                    occupiedTeamNameList.add(selectedTeamName);
                    System.out.println(driver.get(index).getDriverName() + " Set to Team -> " + selectedTeamName);
                }

                do {
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
        System.out.println("Driver Statistics");
        if (driver.size() == 0) {
            System.out.println("No Driver Details Found!. Enter Driver Details from Menu \n\t\t\t¯\\_(ツ)_/¯");
        } else {
            for (Formula1Driver formula1Driver : driver) {
                formula1Driver.PrintDriverTable();
            }
        }
        BackToMainMenu();
    }

    /**
     * This Method Display Driver Table
     */
    @Override
    public void DisplayDriverTable() {
        Collections.sort(driver);
        String tableData = "| %3d | %-18s |     %-3s     | %-17s | %-4.2f |%n";
        System.out.format("+---------------------------------------------------------------------+%n")
                        .format("|                        Formula 1 Driver Table                       |%n")
                        .format("+-----+--------------------+-------------+-------------------+--------+%n")
                        .format("| POS |       DRIVER       | NATIONALITY |        CAR        |   PTS  |%n")
                        .format("+-----+--------------------+-------------+-------------------+--------|%n");
        for (Formula1Driver fDriver : driver) {
            System.out.format(tableData, (driver.indexOf(fDriver)+1), fDriver.getDriverName(), fDriver.getDriverLocation(), fDriver.getTeamName(), fDriver.getCurrentPoints());
        }
        System.out.format("+---------------------------------------------------------------------+%n");
        BackToMainMenu();
    }

    /**
     * This Method Adds Race to Program
     */
    @Override
    public void AddRace() {

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

            saveFile.writeObject(teamNameList);
            saveFile.writeObject(driver);
            saveFile.writeObject(occupiedTeamNameList);

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

                driver.clear();
                teamNameList.clear();
                occupiedTeamNameList.clear();

                Object list1 = savedFile.readObject();
                teamNameList = CastList(list1);
                Collections.sort(teamNameList);
                Object list2 = savedFile.readObject();
                driver = CastArrayList(list2);
                Object list3 = savedFile.readObject();
                occupiedTeamNameList = CastList(list3);
            }
        } catch (Exception e) {
            System.out.println("Oops! Something went Wrong.");
        }
    }

    /**
     * This Method Cast Lists
     * @param obj - List
     * @return - Object cast into List
     */
    @SuppressWarnings("unchecked")
    private List<String> CastList(Object obj) {
        return (List<String>) obj;
    }

    /**
     * This Method Cast ArrayLists
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
        if (driver.size() == 0) {
            System.out.println("No Driver's Found!\n\t¯\\_(ツ)_/¯");
        } else {
            System.out.println("List of Driver's\nIndex | Driver Name | Team/Car Constructor");
            for (Formula1Driver formula1Driver : driver) {
                System.out.println("[" + driver.indexOf(formula1Driver) + "] : " + formula1Driver.getDriverName() + "\t" + formula1Driver.getTeamName());
            }
        }
    }

    /**
     * This Method Display Car Constructor/Team Name according to parsed parameter
     * @param i - 1 : Team Deleted List ; 2 : All Team List
     */
    private void DisplayCarConstructorNames(int i) {
        if (i == 1) {
            Collections.sort(teamNameList);
            for (String teamNameElement : teamNameList) {
                System.out.println("[" + teamNameList.indexOf(teamNameElement) + "] : " + teamNameElement);
            }
        } else if (i == 2){
            Collections.sort(fullTeamList);
            for (String element : fullTeamList) {
                System.out.println("[" + fullTeamList.indexOf(element) + "] : " + element);
            }
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
     * @return - Validated Car Constructor/Team Name
     */
    private String CarConstructorValidator() {
        String teamName = "";
        boolean isValidOption;
        System.out.println("Please Select Car Constructor/Team from the List or Add Custom Team: ");
        DisplayCarConstructorNames(1);
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
                occupiedTeamNameList.add(teamName);
            } else {
                System.out.println("Invalid Input! Try Again.");
            }
        } while (!isValidOption);
        return teamName;
    }

    /**
     * @return
     */
    private String AddNewTeam() {
        Scanner sScan = new Scanner(System.in).useDelimiter("\n");
        System.out.print("Enter Custom Team Name: ");
        return sScan.next();
    }

    /**
     * @param position
     * @return
     */
    private int PositionCountValidator(int position) {
        int positionCount;
        String[] positionArray = {"First", "Second", "Third"};
        String promptMessage = "Enter Driver's no of " + positionArray[position - 1] + " Positions: ";
        String errorMessage = "Invalid Input! Try Again.";
        do {
            System.out.print(promptMessage);
            while (!scanner.hasNextInt()) {
                System.out.print(errorMessage + "\n" + promptMessage);
                scanner.next();
            }
            positionCount = scanner.nextInt();
            if (positionCount < 0) {
                System.out.println(errorMessage);
            }
        } while (positionCount < 0);
        return positionCount;
    }

    /**
     * @return
     */
    private String DriverNameValidator() {
        String driverName;
        do {
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
     * @return
     */
    private String DriverLocationValidator() {
        String driverLocation;
        boolean isValidCountryCodeLength;
        boolean isValidCountryCode;
        do {
            System.out.print("Enter Driver's Location(Country Code Ex:Japan as JPN): ");
            driverLocation = scanner.next().toUpperCase();
            isValidCountryCode = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3).contains(driverLocation);
            isValidCountryCodeLength = driverLocation.length() != 3;
        } while ((!isValidCountryCode) || isValidCountryCodeLength);
        return driverLocation;
    }

    /**
     * @return
     */
    private int DriverRaceCountValidator() {
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
        } while (raceCount < 0);
        return raceCount;
    }

    /**
     * @return
     */
    private float DriverPointsValidator() {
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
        } while (currentPoints < 0.0);
        return currentPoints;
    }

    /**
     * @param val
     */
    private static void printAsciiArt(boolean val) {
        String art1 = "\t\t\t█───█─▄▀▀─█───▄▀▀─▄▀▀▄─█▄─▄█─▄▀▀"
                .concat("\n\t\t\t█───█─█───█───█───█──█─█▀▄▀█─█──")
                .concat("\n\t\t\t█───█─█▀▀─█───█───█──█─█─▀─█─█▀▀")
                .concat("\n\t\t\t█▄█▄█─█───█───█───█──█─█───█─█──")
                .concat("\n\t\t\t─▀─▀───▀▀──▀▀──▀▀──▀▀──▀───▀──▀▀");

        String art2 = "\t\t█▀██▀█─▀██▀─▀█▀────█─────▀██▄─▀█▀─▀██▀▄█▀"
                .concat("\n\t\t──██────██▄▄▄█────▄██─────██▀▄─█───███▀")
                .concat("\n\t\t──██────██───█───▄█▄██────██─▀▄█───██▀█")
                .concat("\n\t\t─▄██▄──▄██▄─▄█▄─▄█▄─▄██▄─▄██▄─▀█──▄██▄▀█▄ YOU!!! ");

        String printArt = val ? art1 : art2;
        System.out.println(printArt);
    }
}
