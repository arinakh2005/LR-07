package task12;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

/**
 * The class serves to connecting and interaction with Point DB
 * @author Arina Kholodnytska
 * @version 1.0
 */
public class Point {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    /**
     * Class constructor. Set connecting with Point DB
     */
    public Point() {
        String userName = "root";
        String password = "";
        String connectionUrl = "jdbc:mysql://localhost:3306/point";
        try {
            setConnectingDB(connectionUrl, userName, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
    }

    /**
     * Do query
     *
     * @param query       the SQL-query
     * @param countColumn the count column in DB table
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
}
