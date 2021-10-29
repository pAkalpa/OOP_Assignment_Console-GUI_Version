import java.util.*;

public class Formula1ChampionshipManager implements ChampionshipManager {
    private static boolean isValid = true;
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Formula1Driver> driver = new ArrayList<>();
    private static final String[] teamNameStringArray = {"Mercedes", "Red Bull Racing", "McLaren", "Ferrari", "AlphaTauri", "Aston Martin", "Williams", "Alfa Romeo Racing", "Haas F1 Team"};
    private static final List<String> teamNameList = Arrays.asList(teamNameStringArray);

    public static void main(String[] args) {
        Formula1ChampionshipManager F1CM = new Formula1ChampionshipManager();
        printAsciiArt(true);
        F1CM.mainMenu();
        printAsciiArt(false);
    }

    public void mainMenu() {
        while (isValid) {
            String menuItems = "\n------------------------------------------------------------"
                    .concat("\n|          Formula1 Championship Manager Program           |")
                    .concat("\n|----------------------------------------------------------|")
                    .concat("\n| 100 or CND |\tCreate a New Driver                        |")
                    .concat("\n| 101 or DAD |\tDelete a Driver                            |")
                    .concat("\n| 102 or DAT |\tDelete a Team(Manufacturer)                |")
                    .concat("\n| 103 or CTT |\tChange the Team(Manufacturer)              |")
                    .concat("\n| 104 or DAS |\tDisplay Statistics                         |")
                    .concat("\n| 105 or DDT |\tDisplay F1 Driver Table                    |")
                    .concat("\n| 106 or ANR |\tAdd Race                                   |")
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
        String[] validInputArray = {"100", "CND", "101", "DAD", "102", "DAT", "103", "CTT", "104", "DAS", "105", "DDT", "106", "ANR", "999", "EXT"};
        int index;
        List<String> validInputList = Arrays.asList(validInputArray);

        if (validInputList.contains(code)) {
            index = validInputList.indexOf(code);
            switch (index) { // switch case of valid input index
                // 100 or VVB
                case 0, 1 -> createNewDriver();
                //101 or VEB
//                case 2, 3 -> viewAllEmptyBooths();
                //102 or APB
//                case 4, 5 -> addPatient();
                //103 or RPB
//                case 6, 7 -> removePatient();
                //104 or VPS
//                case 8, 9 -> sortPatientNames();
                //105 or SPD
//                case 10, 11 -> saveProgramData();
                //106 or LPD
//                case 12, 13 -> loadProgramData();
                //107 or VRV
                case 14, 15 -> isValid = false;
            }
        } else {
            isValid = true;
            System.out.println("Invalid Input! Try Again.");
        }
    }

    private void createNewDriver() {
        String driverName;
        String teamName;
        String driverLocation;
        int firstPositionCount;
        int secondPositionCount;
        int thirdPositionCount;
        int raceCount;
        int currentPoints;

        System.out.println("\nCreate New Driver");

        do {
            Scanner sScan = new Scanner(System.in).useDelimiter("\n");
            System.out.print("Enter Driver's Name: ");
            driverName = sScan.next();
            if (driverName.length() < 3) {
                System.out.println("Invalid Input! Try Again.");
            }
        } while (driverName.length() < 3);

        carConstructor();

        boolean isValidCountryCodeLength = true;
        boolean isValidCountryCode = true;
        do {
            System.out.print("Enter Driver's Location(Country Code Ex:Japan as JPN): ");
            driverLocation = scanner.next().toUpperCase();
            isValidCountryCode = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3).contains(driverLocation);
            isValidCountryCodeLength = driverLocation.length() != 3;
        } while ((!isValidCountryCode) || isValidCountryCodeLength);
        System.out.println();
        System.out.println("Back to Main Menu....");
    }

    private void carConstructor() {
        System.out.println("Please Select Car Constructor/Team from the List or Add Custom Team: ");
        for (String teamNameElement : teamNameList) {
            System.out.println("[" + teamNameList.indexOf(teamNameElement) + "] : " + teamNameElement);
        }
        System.out.println("[A] : Add a Custom Team");

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
