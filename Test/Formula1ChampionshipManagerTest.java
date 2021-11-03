import org.junit.Test;

import static org.junit.Assert.*;

public class Formula1ChampionshipManagerTest {

    @Test
    public void dateValidator() {
        String dateString = "2021/02/28";

        Formula1ChampionshipManager f1cm = new Formula1ChampionshipManager();
        boolean actualValue = f1cm.DateValidator(dateString);
        assertTrue(actualValue);
    }
}