package task12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

/**
 * The class serves to enter point data
 * @author Arina Kholodnytska
 * @version 1.0
 */
public class InputPointFrame extends JFrame {
    /**
     * The frame instance
     */
    JFrame jFrame;

    /**
     * Class constructor. Creates the frame.
     * @param findClosestPoint is for finding the closest point
     */
    public InputPointFrame(boolean findClosestPoint) {
        jFrame = new JFrame();
        jFrame.setSize(200, 200);
        jFrame.setLayout(new FlowLayout());

        JLabel labelCoordX = new JLabel("Координата х точки:");
        JTextField coordX = new JTextField("Введіть значення");
        JLabel labelCoordY = new JLabel("Координата y точки:");
        JTextField coordY = new JTextField("Введіть значення");

        JButton find = new JButton("Знайти");

        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x_value = Integer.parseInt(coordX.getText());
                int y_value = Integer.parseInt(coordY.getText());

                Point point = new Point();
                try {
                    jFrame.getContentPane().removeAll();
                    addInputElements(labelCoordX, labelCoordY, coordX, coordY, find);
                    jFrame.repaint();

                    String query;
                    if (findClosestPoint) {
                        query = "SELECT * FROM Point \n" +
                                "ORDER BY ABS (coordinate_x - " + x_value + "), (coordinate_y - " + y_value + ")\n" +
                                "LIMIT 1";
                    } else {
                        query = "SELECT * FROM Point\n" +
                                "ORDER BY (coordinate_x - " + x_value + "), (coordinate_y - " + y_value + ") ASC\n" +
                                "LIMIT 1";
                    }
                    Vector<Vector<String>> resultData = point.doQuery(query, 3);
                    JTable table = new JTable(resultData, point.getTableColumn());
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setPreferredSize(new Dimension(300, 50));
                    jFrame.setSize(400, 300);
                    jFrame.add(scrollPane);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        addInputElements(labelCoordX, labelCoordY, coordX, coordY, find);

        jFrame.setVisible(true);
    }

    private void addInputElements(JLabel labelCoordX, JLabel labelCoordY, JTextField coordX, JTextField coordY, JButton find) {
        jFrame.add(labelCoordX);
        jFrame.add(coordX);
        jFrame.add(labelCoordY);
        jFrame.add(coordY);
        jFrame.add(find);
    }
}
