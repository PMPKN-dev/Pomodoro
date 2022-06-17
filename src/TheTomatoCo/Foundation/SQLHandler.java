package TheTomatoCo.Foundation;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.Objects;

public class SQLHandler {

    public static void createConsultant(Connection con,String ID, String consultantName, int pomodoroLength, int shortBreakLength, int longBreakLength) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO tbl_Consultant values(?,?,?,?,?)");
        p.setString(1,ID);
        p.setString(2,consultantName);
        p.setInt(3,pomodoroLength);
        p.setInt(4,shortBreakLength);
        p.setInt(5,longBreakLength);
        p.execute();
        p.close();
    }

    public static String checkLogin(Connection con, String ConsultantID) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT Password FROM tbl_Login WHERE ConsultantID=?");
        p.setString(1,ConsultantID);
        ResultSet rs = p.executeQuery();
        rs.next();
        String theresult = rs.getString(1);
        p.close();
        return theresult;
    }

    public static int GetConsultantID(Connection con, String Username) throws SQLException{
        PreparedStatement p = con.prepareStatement("Select ConsultantID from tbl_Consultant where ConsultantName = ?");
        p.setString(1,Username);
        ResultSet rs = p.executeQuery();
        rs.next();
        int ID = rs.getInt(1);
        p.execute();
        p.close();
        return ID;
    }

    public static int VerifyLogin(Connection con, String Password, String Username) throws SQLException{
        PreparedStatement p = con.prepareStatement("Select PermissionLevel from tbl_Login where Password = ? and ConsultantID = ?");
        p.setString(1,Password);
        p.setString(2,Username);
        ResultSet rs = p.executeQuery();
        rs.next();
        int lvl = rs.getInt(1);
        p.close();
        return lvl;
    }

    public static int AmountofProjects(Connection con) throws SQLException {
        PreparedStatement p = con.prepareStatement("Select max(ProjectID) from tbl_Project");
        ResultSet rs = p.executeQuery();
        rs.next();
        int value = rs.getInt(1);
        p.close();
        return value;
    }

    public static String ProjectName(Connection con) throws SQLException{
        PreparedStatement p = con.prepareStatement("Select ProjectName from tbl_Project");
        ResultSet rs = p.executeQuery();
        rs.next();
        String Pname = rs.getString(1);
        p.close();
        return Pname;
    }

    public static int getProjectID(Connection con, String ProjectName) throws SQLException{
        PreparedStatement p = con.prepareStatement("Select ProjectID from tbl_Project where ProjectName = ?");
        p.setString(1,ProjectName);
        ResultSet rs = p.executeQuery();
        rs.next();
        int ID = rs.getInt(1);
        p.close();
        return ID;
    }

    public static void createTask(Connection con, String ConsultantID, int ProjectID, String TaskName, int AssignedPomodoros) throws SQLException {
        PreparedStatement p = con.prepareStatement("Insert into tbl_Tasks values(?,?,?,?,0)");
        p.setString(1, ConsultantID);
        p.setInt(2,ProjectID);
        p.setString(3,TaskName);
        p.setInt(4,AssignedPomodoros);
        p.executeUpdate();
        p.close();

    }

    public static void updateConsultant(Connection con, int pomodoroLength, int shortBreakLength, int longBreakLength, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("UPDATE tbl_Consultant SET PomodoroTime = ?, PomodoroShortBreakTime = ?, PomodoroLongBreakTime = ? WHERE ConsultantID = ?");
        p.setInt(1,pomodoroLength);
        p.setInt(2,shortBreakLength);
        p.setInt(3,longBreakLength);
        p.setString(4,ConsultantID);
        p.executeUpdate();
        p.close();
    }

    public static int setPomodoroTime(Connection con, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroTime FROM tbl_Consultant WHERE ConsultantID = ?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        p.close();
        return rs.getInt(1);
    }

    public static int setShortBreakTime(Connection con, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroShortBreakTime FROM tbl_Consultant WHERE ConsultantID = ?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        p.close();
        return rs.getInt(1);
    }

    public static int setLongBreakTime(Connection con, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroLongBreakTime FROM tbl_Consultant WHERE ConsultantID = ?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        p.close();
        return rs.getInt(1);
    }

    public static int getPermissionLevel (Connection con, int userName) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT PermissionLevel FROM tbl_Login WHERE ConsultantID=?");
        p.setInt(1,userName);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        p.close();
        return rs.getInt(1);
    }

    public static void deactivateConsultant(Connection con, String ID) throws SQLException {
        PreparedStatement p = con.prepareStatement("UPDATE tbl_StatusOfConsultant SET Status=0 WHERE ConsultantID=?");
        p.setString(1,ID);
        p.execute();
        p.close();
    }

    public static boolean checkUsername(String ID) throws SQLException{
        //gets the ID from parameter from database
        PreparedStatement p = DB.getCon().prepareStatement("SELECT ConsultantID FROM tbl_Consultant WHERE ConsultantID=?");
        p.setString(1,ID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        //tries to get a result from the result set
        try {
            rs.getString(1);
            p.close();
            return true; //if the given name is already in the database, returns true
        }
        catch (SQLServerException e){
            p.close();
            return false; //if the result comes back faulty, meaning that there is no result, it returns false
        }
    }

    public static void createConsultantLogin(Connection con, String ID) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO tbl_Login VALUES (?,?,?)");
        p.setString(1, ID);
        p.setString(2,"0000");//default password
        p.setInt(3,1);//creates consultant with perms level 1
        p.execute();
        p.close();
    }

    public static void createProject(Connection con, String ID, String Name, int Duration) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO tbl_Project VALUES (?,?,?)");
        p.setString(1,ID);
        p.setString(2,Name);
        p.setInt(3,Duration);
        p.execute();
        p.close();
    }
    public static String getAssignedPomodoros(Connection con, String TaskName, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("select AssignedPomodoros from tbl_Tasks where TaskName = ? and ConsultantID = ?;");
        p.setString(1,TaskName);
        p.setString(2,ConsultantID);
        ResultSet rs = p.executeQuery();
        rs.next();
        String theresult = rs.getString(1);
        p.close();
        return theresult;

    }
    public static int getPomodoroTimer(Connection con, String ConsultantID, String TimerType) throws SQLException{
        int theresult = 0;
        if(Objects.equals(TimerType, "Pomodoro")) {
            PreparedStatement p = con.prepareStatement("select PomodoroTime  from tbl_Consultant where ConsultantID = ?");
            p.setString(1,ConsultantID);
            ResultSet rs = p.executeQuery();
            rs.next();
            theresult = rs.getInt(1);
            p.close();

        }else if(Objects.equals(TimerType, "ShortBreak")){
            PreparedStatement p = con.prepareStatement("select PomodoroShortBreakTime  from tbl_Consultant where ConsultantID = ?");
            p.setString(1,ConsultantID);
            ResultSet rs = p.executeQuery();
            rs.next();
            theresult = rs.getInt(1);
            p.close();

        }else if(Objects.equals(TimerType, "LongBreak")){
            PreparedStatement p = con.prepareStatement("select PomodoroLongBreakTime  from tbl_Consultant where ConsultantID = ?");
            p.setString(1,ConsultantID);
            ResultSet rs = p.executeQuery();
            rs.next();
            theresult = rs.getInt(1);
            p.close();
        }
        return theresult;
    }


    //note for future, figure out how to sub-categorize these in a proper manner
    // that manner being separable when running a command i.e.
    // SQLHandler.Get.ProjectID(); for ease of search with more than 10 commands
}
