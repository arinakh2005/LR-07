import org.junit.Test;
import task15.Poem;
import java.sql.SQLException;
import java.util.Vector;
import static org.junit.Assert.assertEquals;
import static task15.task15.frame;
import static task15.task15.initJFrame;

public class Task15Test {
    @Test
    public void initJFrameWithTitle() {
        System.out.println("should check initialization JFrame with title");
        String expected = "Poems";

        initJFrame();
        String actual = frame.getTitle();

        assertEquals(actual, expected);
    }

    @Test
    public void checkDBConnection() throws SQLException {
        System.out.println("should check Poem DB connection");
        int expectedRows = 1;

        Poem poem = new Poem();
        Vector<Vector<String>> actualRows = poem.doQuery("SELECT * FROM Poem WHERE Author = 'Іван Франко'", 7);

        assertEquals(actualRows.size(), expectedRows);
    }
}
