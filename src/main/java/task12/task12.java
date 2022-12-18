package task12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

/**
 * The class serves to execute task 12:
 * creating simple top-level window for a Java application
 * with possibility to interaction with DB Point.
 * @author Arina Kholodnytska
 * @version 1.0
 */
public class task12 {
    /**
     * The main frame
     */
    public static JFrame frame;

    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Point point = new Point();
        Vector<Vector<String>> tableData = null;
        Vector<String> column = null;
        try {
            tableData = point.doQuery("SELECT * FROM Point", 3);
            column = point.getTableColumn();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initJFrame();
        JTable table = new JTable(tableData, column);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        frame.add(scrollPane, BorderLayout.NORTH);

        JPanel jPanelButtons = new JPanel(new FlowLayout());
        JButton buttonClosestPoint = new JButton("Отримати найбільш наближену точку до заданої");
        JButton buttonTheMostDistantPoint = new JButton("Отримати найбільш віддалену точку від заданої");
        JButton buttonPointsWhichHaveCommonLine = new JButton("Вивести точки, що лежать на одній прямій із заданою прямою");

        buttonClosestPoint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InputPointFrame inputFrameForClosestPoint = new InputPointFrame(true);
            }
        });

        buttonTheMostDistantPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputPointFrame inputFrameForFarthestPoint = new InputPointFrame(false);
            }
        });

        buttonPointsWhichHaveCommonLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputLineFrame inputLineFrame = new InputLineFrame();
            }
        });

        jPanelButtons.add(buttonClosestPoint);
        jPanelButtons.add(buttonTheMostDistantPoint);
        jPanelButtons.add(buttonPointsWhichHaveCommonLine);

        frame.add(jPanelButtons, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Init main frame
     */
    public static void initJFrame() {
        frame = new JFrame("Points");
        frame.setSize(500, 350);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
