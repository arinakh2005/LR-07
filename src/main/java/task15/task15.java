package task15;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

/**
 * The class serves to execute task 15:
 * creating simple top-level window for a Java application
 * with possibility to interaction with DB Poem.
 * @author Arina Kholodnytska
 * @version 1.0
 */
public class task15 {
    /**
     * The main frame
     */
    public static JFrame frame;

    /**
     * The entry point of application.
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Poem poem = new Poem();
        Vector<Vector<String>> tableData = null;
        Vector<String> column = null;
        try {
            tableData = poem.doQuery("SELECT * FROM Poem", 7);
            column = poem.getTableColumn();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initJFrame();
        JTable table = new JTable(tableData, column);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 150));
        frame.add(scrollPane, BorderLayout.NORTH);

        JPanel jPanelButtons = new JPanel(new FlowLayout());

        JButton buttonFindByAuthor = new JButton("Знайти вірш за ім'ям автора");
        JButton buttonMostExclamationSentence = new JButton("Знайти вірш з найбільшою к-стю окличних речень");
        JButton buttonFewestNarrativeSentence = new JButton("Знайти вірш з найменшою к-стю розповідних речень");
        JButton buttonSonnetCount = new JButton("Порахувати к-сть сонет");

        buttonMostExclamationSentence.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<Vector<String>> resultMostExclamationSentence = poem.doQuery("SELECT * FROM Poem\n" +
                            "WHERE Count_exclamations IN (SELECT MAX(Count_exclamations) FROM Poem)", 7);
                    JOptionPane.showMessageDialog(frame, String.format("Вірш з найбільшою к-стю окличних речень (=%s) має назву '%s'", resultMostExclamationSentence.get(0).get(5), resultMostExclamationSentence.get(0).get(0)));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonFewestNarrativeSentence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<Vector<String>> resultFewestNarrativeSentence = poem.doQuery("SELECT * FROM Poem\n" +
                            "WHERE Count_narrative_sentence IN (SELECT MIN(Count_narrative_sentence) FROM Poem)", 7);
                    JOptionPane.showMessageDialog(frame, String.format("Вірш з найменшою к-стю розповідних речень (=%s) має назву '%s'", resultFewestNarrativeSentence.get(0).get(6), resultFewestNarrativeSentence.get(0).get(0)));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonSonnetCount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Vector<Vector<String>> resultSonnetCount = poem.doQuery("SELECT COUNT(Title) FROM Poem WHERE TYPE = \"Sonet\"", 1);
                    JOptionPane.showMessageDialog(frame, ("К-сть сонет = " + resultSonnetCount.get(0).get(0)));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonFindByAuthor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FindByAuthorFrame findByAuthorFrame = new FindByAuthorFrame();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        jPanelButtons.add(buttonFindByAuthor);
        jPanelButtons.add(buttonMostExclamationSentence);
        jPanelButtons.add(buttonFewestNarrativeSentence);
        jPanelButtons.add(buttonSonnetCount);

        frame.add(jPanelButtons, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Init the main frame
     */
    public static void initJFrame() {
        frame = new JFrame("Poems");
        frame.setSize(500, 350);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
