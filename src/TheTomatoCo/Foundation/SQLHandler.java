package TheTomatoCo.Foundation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHandler {

    //TODO create a stored procedure for this
    public static void createConsultant(Connection con, String consultantName, int pomodoroLength, int breakLength) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO tbl_Consultant values(?,?,?)");
        p.setString(1, consultantName);
        p.setInt(2,pomodoroLength);
        p.setInt(3,breakLength);
        p.execute();
        p.close();
    }

    public static int checkLogin(Connection con, int ConsultantID) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT Password FROM tbl_Login WHERE ConsultantID=?");
        p.setInt(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        return rs.getInt(1);
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
        PreparedStatement p = con.prepareStatement("Select PermissionLevel from tbl_Login where Password = ? and ConsultantID = (select ConsultantID from tbl_Consultant where ConsultantName = ?)");
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
}
