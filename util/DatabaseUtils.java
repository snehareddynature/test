package in.at.util;

import java.sql.*;

public class DatabaseUtils {

    public static Connection connection = null;

    public static Connection getDBConnection() {
        //String dbName = PropUtils.getPropValue(PropUtils.configProp, "dbServerName");
        String dbName = PropUtils.getProperties().getProperty("dbServerName");
        System.out.println("************"+dbName+"*************");
        //String uname = FrameworkUtils.getDecPass(PropUtils.getPropValue(BaseTest.configProp, "db2username"));
        //String pwd = FrameworkUtils.getDecPass(PropUtils.getPropValue(BaseTest.configProp, "db2password"));
        try {
            connection = DriverManager.getConnection(dbName);
            connection.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("SQLite Database Connection Failed! Check output console");
            e.printStackTrace();
            System.exit(1);
        }
        if (connection != null) {
            System.out.println("SQLite Database Connected");
        }
        return connection;
    }

    public static void closeDBConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection to the database closed successfully...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getResultSet(Connection connection, String query) throws SQLException {

        // create the java statement
        Statement statement = connection.createStatement();

        // execute the query, and get a java result set
        System.out.println("Query : " + query);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResultSet resultSet = statement.executeQuery(query);
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultSet;

    }
}