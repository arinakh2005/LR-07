import org.junit.Test;
import task12.Point;
import java.sql.SQLException;
import java.util.Vector;
import static org.junit.Assert.assertEquals;
import static task12.task12.frame;
import static task12.task12.initJFrame;

public class Task12Test {
    @Test
    public void initJFrameWithTitle() {
        System.out.println("should check initialization JFrame with title");
        String expected = "Points";

        initJFrame();
        String actual = frame.getTitle();

        assertEquals(actual, expected);
    }

    @Test
    public void checkDBConnection() throws SQLException {
        System.out.println("should check Point DB connection");
        int expectedRows = 1;

        Point point = new Point();
        Vector<Vector<String>> actualRows = point.doQuery("SELECT * FROM Point WHERE title = 'A'", 3);

        assertEquals(actualRows.size(), expectedRows);
    }
}
