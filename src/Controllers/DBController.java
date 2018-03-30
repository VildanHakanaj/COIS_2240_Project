package Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {

    public static void main(String[]args){
        try{
            /*accepts connection string and returns a connection instance. How do I know that? DOCUMENTATION! :)

            Instead of DriverManager, we can utilize Data Source Objects as well, if anyone writes a 200 word
            explanation of what that is and the difference between data source object and driver manager you will be
            awarded 1% as EC.

            If this database did not exist, the Driver Manager will create one for us.
             */
            String url = "jdbc:sqlite:src/data.db";
            Connection conn = DriverManager.getConnection(url);
            /* This is a statement object, recall that Java is an OOP language therefore we created an object
            statement and we will execute this instance of statement we have created wth a .execute method.
             */

            Statement statement = conn.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS contacts (name TEXT, phone INTEGER, email TEXT)");
            statement.execute("INSERT INTO contacts (name, phone, email) VALUES ('Maggie', '565656', 'aaddas@trentu.ca')");
            /* we must close the statement and the connection as well. Does anyone know why we do that?
                If you close the connection first you will get an error.
             */
            statement.close();
            conn.close();

        }catch (SQLException e){

            System.out.println("Something went wrong: " + e.getMessage());
        }//end catch bracket
    }//end main method bracket


}//end main class bracket
