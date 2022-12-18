package task12;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

/**
 * The class serves to enter the equation of a line
 * @author Arina Kholodnytska
 * @version 1.0
 */

public class InputLineFrame extends JFrame {
    /**
     * The frame instance.
     */
    JFrame jFrame;

    /**
     * Class constructor. Creates the frame
     */
    public InputLineFrame() {
        jFrame = new JFrame();
        jFrame.setSize(250, 200);
        jFrame.setLayout(new FlowLayout());

        JLabel labelLine = new JLabel("Пряму задано рівнянням у = kx + b\n");
        labelLine.setHorizontalAlignment(SwingConstants.CENTER);
        labelLine.setPreferredSize(new Dimension(250, 30));
        JLabel labelK = new JLabel("Коефцієнт k:");
        JTextField valueK = new JTextField("Введіть значення k:");
        JLabel labelB = new JLabel("Значення b:");
        JTextField valueB = new JTextField("Введіть значення b:");
        JButton find = new JButton("Знайти");

        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double k_value = Double.parseDouble(valueK.getText());
                double b_value = Double.parseDouble(valueB.getText());

                Point point = new Point();
                try {
                    jFrame.getContentPane().removeAll();
                    addInputLineElements(labelLine, labelK, valueK, labelB, valueB, find);
                    jFrame.repaint();

                    String query = "SELECT * FROM Point";
                    Vector<Vector<String>> selectData = point.doQuery(query, 3);
                    Vector<Vector<String>> resultData = new Vector<>();
                    for (int i = 0; i < selectData.size(); i++) {
                        double coordY = Double.parseDouble(selectData.get(i).get(2));
                        double coordX = Double.parseDouble(selectData.get(i).get(1));
                        if (coordY == (k_value * coordX + b_value))
                            resultData.add(selectData.get(i));
                    }
                    JTable table = new JTable(resultData, point.getTableColumn());
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setPreferredSize(new Dimension(300, 150));
                    jFrame.setSize(400, 300);
                    jFrame.add(scrollPane);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        addInputLineElements(labelLine, labelK, valueK, labelB, valueB, find);

        jFrame.setVisible(true);
    }

    private void addInputLineElements(JLabel labelLine, JLabel labelK, JTextField valueK, JLabel labelB, JTextField valueB, JButton find) {
        jFrame.add(labelLine);
        jFrame.add(labelK);
        jFrame.add(valueK);
        jFrame.add(labelB);
        jFrame.add(valueB);
        jFrame.add(find);
    }
}
