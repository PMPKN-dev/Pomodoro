package TheTomatoCo.Foundation;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHandler {

    //TODO create a stored procedure for this
    public static void createConsultant(Connection con, String consultantName, int pomodoroLength, int shortBreakLength, int longBreakLength) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO tbl_Consultant values(?,?,?,?)");
        p.setString(1, consultantName);
        p.setInt(2,pomodoroLength);
        p.setInt(3,shortBreakLength);
        p.setInt(4,longBreakLength);
        p.execute();
        p.close();
    }

    public static String checkLogin(Connection con, String ConsultantID) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT Password FROM tbl_Login WHERE ConsultantID=?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        return rs.getString(1);
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
        //PreparedStatement p = con.prepareStatement("Select PermissionLevel from tbl_Login where Password = ? and ConsultantID = (select ConsultantID from tbl_Consultant where ConsultantName = ?)");
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

    public static void createTask(Connection con, int ConsultantID, int ProjectID, String TaskName, int AssignedPomodoros) throws SQLException {
        PreparedStatement p = con.prepareStatement("Insert into tbl_Tasks values(?,?,?,?)");
        p.setInt(1, ConsultantID);
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
        //p.setString(1,pomodoroLength.getText());
        //p.setString(2,shortBreakLength.getText());
        //p.setString(3,longBreakLength.getText());
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
        return rs.getInt(1);
    }
    public static int setShortBreakTime(Connection con, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroShortBreakTime FROM tbl_Consultant WHERE ConsultantID = ?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        return rs.getInt(1);
    }
    public static int setLongBreakTime(Connection con, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroLongBreakTime FROM tbl_Consultant WHERE ConsultantID = ?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        return rs.getInt(1);
    }

    public static int getPermissionLevel (Connection con, int userName) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT PermissionLevel FROM tbl_Login WHERE ConsultantID=?");
        p.setInt(1,userName);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        return rs.getInt(1);
    }

    public static void deactivateConsultant(Connection con, String ID) throws SQLException {
        PreparedStatement p = con.prepareStatement("UPDATE tbl_StatusOfConsultant SET Status=0 WHERE ConsultantID=?");
        p.setString(1,ID);
        p.execute();
    }
}
