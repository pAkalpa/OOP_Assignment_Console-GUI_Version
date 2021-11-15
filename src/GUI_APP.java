import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI_APP extends JFrame implements ActionListener {
    private ArrayList<Formula1Driver> driver;
    private ArrayList<RaceData> races;
    private List<String> teamNameList;

    /*--------------------- UI Components ---------------------*/
    private JToolBar toolBar = new JToolBar();
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
    private JButton sortPointDes = new JButton("Sort by Point (Descending)");
    private JButton sortPointAsc = new JButton("Sort by Point (Ascending)");
    private JButton sortFirstDes = new JButton("Sort by First Positions (Descending)");
    private JButton generateRace = new JButton("Generate Race Randomly");
    private JButton generateRaceWithPos = new JButton("Generate Race Randomly(With Positions)");
    private JTable driverTableTop;
    private JTable driverTableBottom;
    private DefaultTableModel tableModel;

    /**
     * Main Constructor Of the GUI
     * @param driver - driver ArrayList
     * @param teamNameList - teamNameList String List
     * @param races - races ArrayList
     */
    public GUI_APP(ArrayList<Formula1Driver> driver, ArrayList<RaceData> races, List<String> teamNameList) {
        this.driver = driver;
        this.races = races;
        this.teamNameList = teamNameList;

        tabbedPane.addTab("Driver Table", driverTablePane());
        tabbedPane.addTab("Race Table", raceTablePane());
        getContentPane().add(tabbedPane);

        sortPointDes.addActionListener(this);
        sortPointAsc.addActionListener(this);
        sortFirstDes.addActionListener(this);

        setTitle("F1 Championship Manager");
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private JPanel driverTablePane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

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
        return panel;
    }

    private JScrollPane topDriverTableRenderer() {
        String[] header = { "Pos", "Driver Name", "Nationality", "Team/Constructor", "First Pos Count", "Second Pos Count", "Third Pos Count", "Pts" };

        tableModel = new DefaultTableModel(header,0);
        driverTableTop = new JTable(tableModel);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);

        tableBody();

        driverTableTop.setPreferredScrollableViewportSize(new Dimension(950, 100));
        driverTableTop.setFillsViewportHeight(true);
        driverTableTop.setEnabled(false);
        driverTableTop.setDefaultRenderer(Object.class, dtcr);
        driverTableTop.getTableHeader().setReorderingAllowed(false);
        driverTableTop.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(driverTableTop);
        return scrollPane;
    }

    private JScrollPane bottomDriverTableRenderer() {
        String[] header = { "Pos", "Driver Name", "Nationality", "Team/Constructor", "First Pos Count", "Second Pos Count", "Third Pos Count", "Pts" };

        tableModel = new DefaultTableModel(header,0);
        driverTableBottom = new JTable(tableModel);
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);

        tableBody();

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
        }
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

            Object[] tableData = { position, driverName, driverLocation, teamName, fps, spc, tpc, pts };
            tableModel.addRow(tableData);
        }
    }
}
