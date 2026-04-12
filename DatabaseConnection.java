import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//**************************************
//Database Connection Class
//Handles connection to SQLite database
//**************************************

//database connection class
public class DatabaseConnection 
{
    //database url
    private static final String URL = "jdbc:sqlite:vehicle.db";

    //connect to database
    public static Connection connect() 
    {
        //connection object
        Connection conn = null;
        
        try 
        {
            //open connection
            conn = DriverManager.getConnection(URL);
            
            //success message
            System.out.println("Connected to the database successfully.");
        } 
        catch(SQLException e) 
        {
            //error message
            System.out.println("Connection error: " + e.getMessage());
        }
        
        //return connection
        return conn;
    }
}
