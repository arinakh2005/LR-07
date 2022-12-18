package task15;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

/**
 * The class serves to find poems by author name
 * @author Arina Kholodnytska
 * @version 1.0
 */
public class FindByAuthorFrame {
    /**
     * The main frame
     */
    JFrame jFrame;

    /**
     * Class constructor. Creates the frame
     */
    public FindByAuthorFrame() {
        jFrame = new JFrame();
        jFrame.setSize(500, 200);
        jFrame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Прізвище та ім'я автора:");
        JTextField author = new JTextField("Введіть значення");
        JButton find = new JButton("Знайти твори");

        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String author_name = author.getText();

                Poem poem = new Poem();
                try {
                    jFrame.getContentPane().removeAll();
                    addInputLineElements(label, author, find);
                    jFrame.repaint();

                    String query = "SELECT * FROM Poem WHERE Author = '" + author_name + "'";
                    Vector<Vector<String>> resultData = poem.doQuery(query, 7);
                    JTable table = new JTable(resultData, poem.getTableColumn());
                    JScrollPane scrollPane = new JScrollPane(table);
                    scrollPane.setPreferredSize(new Dimension(500, 100));
                    jFrame.add(scrollPane);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        addInputLineElements(label, author, find);
        jFrame.setVisible(true);
    }

    private void addInputLineElements(JLabel label, JTextField value, JButton find) {
        jFrame.add(label);
        jFrame.add(value);
        jFrame.add(find);
    }
}
