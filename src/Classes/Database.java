package Classes;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Hashtable;

public class Database {
    private Connection conn = null;
    private Statement stm =  null;

    public Database() throws SQLException {
        try{
            //Get the connection
            conn = connect();
            //Create the query for the users table
            String sql ="CREATE TABLE IF NOT EXISTS `users` (" +
                        "`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,"+
                        "`name`	varchar(50) NOT NULL,"+
                        "`email`	varchar(50) NOT NULL,"+
                        "`username`	varchar(50) NOT NULL,"+
                        "`pass`	varchar(50) NOT NULL);";
            stm = conn.createStatement();
            stm.executeUpdate(sql);
            //Create events table if it doesn't exist
            sql = "CREATE TABLE IF NOT EXISTS`Events` (" +
                    "`ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "`fk_userID` INTEGER NOT NULL," +
                    "`Title` NUMERIC," +
                    "`Date` BLOB," +
                    "`Duration` INTEGER," +
                    "`Description` TEXT," +
                    "`Privacy` INTEGER," +
                    "`Thirty` TEXT," +
                    "`Hour` TEXT," +
                    "`Day` TEXT," +
                    "`Week` TEXT," +
                    "`Repeat` INTEGER," +
                    "`Colour` TEXT," +
                    "`Start` INTEGER," +
                    "`End` NUMERIC," +
                    "`Color` INTEGER," +
                    "FOREIGN KEY(fk_userID) REFERENCE users(id);" + //Add a foreign key to join the tables.
                    ");";
            stm = conn.createStatement();
            stm.executeUpdate(sql);
            stm.close(); //Close the connection and statement
            conn.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //Inserts the users into the database
    public void insertUser(Hashtable<String, String> user) throws NoSuchAlgorithmException, SQLException {
        String name, email, uid, pwd;
        name = user.get("uid");
        email = user.get("email");
        uid = user.get("uid");
        pwd = user.get("pwd");

        pwd = MyValidation.hashPassword(pwd);
        String sql ="INSERT INTO users (name, email, username, pass) VALUES('" + name + "', '" + email + "', '" + uid + "', '" + pwd + "');";
        stm = conn.createStatement();
        stm.executeQuery(sql);
    }
    //Deletes the user
    public void deleteUserAndEvents(int userId) throws SQLException {
        conn = connect(); //Get the connection
        String sql = "DELETE * FROM events, users WHERE userid = '" + userId + "'";
        stm = conn.createStatement();
        stm.executeUpdate(sql);
        stm.close();
        conn.close();
    }

    public void insertEvents(String event[]){
        String sql = "INSERT INTO events (fk_userID, Title, Date, Duration, Description, Privacy, Thirty, Hour, Day, Week, Repeat, Colour, Start, End, Color) ";
        sql += "VALUES( ";
        for (int i = 0; i < event.length ; i++) {
            sql += event[i];
        }
    }

    //A connection method so we dont have to type the url all over again
    //Returns the connection handle
    public Connection connect() throws SQLException {
        return conn = DriverManager.getConnection("jdbc:sqlite:src\\data.db");
    }


}
