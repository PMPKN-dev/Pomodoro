package Hub.Foundation;

import java.sql.*;

public class DB {

    static Connection con;

    /**
     * this creates and returns a connection to the database, useful for controllers and the like.
     * Use together with closeCon() to conveniently handle the connection from other classes
     * @return a Connection to the database
     */
    public static Connection getCon(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=CamperDB", "sa", "1234");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }


    /**
     * closes the connection made by getCon()
     */
    public static void closeCon(){
        try {
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
