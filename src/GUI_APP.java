import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GUI_APP extends JFrame implements ActionListener {
    private ArrayList<Formula1Driver> driver;
    private ArrayList<RaceData> races;
    private List<String> teamNameList;
    private List<String> raceDates;
    private static List<String> foundDate = new ArrayList<>();
    private static List<Integer> foundFinishedPositions = new ArrayList<>();
    private static List<Integer> foundStartPositions = new ArrayList<>();

    /*--------------------- UI Components ---------------------*/
    private JToolBar toolBar = new JToolBar();
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private JButton sortPointDes = new JButton("Sort by Point (Descending)");
    private JButton sortPointAsc = new JButton("Sort by Point (Ascending)");
    private JButton sortFirstDes = new JButton("Sort by First Positions (Descending)");
    private JButton generateRace = new JButton("Generate Race Randomly");
    private JButton generateRaceWithPos = new JButton("Generate Race Randomly(With Positions)");
    private JButton searchButton = new JButton("Search");
    private JButton sortRaceButton = new JButton("Sort Race Date(Ascending)");
    private JTextField searchText = new JTextField(20);
    private JTable driverTableTop;
    private JTable driverTableBottom;
    private JTable sortTable;
    private JTable searchTable;
    private DefaultTableModel tableModel;
    private DefaultTableModel sortTableModel;
    private DefaultTableModel searchTableModel;


    /**
     * Main Constructor Of the GUI
     *
     * @param driver       - driver ArrayList
     * @param teamNameList - teamNameList String List
     * @param races        - races ArrayList
     */
    public GUI_APP(ArrayList<Formula1Driver> driver, ArrayList<RaceData> races, List<String> teamNameList, List<String> raceDates) {
        this.driver = driver;
        this.races = races;
        this.teamNameList = teamNameList;
        this.raceDates = raceDates;

        tabbedPane.addTab("Driver Table", driverTablePane());
        tabbedPane.addTab("Race Table", raceTablePane());
        getContentPane().add(tabbedPane);

        sortPointDes.addActionListener(this);
        sortPointAsc.addActionListener(this);
        sortFirstDes.addActionListener(this);
        generateRace.addActionListener(this);
        generateRaceWithPos.addActionListener(this);
        searchButton.addActionListener(this);
        sortRaceButton.addActionListener(this);

        setTitle("F1 Championship Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private JPanel driverTablePane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        toolBar.addSeparator();
        toolBar.add(sortPointDes);
        toolBar.addSeparator();
        toolBar.add(sortPointAsc);
        toolBar.addSeparator();
        toolBar.add(sortFirstDes);
        toolBar.addSeparator();
        toolBar.add(generateRace);
        toolBar.addSeparator();
        toolBar.setFloatable(false);

        JToolBar midBar = new JToolBar();
        midBar.setFloatable(false);
        midBar.add(generateRaceWithPos);

        panel.add(toolBar);
        panel.add(topDriverTableRenderer());
        panel.add(midBar);
//        panel.add(bottomDriverTableRenderer());

        return panel;
    }

    private JPanel raceTablePane() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2,0,5));

        JToolBar searchToolBar = new JToolBar();
        JToolBar sortToolBar = new JToolBar();
        searchToolBar.setFloatable(false);
        sortToolBar.setFloatable(false);

        searchToolBar.add(searchText);
        searchToolBar.add(searchButton);

        sortToolBar.add(sortRaceButton);

        panel.add(searchToolBar);
        panel.add(sortToolBar);
        panel.add(searchTableRenderer());
        panel.add(sortTableRenderer());

        return panel;
    }

    private JScrollPane sortTableRenderer() {
        String[] header = {"Race Date", "No of Participants"};
        sortTableModel = new DefaultTableModel(header, 0);
        sortTable = new JTable(sortTableModel);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);

        sortTableBody();

//        sortTable.setPreferredScrollableViewportSize(new Dimension(950, 100));
//        sortTable.setFillsViewportHeight(true);
        sortTable.setEnabled(false);
        sortTable.setDefaultRenderer(Object.class, dtcr);
        sortTable.getTableHeader().setReorderingAllowed(false);
        sortTable.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(sortTable);
        return scrollPane;
    }

    private JScrollPane searchTableRenderer() {
        String[] header = { "Race Date", "Finish Position" };
        searchTableModel = new DefaultTableModel(header, 0);
        searchTable = new JTable(searchTableModel);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);


        searchTableBody();
//        searchTable.setPreferredScrollableViewportSize(new Dimension(950, 100));
//        searchTable.setFillsViewportHeight(true);
        searchTable.setEnabled(false);
        searchTable.setDefaultRenderer(Object.class, dtcr);
        searchTable.getTableHeader().setReorderingAllowed(false);
        searchTable.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(searchTable);
        return  scrollPane;
    }

    private JScrollPane topDriverTableRenderer() {
        String[] header = {"Pos", "Driver Name", "Nationality", "Team/Constructor", "First Pos Count", "Second Pos Count", "Third Pos Count", "Pts"};

        tableModel = new DefaultTableModel(header, 0);
        driverTableTop = new JTable(tableModel);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);

        tableBody();

        driverTableTop.setPreferredScrollableViewportSize(new Dimension(950, 100));
//        driverTableTop.setFillsViewportHeight(true);
        driverTableTop.setEnabled(false);
        driverTableTop.setDefaultRenderer(Object.class, dtcr);
        driverTableTop.getTableHeader().setReorderingAllowed(false);
        driverTableTop.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(driverTableTop);
        return scrollPane;
    }

    private JScrollPane bottomDriverTableRenderer() {
        String[] header = {"Pos", "Driver Name", "Nationality", "Team/Constructor", "First Pos Count", "Second Pos Count", "Third Pos Count", "Pts"};

        tableModel = new DefaultTableModel(header, 0);
        driverTableBottom = new JTable(tableModel);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);

        driverTableBottom.setPreferredScrollableViewportSize(new Dimension(950, 100));
        driverTableBottom.setFillsViewportHeight(true);
        driverTableBottom.setEnabled(false);
        driverTableBottom.setDefaultRenderer(Object.class, dtcr);
        driverTableBottom.getTableHeader().setReorderingAllowed(false);
        driverTableBottom.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(driverTableBottom);
        return scrollPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sortPointDes) {
            tableModel.setRowCount(0);
            driver.sort(new SortByPoints());
            tableBody();
        } else if (e.getSource() == sortPointAsc) {
            tableModel.setRowCount(0);
            driver.sort(new SortByPoints().reversed());
            tableBody();
        } else if (e.getSource() == sortFirstDes) {
            tableModel.setRowCount(0);
            driver.sort(new SortByFPS());
            tableBody();
        } else if (e.getSource() == generateRace) {
            tableModel.setRowCount(0);
            generateRandomRace();
            tableBody();
        } else if (e.getSource() == generateRaceWithPos) {

        } else if (e.getSource() == searchButton) {
            String keyword = searchText.getText();
            searchArrayLists(keyword);
            searchTableModel.setRowCount(0);
            searchTableBody();
            foundDate.clear();
            foundFinishedPositions.clear();
            foundStartPositions.clear();
        } else if (e.getSource() == sortRaceButton) {
            sortTableModel.setRowCount(0);
            races.sort(new SortByDate());
            sortTableBody();
        }
    }

    private void generateRandomRace() {
        if (driver.size() != 0) {
            Formula1ChampionshipManager f1cm = new Formula1ChampionshipManager();
            ArrayList<Integer> positionList = new ArrayList<>();
            boolean validDate;
            boolean containDate;
            String dateString;
            int randomNumber;
            do {
                String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                String month = randomNumberGenerator(1,12);
                String day = randomNumberGenerator(1,31);
                dateString = year + "/" + month + "/" + day;
                validDate = f1cm.DateValidator(dateString, Integer.parseInt(year));
                containDate = raceDates.contains(dateString);
            } while (containDate&&(!validDate));
            raceDates.add(dateString);
            races.add(new RaceData(dateString));
            for (Formula1Driver formula1Driver : driver) {
                do {
                    randomNumber = Integer.parseInt(randomNumberGenerator(1,25));
                } while (positionList.contains(randomNumber));
                positionList.add(randomNumber);
                addPoints(randomNumber,formula1Driver,dateString);
                formula1Driver.setRaceCount(1);
            }
            positionList.clear();
        }
    }

    private String randomNumberGenerator(int min, int max) {
        String randomNumber = String.valueOf((int) (Math.random() * (max - min + 1) + min));
        if (randomNumber.length() == 1) {
            randomNumber = "0" + randomNumber;
        }
        return randomNumber;
    }

    private void addPoints(int position, Formula1Driver formula1Driver, String dateString) {
        Formula1ChampionshipManager.AddDriverPoints(formula1Driver, dateString, position, races);
    }

    private void tableBody() {
        for (Formula1Driver formula1Driver : driver) {
            int position = driver.indexOf(formula1Driver) + 1;
            String driverName = formula1Driver.getDriverName();
            String driverLocation = formula1Driver.getDriverLocation();
            String teamName = formula1Driver.getTeamName();
            int fps = formula1Driver.getFirstPositionCount();
            int spc = formula1Driver.getSecondPositionCount();
            int tpc = formula1Driver.getThirdPositionCount();
            float pts = formula1Driver.getCurrentPoints();

            Object[] tableData = {position, driverName, driverLocation, teamName, fps, spc, tpc, pts};
            tableModel.addRow(tableData);
        }
    }

    private void sortTableBody() {
        for (RaceData race : races) {
            String date = race.getRaceDate();
            int participated = race.getDriverNames().size();
            Object[] tableData = {date, participated};
            sortTableModel.addRow(tableData);
        }
    }

    private void searchTableBody() {
        for (int i = 0; i < foundDate.size(); i++) {
            String date = foundDate.get(i);
            int fPos = foundFinishedPositions.get(i);
//            int sPos = foundStartPositions.get(i);

            Object[] tableData = {date, fPos};
            searchTableModel.addRow(tableData);
        }
    }

    private void searchArrayLists(String keyword) {
        for (RaceData race : races) {
            ArrayList<String> names = race.getDriverNames();
            for (String name : names) {
                if (keyword.equalsIgnoreCase(name)) {
                    int index = names.indexOf(name);
                    foundDate.add(race.getRaceDate());
                    foundFinishedPositions.add(race.getDriverPositions().get(index));
//                    foundFinishedPositions.add(race.getDriverStartPositions().get(index));
                }
            }
        }
    }
}
