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

    public static int getPermissionLevel (Connection con, int userName) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT PermissionLevel FROM tbl_Login WHERE ConsultantID=?");
        p.setInt(1,userName);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        return rs.getInt(1);
    }
}
