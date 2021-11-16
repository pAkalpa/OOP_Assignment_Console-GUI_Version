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
    private static final List<String> foundDate = new ArrayList<>();
    private static final List<Integer> foundFinishedPositions = new ArrayList<>();
//    private static final List<Integer> foundStartPositions = new ArrayList<>();

    /*--------------------- UI Components ---------------------*/
    private final JToolBar toolBar = new JToolBar();
    private final JButton sortPointDes = new JButton("Sort by Point (Descending)");
    private final JButton sortPointAsc = new JButton("Sort by Point (Ascending)");
    private final JButton sortFirstDes = new JButton("Sort by First Positions (Descending)");
    private final JButton generateRace = new JButton("Generate Race Randomly");
    private final JButton generateRaceWithPos = new JButton("Generate Race Randomly(With Positions)");
    private final JButton searchButton = new JButton("Search");
    private final JButton sortRaceButton = new JButton("Sort Race Date(Ascending)");
    private final JTextField searchText = new JTextField(20);
    private DefaultTableModel topTableModel;
    private DefaultTableModel bottomTableModel;
    private DefaultTableModel sortTableModel;
    private DefaultTableModel searchTableModel;

    /*-------------------- Assets ---------------------------*/
    ImageIcon frameIcon = new ImageIcon("src/assets/icon.png");
    ImageIcon driverIcon = new ImageIcon("src/assets/driver.png");
    ImageIcon raceDateIcon = new ImageIcon("src/assets/race-track.png");
    ImageIcon descendingImg = new ImageIcon("src/assets/sort-descending-xs.png");
    ImageIcon ascendingImg = new ImageIcon("src/assets/sort-ascending-xs.png");
    ImageIcon randomIcon = new ImageIcon("src/assets/random-xs.png");
    ImageIcon searchIcon = new ImageIcon("src/assets/search-xs.png");
    ImageIcon notFoundIcon = new ImageIcon("src/assets/not-found-sm.png");
    ImageIcon binIcon = new ImageIcon("src/assets/bin-sm.png");
    ImageIcon emptyIcon = new ImageIcon("src/assets/error-sm.png");
    ImageIcon infoIcon = new ImageIcon("src/assets/info-sm.png");


    /**
     * Main Constructor Of the GUI
     *
     * @param driver - driver ArrayList
     * @param races  - races ArrayList
     */
    public GUI_APP(ArrayList<Formula1Driver> driver, ArrayList<RaceData> races) {
        this.driver = driver;
        this.races = races;

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Driver Table", driverIcon, driverTablePane());
        tabbedPane.addTab("Race Table", raceDateIcon, raceTablePane());
        getContentPane().add(tabbedPane);

        sortPointDes.addActionListener(this);
        sortPointDes.setIcon(descendingImg);
        sortPointAsc.addActionListener(this);
        sortPointAsc.setIcon(ascendingImg);
        sortFirstDes.addActionListener(this);
        sortFirstDes.setIcon(descendingImg);
        generateRace.addActionListener(this);
        generateRace.setIcon(randomIcon);
        generateRaceWithPos.addActionListener(this);
        searchButton.addActionListener(this);
        searchButton.setIcon(searchIcon);
        sortRaceButton.addActionListener(this);
        sortRaceButton.setIcon(ascendingImg);

        setTitle("F1 Championship Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setIconImage(frameIcon.getImage());
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
        toolBar.setRollover(true);

        JToolBar midBar = new JToolBar();
        midBar.setFloatable(false);
        midBar.add(generateRaceWithPos);

        panel.add(toolBar);
        panel.add(topDriverTableRenderer());
        panel.add(midBar);
        panel.add(bottomDriverTableRenderer());

        return panel;
    }

    private JPanel raceTablePane() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        JToolBar searchToolBar = new JToolBar();
        searchToolBar.setFloatable(false);

        searchToolBar.add(searchText);
        searchToolBar.add(searchButton);

        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.PAGE_START;
        panel.add(searchToolBar, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.PAGE_START;
        panel.add(sortRaceButton, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.CENTER;
        panel.add(searchTableRenderer(), gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.CENTER;
        panel.add(sortTableRenderer(), gc);

        return panel;
    }

    private JScrollPane sortTableRenderer() {
        String[] header = {"Race Date", "No of Participants"};
        sortTableModel = new DefaultTableModel(header, 0);
        JTable sortTable = new JTable(sortTableModel);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        sortTableBody();

        sortTable.setEnabled(false);
        sortTable.setDefaultRenderer(Object.class, cellRenderer);
        sortTable.getTableHeader().setReorderingAllowed(false);
        sortTable.getTableHeader().setResizingAllowed(false);

        return new JScrollPane(sortTable);
    }

    private JScrollPane searchTableRenderer() {
        String[] header = {"Race Date", "Finish Position"};
        searchTableModel = new DefaultTableModel(header, 0);
        JTable searchTable = new JTable(searchTableModel);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        searchTableBody();

        searchTable.setEnabled(false);
        searchTable.setDefaultRenderer(Object.class, cellRenderer);
        searchTable.getTableHeader().setReorderingAllowed(false);
        searchTable.getTableHeader().setResizingAllowed(false);

        return new JScrollPane(searchTable);
    }

    private JScrollPane topDriverTableRenderer() {
        String[] header = {"Pos", "Driver Name", "Nationality", "Team/Constructor", "First Pos Count", "Second Pos Count", "Third Pos Count", "Pts"};

        topTableModel = new DefaultTableModel(header, 0);
        JTable driverTableTop = new JTable(topTableModel);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        topTableBody();

        driverTableTop.setPreferredScrollableViewportSize(new Dimension(950, 100));
//        driverTableTop.setFillsViewportHeight(true);
        driverTableTop.setEnabled(false);
        driverTableTop.setDefaultRenderer(Object.class, cellRenderer);
        driverTableTop.getTableHeader().setReorderingAllowed(false);
        driverTableTop.getTableHeader().setResizingAllowed(false);

        return new JScrollPane(driverTableTop);
    }

    private JScrollPane bottomDriverTableRenderer() {
        String[] header = {"Pos", "Driver Name", "Nationality", "Team/Constructor", "First Pos Count", "Second Pos Count", "Third Pos Count", "Pts"};

        bottomTableModel = new DefaultTableModel(header, 0);
        JTable driverTableBottom = new JTable(bottomTableModel);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        bottomTableBody();

        driverTableBottom.setPreferredScrollableViewportSize(new Dimension(950, 100));
//        driverTableBottom.setFillsViewportHeight(true);
        driverTableBottom.setEnabled(false);
        driverTableBottom.setDefaultRenderer(Object.class, cellRenderer);
        driverTableBottom.getTableHeader().setReorderingAllowed(false);
        driverTableBottom.getTableHeader().setResizingAllowed(false);

        return new JScrollPane(driverTableBottom);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sortPointDes) {
            topTableModel.setRowCount(0);
            driver.sort(new SortByPoints());
            topTableBody();
        } else if (e.getSource() == sortPointAsc) {
            topTableModel.setRowCount(0);
            driver.sort(new SortByPoints().reversed());
            topTableBody();
        } else if (e.getSource() == sortFirstDes) {
            topTableModel.setRowCount(0);
            driver.sort(new SortByFPS());
            topTableBody();
        } else if (e.getSource() == generateRace) {
            topTableModel.setRowCount(0);
            generateRandomRace();
            topTableBody();
        } else if (e.getSource() == generateRaceWithPos) {
            bottomTableModel.setRowCount(0);
            bottomTableBody();
        } else if (e.getSource() == searchButton) {
            String keyword = searchText.getText();
            if (keyword.length() != 0) {
                searchArrayLists(keyword);
                searchTableModel.setRowCount(0);
                searchTableBody();
                if (foundDate.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No exact matches found!", "Not Found!", JOptionPane.INFORMATION_MESSAGE, notFoundIcon);
                }
                foundDate.clear();
                foundFinishedPositions.clear();
//            foundStartPositions.clear();
//                searchText.setText("");
            }
        } else if (e.getSource() == sortRaceButton) {
            sortTableModel.setRowCount(0);
            races.sort(new SortByDate());
            sortTableBody();
        }
    }

    private void generateRandomRace() {
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> positions = new ArrayList<>();
        if (races.size() <= 30) {
            if (driver.size() != 0) {
                Formula1ChampionshipManager f1cm = new Formula1ChampionshipManager();
                ArrayList<Integer> positionList = new ArrayList<>();
                boolean validDate;
                boolean containDate;
                String dateString;
                int randomNumber;
                do {
                    String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
                    String month = randomNumberGenerator(12);
                    String day = randomNumberGenerator(31);
                    dateString = year + "/" + month + "/" + day;
                    validDate = f1cm.DateValidator(dateString, Integer.parseInt(year));
                    containDate = races.contains(dateString);
                } while (containDate && (!validDate));
                races.add(new RaceData(dateString));
                for (Formula1Driver formula1Driver : driver) {
                    do {
                        randomNumber = Integer.parseInt(randomNumberGenerator(25));
                    } while (positionList.contains(randomNumber));
                    positionList.add(randomNumber);
                    addPoints(randomNumber, formula1Driver, dateString);
                    names.add(formula1Driver.getDriverName());
                    positions.add(randomNumber);
                    formula1Driver.setRaceCount(1);
                }
                generatedInfoDialog(dateString, names, positions);
                names.clear();
                positions.clear();
                positionList.clear();
            } else {
                emptyDriverDialog();
            }
        } else {
            maxRacesDialog();
        }
    }

    private String randomNumberGenerator(int max) {
        String randomNumber = String.valueOf((int) (Math.random() * (max - 1 + 1) + 1));
        if (randomNumber.length() == 1) {
            randomNumber = "0" + randomNumber;
        }
        return randomNumber;
    }

    private void addPoints(int position, Formula1Driver formula1Driver, String dateString) {
        Formula1ChampionshipManager.AddDriverPoints(formula1Driver, dateString, position, races);
    }

    private void topTableBody() {
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
            topTableModel.addRow(tableData);
        }
    }

    private void bottomTableBody() {
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
            bottomTableModel.addRow(tableData);
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

    private void maxRacesDialog() {
        Object[] option = {"Continue" , "Delete"};
        int n = JOptionPane.showOptionDialog(this, "Maximum(30) Races added for this Season.  \nPress Delete to Clear Previous Race Date's! or Press Continue for Keep Data.      \n\n", "Maximum Races Added!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, binIcon, option, option[0]);
        if (n == 1) {
            races.clear();
        }
    }

    private void emptyDriverDialog() {
        JOptionPane.showMessageDialog(this, "Driver List is Empty!\nTry To add Driver's using Console Menu.", "Driver's Not Found!", JOptionPane.INFORMATION_MESSAGE, emptyIcon);
    }

    private void generatedInfoDialog(String date, ArrayList<String> names, ArrayList<Integer> position) {
        String messageTop = "Race Date : " + date + "\n";
        StringBuilder messageMid = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            messageMid.append(names.get(i)).append(" - ").append(position.get(i)).append("\n");
        }
        String fullMessage = messageTop + messageMid;
        JOptionPane.showMessageDialog(this, fullMessage, "Generated Race Info", JOptionPane.INFORMATION_MESSAGE, infoIcon);
    }
}
