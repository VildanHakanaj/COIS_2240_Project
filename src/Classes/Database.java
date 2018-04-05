package Classes;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Hashtable;

public class Database {
    private Connection conn = null;
    private Statement stm =  null;

    //Will create the tables if they don't exist
    //Add the event table
    //Add the users table
    //Add the index in both table for fast search of username and id title
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
            stm.close(); //Close the connection and statement

            stm = conn.createStatement();
            stm.executeUpdate("CREATE INDEX IF NOT EXISTS `username` ON `users` ( `username` );"); //Set an index on the user table
            stm.executeUpdate("INSERT INTO users (name, email, username, pass) VALUES ('Vildan', 'email@gmail.com', 'username', 'password')");
            stm.close();
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
                    "FOREIGN KEY(fk_userID) REFERENCES `users`(id)" + //Add a foreign key to join the tables.
                    ");";
            stm = conn.createStatement();
            stm.executeUpdate(sql);
            //Index for the event
            stm.executeUpdate("CREATE INDEX IF NOT EXISTS `title` ON `Events` (`Title`);"); //Add index in the events table for faster search
            stm.close();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
    }
    //Inserts the users into the database
    public void insertUser(Hashtable<String, String> user) throws NoSuchAlgorithmException, SQLException {
        conn = connect(); //Open the connection
        String name, email, uid, pwd;

        name = user.get("uid");
        email = user.get("email");

        uid = user.get("uid");
        pwd = user.get("pwd");

        //Hash the password before inserting.
        pwd = MyValidation.hashPassword(pwd);
        String sql ="INSERT INTO users (name, email, username, pass) VALUES('" + name + "', '" + email + "', '" + uid + "', '" + pwd + "');";
        stm = conn.createStatement();
        stm.executeQuery(sql);

        closeConnection(); //Close the connection;
    }
////    //Deletes the user
////    public void deleteUserAndEvents(String username) throws SQLException {
////        conn = connect(); //Get the connection
////        String sql = "DELETE * FROM events, users WHERE username = '" + username + "'";
////        stm = conn.createStatement();
////        stm.executeUpdate(sql);
////        stm.close();
////        closeConnection();
////    }

////    //Insert events
////    //Insert events in the database
//////    public void insertEvent(String event[]){
//////        String sql = "INSERT INTO events (fk_userID, Title, Date, Duration, Description, Privacy, Thirty, Hour, Day, Week, Repeat, Colour, Start, End, Color) ";
//////        sql += "VALUES( ";
//////        for (int i = 0; i < event.length ; i++) {
//////            sql += event[i];
//////        }
//////    }

    //Selects users by username
    public ResultSet selectUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        conn = connect();
        stm = conn.createStatement();
        return stm.executeQuery(sql);
    }

    //A connection method so we dont have to type the url all over again
    //Returns the connection handle
    public Connection connect() throws SQLException {
        return conn = DriverManager.getConnection("jdbc:sqlite:src\\data.db");
    }

    //Helper method to close the connection
    public void closeConnection() throws SQLException{
        if(conn != null){ //close the connection;
            conn.close();
        }
    }
}
