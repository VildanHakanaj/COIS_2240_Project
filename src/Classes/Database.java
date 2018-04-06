package Classes;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Hashtable;


/*
* NAME DATABASE:
* This class takes care of connection to the database
* Anything that has to do with the database interaction
* such as:
*
* - Creating the initial users table
* - Creating the initial Events table
*
* - InsertUsers
* - SelectUsers
* - Delete Users
*
* - Insert Events
* - Select Events (By ID and Title)
* - Delete Events (By user id or event id)
*
* - Get a connection handle
* - Close the connection
*
* */


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
//            stm.executeUpdate("INSERT INTO users (name, email, username, pass) VALUES ('Vildan', 'email@gmail.com', 'username', 'password')");
            stm.close();

            //Create events table if it doesn't exist
            sql = "CREATE TABLE IF NOT EXISTS `Events` (" +
                    "`ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "`fk_userID` INTEGER NOT NULL," +
                    "`Title` TEXT," +
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
                    "`Strt` INTEGER," +
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
        stm.executeUpdate(sql);
        stm.close();
        closeConnection(); //Close the connection;

    }

    /*
    * Since we have decided to do users with the events that means that the events need to belong to users
    * I have put a foreign key between the users and the events to constrain them together.
    * Wich that means if you want to delete users you are going to have to delete events associated with that user.
    * If its to much to change to add the userId
    * Than you can just ignore this method its just here just in case.
    *
    * */
    //Deletes the user and the event associated with them
    public void deleteUserAndEvent(String userId) throws SQLException {
        conn = connect(); //Get the connection
        String sql = "DELETE FROM events, users WHERE username = '" + userId + "'";
        stm = conn.createStatement();
        stm.executeUpdate(sql);
        stm.close();
        closeConnection();
    }

//    Insert events
//    Insert events in the database
//    public void insertEvent(Hashtable<Strin   g, String> event) throws SQLException {
//        String sql = "INSERT INTO events (fk_userID, Title, Date, Duration, " +
//                "Description, Privacy, Thirty, " +
//                "Hour, Day, Week, Repeat, Colour, " +
//                "Start, End, Color, Str) ";
//        sql += String.format("VALUES(%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s';",
//                event.get("uid"), event.get("Title"),
//                event.get("Date"), event.get("Duration"),
//                event.get("Description"), event.get("Privacy"),
//                event.get("Thirty"), event.get("Hour"),
//                event.get("Day"), event.get("Week"),
//                event.get("Repeat"), event.get("Colour"),
//                event.get("Start"), event.get("End"),
//                event.get("Color"), event.get("Strt"));
//
//        System.out.println(sql);
//        conn = connect();
//        stm = conn.createStatement();
//        stm.executeUpdate(sql);
//    }


    /*
    * @String username the user username
    * Selects the user by it username
    * */
    public ResultSet selectUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        conn = connect();
        stm = conn.createStatement();
        return stm.executeQuery(sql);

    }


    /*--------------------------------------------------------------
    * EVENT FUNCTION FOR DATABASE
    * ------------------------------------------------------------*/


    /*
    * @event is all the string variables
    * @numbers are all the integer variables
    *
    * This method will insert the event
    *
    * Not sure yet on the approach to take on this.
    * */
    public void insertEvent(String[] event, int[] numbers){
        String sql = "INSERT INTO Events (ID, fk_userID, Duration, Privacy, Repeat, Start, End, Color, Title,)";
    }

    /*
    * @int id is the event id
    * this method will select the event based on the id that is passed to it.
    * */
    public ResultSet selectEventById(int id) throws SQLException {
        String sql = "SELECT * FROM Events WHERE ID = " + id;
        conn = connect();
        stm = conn.createStatement();
        ResultSet set = stm.executeQuery(sql);

           //Uncomment this to make sure you are getting results back
//        if(set.next()){
//            System.out.println(set.getString("Title"));
//        }
        return set; //return the results from the selection
    }

    /*
    * @int id is the event id you want to remove
    * This method will delete the event with the id passed from the database
    * */
    public void deleteEvent(int id) throws SQLException {
        String sql = "DELETE FROM Events WHERE ID = " + id;
            conn = connect();
            stm = conn.createStatement();
            stm.executeUpdate(sql);
            stm.close();
    }



    /*HELPER METHODS FOR THE DATABASE CLASS
    * -------------------------------------*/

    /*
    * Gets a connection handle.
    * @return the connection handle
    * Call this method when you need to connect
    * */
    public Connection connect() throws SQLException {
        return conn = DriverManager.getConnection("jdbc:sqlite:src\\data.db");
    }

    /*
    * Closes the connection
    * Check if there is a connection first
    * if yes then close it
    * otherwise it ignores it.
    * */
    public void closeConnection() throws SQLException{
        if(conn != null){ //close the connection;
            conn.close();
        }
    }
}
