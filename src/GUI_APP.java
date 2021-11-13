import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI_APP extends JFrame implements ActionListener {
    private ArrayList<Formula1Driver> driver;
    private List<String> teamNameList;
    private List<String> raceDates;

    /*--------------------- UI Components ---------------------*/
    private JButton driverTableButton = new JButton("F1 Driver Table");
    private JButton raceDateButton = new JButton("Race Date Table");

    /**
     * Main Constructor Of the GUI
     * @param driver - driver ArrayList
     * @param teamNameList - teamNameList String List
     * @param raceDates - raceDates String List
     */
    public GUI_APP(ArrayList<Formula1Driver> driver, List<String> teamNameList, List<String> raceDates) {
        this.driver = driver;
        this.teamNameList = teamNameList;
        this.raceDates = raceDates;

        setTitle("F1 Championship Manager");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
