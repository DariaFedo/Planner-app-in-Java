package sample.database;

import sample.model.Task;
import sample.model.User;
import java.sql.*;

public class DBHandler extends Config {
    Connection dbConnection;

    // Connecting to the DataBase on MySQL server
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    /*WRITE -> taking SQL and passing data from Const Class// create own QUERY
    invoking dbConnection that we return as dConnection to in fact... make that connection. LOL*/

    //USER DATABASE
    //passing object from class User to create new instances of user class.
    public void registerUser(User user) {

        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USER_FIRSTNAME + "," + Const.USER_USERNAME
                + "," + Const.USER_PASSWORD + "," + Const.USER_GENDER + ")" + "VALUES(?,?,?,?)";

        try {

            /*A SQL statement is precompiled and stored in a PreparedStatement object. This object can then be used to
            efficiently execute this statement multiple times. Note: The setter methods (setShort, setString, and so on)
            for setting IN parameter values must specify types that are compatible with the defined SQL type of the input parameter.*/

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getGender());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //method which check if login data is OK, then it returns User from mysql
    public ResultSet checkLogin(User user) {

        ResultSet resultSet = null; //gives the column after proper login

        if (!user.getUsername().equals("") || !user.getPassword().equals("")) {

            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE "
                    + Const.USER_USERNAME + "=?" + " AND " + Const.USER_PASSWORD
                    + "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("error");

        }
        return resultSet;
    }

    // TASK DATABASE
    //create task
    public void createNewTask(Task task) {
        String insert = "INSERT INTO " + Const.TASKS_TABLE + "(" + Const.USER_ID + "," + Const.TASK_TASK + "," +
                Const.TASK_DATE + "," + Const.TASK_DESCRIPTION + ")" + "VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            System.out.println("From DBHandler UserId: " + task.getUserID());

            preparedStatement.setInt(1, task.getUserID());
            preparedStatement.setString(2, task.getTask());
            preparedStatement.setTimestamp(3, task.getDate());
            preparedStatement.setString(4, task.getDescription());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //check tasks
    public int getAllTasks(int userID) throws SQLException, ClassNotFoundException {

        String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE "
                + Const.USER_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userID);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return resultSet.getInt(1);
    }

    // search tasks for specified user

    public ResultSet getTasksByUser(int userID) {

        ResultSet resultTasks = null;

        String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " + Const.USER_ID + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userID);

            resultTasks = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultTasks;
    }

    public void updateTask(Timestamp date, String description, String task, int taskID) throws SQLException, ClassNotFoundException {

        String query = "UPDATE tasks SET date=?, description=?, task=? WHERE taskID=?";


        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setTimestamp(1, date);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, task);
        // preparedStatement.setInt(4, userId);
        preparedStatement.setInt(4, taskID);
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }

    public void deleteTask(int userId, int taskID) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE "+
                Const.USER_ID + "=?" + " AND " + Const.TASK_ID + "=?";

        PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, taskID);
        preparedStatement.execute();
        preparedStatement.close();
    }


}
