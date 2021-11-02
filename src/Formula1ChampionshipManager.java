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

    public static void main(String[] args) {
        Formula1ChampionshipManager F1CM = new Formula1ChampionshipManager();
        Collections.addAll(teamNameList, teamNameStringArray);
        printAsciiArt(true);
        F1CM.LoadFileOnStart();
        F1CM.mainMenu();
        F1CM.SaveFileOnExit();
        printAsciiArt(false);
    }

    @Override
    public void mainMenu() {
        while (isValid) {
            String menuItems = "\n------------------------------------------------------------"
                    .concat("\n|          Formula1 Championship Manager Program           |")
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

    private void mainMenuInputValidation(String code) {
        String[] validInputArray = {"100", "CND", "101", "DAD", "102", "CTT", "103", "DAS", "104", "DDT", "105", "ANR", "999", "EXT"};
        int index;
        List<String> validInputList = Arrays.asList(validInputArray);

        if (validInputList.contains(code)) {
            index = validInputList.indexOf(code);
            switch (index) { // switch case of valid input index
                // 100 or VVB
                case 0, 1 -> CreateNewDriver();
                //101 or VEB
                case 2, 3 -> DisplayDriverNames();
                //102 or APB
//                case 4, 5 -> addPatient();
                //103 or RPB
                case 6, 7 -> DisplayStatistics();
                //104 or VPS
//                case 8, 9 -> DisplayStatistics();
                //105 or SPD
//                case 10, 11 -> saveProgramData();
                //106 or LPD
                case 12, 13 -> isValid = false;
            }
        } else {
            isValid = true;
            System.out.println("Invalid Input! Try Again.");
        }
    }

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
                System.out.print("Do You Want to create Another Driver?(Y/n) ");
                option = scanner.next().toUpperCase();
                flag = option.matches(inputRegexPattern);
                if (!flag) System.out.println("Invalid Input! Try Again.");
            } while (!flag);
        } while (!option.equals("N"));

        BackToMainMenu();
    }

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

//            System.out.println("File Saved Successfully!");

        } catch (IOException e) {
            System.out.println("Oops! Something went Wrong.");
        }
    }

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

                Object list1 = savedFile.readObject();
                teamNameList = cast1(list1);
                Collections.sort(teamNameList);
                Object list2 = savedFile.readObject();
                driver = cast2(list2);
            }
        } catch (Exception e) {
            System.out.println("Oops! Something went Wrong.");
        }
    }

    @SuppressWarnings("unchecked")
    private List<String> cast1(Object obj) {
        return (List<String>) obj;
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Formula1Driver> cast2(Object obj) {
        return (ArrayList<Formula1Driver>) obj;
    }

    @Override
    public void DisplayDriverTable() {

    }

    @Override
    public void AddRace() {

    }

    @Override
    public void DisplayDriverNames() {
        System.out.println("List of Driver's\nIndex | Driver Name | Team/Car Constructor");
        if (driver.size() == 0) {
            System.out.println("No Driver's Found!\n\t¯\\_(ツ)_/¯");
        } else {
            for (Formula1Driver formula1Driver : driver) {
                System.out.println("[" + driver.indexOf(formula1Driver) + "] : " + formula1Driver.getDriverName() + "\t" + formula1Driver.getTeamName());
            }
        }
    }

    @Override
    public void DeleteDriver() {
        DisplayDriverNames();
        if (driver.size() != 0) {

        } else {
            System.out.println("Try Adding Driver using Option in Main Menu -> Create A New Driver");
        }
        BackToMainMenu();
    }

    @Override
    public void DisplayCarConstructorNames() {
        Collections.sort(teamNameList);
        for (String teamNameElement : teamNameList) {
            System.out.println("[" + teamNameList.indexOf(teamNameElement) + "] : " + teamNameElement);
        }
    }

    private void BackToMainMenu() {
        System.out.println("Back to Main Menu....");
    }

    private void DisplayStatistics() {
        System.out.println("Driver Statistics");
        if (driver.size() == 0) {
            System.out.println("No Driver Details Found!. Enter Driver Details from Menu \n\t\t\t¯\\_(ツ)_/¯");
        } else {
            for (Formula1Driver formula1Driver : driver) {
                String statistics = "________________________________________________________"
                        .concat("\nDriver's Name: " + formula1Driver.getDriverName())
                        .concat("\nDriver's Nationality: " + formula1Driver.getDriverLocation())
                        .concat("\nDriver's Team Name: " + formula1Driver.getTeamName())
                        .concat("\nDriver's First Position Count: " + formula1Driver.getFirstPositionCount())
                        .concat("\nDriver's Second Position Count: " + formula1Driver.getSecondPositionCount())
                        .concat("\nDriver's Third Position Count: " + formula1Driver.getThirdPositionCount())
                        .concat("\nDriver Participated Race Count: " + formula1Driver.getRaceCount())
                        .concat("\nDriver's Current Points: " + formula1Driver.getCurrentPoints())
                        .concat("\n--------------------------------------------------------");
                System.out.println(statistics);
            }
        }
        BackToMainMenu();
//        ArrayList<Formula1Driver> f1d = driver.sort(Comparator.comparing(Driver::getPodiumCount));
    }


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
                    teamName = addNewTeam();
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

    private String addNewTeam() {
        Scanner sScan = new Scanner(System.in).useDelimiter("\n");
        System.out.print("Enter Custom Team Name: ");
        return sScan.next();
    }

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

    private String DriverNameValidator() {
        String driverName;
        do {
            Scanner sScan = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Enter Driver's Name: ");
            driverName = sScan.next();
            if (driverName.length() < 3) {
                System.out.println("Invalid Input! Try Again.");
            }
        } while (driverName.length() < 3);
        return driverName;
    }

    private String DriverLocationValidator() {
        String driverLocation;
        boolean isValidCountryCodeLength;
        boolean isValidCountryCode;
        do {
            System.out.print("\nEnter Driver's Location(Country Code Ex:Japan as JPN): ");
            driverLocation = scanner.next().toUpperCase();
            isValidCountryCode = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3).contains(driverLocation);
            isValidCountryCodeLength = driverLocation.length() != 3;
        } while ((!isValidCountryCode) || isValidCountryCodeLength);
        return driverLocation;
    }

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
