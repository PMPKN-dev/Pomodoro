package TheTomatoCo.Foundation;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    //TODO: link with login
    public static void updateConsultant(Connection con, TextField pomodoroLength, TextField shortBreakLength, TextField longBreakLength) throws SQLException{
        //ConsultantID as 1 is to test it actually works
        //Ideally it should somehow grab the ID from the login
        PreparedStatement p = con.prepareStatement("UPDATE tbl_Consultant SET PomodoroTime = ?, PomodoroShortBreakTime = ?, PomodoroLongBreakTime = ? WHERE ConsultantID = 1");
        p.setString(1,pomodoroLength.getText());
        p.setString(2,shortBreakLength.getText());
        p.setString(3,longBreakLength.getText());
        p.executeUpdate();
        p.close();
    }

    //TODO: link with login
    //Sets the timer for the chosen consultant, needs to link with login, placeholder ID currently
    public static void setConsultantTime(Connection con, int pomodoroLength, int shortBreakLength, int longBreakLength) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroTime = ?, PomodoroShortBreakTime = ?, PomodoroLongBreakTime = ? FROM tbl_Consultant WHERE ConsultantID = 1");
        p.setString(1, String.valueOf(pomodoroLength));
        p.setString(2, String.valueOf(shortBreakLength));
        p.setString(3, String.valueOf(longBreakLength));
        p.execute();
        p.close();
        /*
        ResultSet rs = p.getResultSet();
        rs.next();
        return rs.getInt(1);

         */


    }
}
