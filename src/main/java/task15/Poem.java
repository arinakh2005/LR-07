package task15;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Vector;

/**
 * The class serves to connecting and interaction with Poem DB
 * @author Arina Kholodnytska
 * @version 1.0
 */
public class Poem {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    /**
     * Class constructor. Set connecting with Poem DB
     */
    public Poem() {
        String userName = "root";
        String password = "";
        String connectionUrl = "jdbc:mysql://localhost:3306/poem";
        try {
            setConnectingDB(connectionUrl, userName, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }

    /**
     * Do SQL query
     *
     * @param query       the query
     * @param countColumn the count column
     * @return the vector of result
     * @throws SQLException the sql exception
     */
    public Vector<Vector<String>> doQuery(String query, int countColumn) throws SQLException {
        resultSet = statement.executeQuery(query);

        Vector<Vector<String>> data = new Vector<>();
        Vector<String> row;
        while (resultSet.next()) {
            row = new Vector<String>(countColumn);
            for(int i = 1; i <= countColumn; i++) {
                row.add(resultSet.getString(i));
            }
            data.add(row);
        }

        return data;
    }

    /**
     * Gets table column
     *
     * @return the table column
     * @throws SQLException the sql exception
     */
    public Vector<String> getTableColumn() throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int countColumn = resultSetMetaData.getColumnCount();
        Vector<String> column = new Vector<>(countColumn);
        for (int i = 1; i <= countColumn; i++) {
            column.add(resultSetMetaData.getColumnName(i));
        }

        return column;
    }

    private void setConnectingDB(String connectionUrl, String userName, String password) throws SQLException {
        connection = DriverManager.getConnection(connectionUrl, userName, password);
        statement = connection.createStatement();
    }

    private void insertData() throws SQLException, FileNotFoundException {
        String insertQuery = "INSERT INTO Poem (Title, Author, Year, Content, Type, Count_exclamations, Count_narrative_sentence)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?) ";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, "Лісова пісня");
        preparedStatement.setString(2, "Леся Українка");
        preparedStatement.setString(3, "1911");
        FileInputStream fin = new FileInputStream("poem3.txt");
        preparedStatement.setBinaryStream(4, fin);
        preparedStatement.setString(5, "Other");
        preparedStatement.setString(6, "2");
        preparedStatement.setString(7, "5");
        preparedStatement.execute();
    }
}
