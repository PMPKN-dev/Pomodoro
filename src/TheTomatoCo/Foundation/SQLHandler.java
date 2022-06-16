package TheTomatoCo.Foundation;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;

public class SQLHandler {

    /**
     * Creates a new Consultant within the tbl_Consultant in Database with an ID, their real name, default times
     * "INSERT INTO tbl_Consultant VALUES (ID,consultantName,25,5,10)
     * @param con A connection to the database
     * @param ID Their name followed by a number, ex: LarsHein03
     * @param consultantName Their real name, ex: Lars Hein
     * @param pomodoroLength Default pomodoro time: 25min
     * @param shortBreakLength Default short break time: 5min
     * @param longBreakLength Default long break time: 10min
     * @throws SQLException SQLException
     */
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

    /**
     * Uses query to check if the login matches an existing login
     * "SELECT Password FROM tbl_Login WHERE ConsultantID = chosen_ConsultantID
     * @param con A connection to database
     * @param ConsultantID Their name followed by a number, ex: LarsHein03
     * @return theresult
     * @throws SQLException SQLException
     */
    public static String checkLogin(Connection con, String ConsultantID) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT Password FROM tbl_Login WHERE ConsultantID=?");
        p.setString(1,ConsultantID);
        ResultSet rs = p.executeQuery();
        rs.next();
        String theresult = rs.getString(1);
        p.close();
        return theresult;
    }

    /**
     * Grabs the ID based on a Consultants real name
     * "SELECT ConsultantID FROM tbl_Consultant WHERE ConsultantName = Name
     * @param con A connection to database
     * @param Username ConsultantName, ex: Lars Hein
     * @return ID, ex: LarsHein01
     * @throws SQLException SQLException
     */
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

    /**
     * Links the label that defines who is currently logged in with the login data from database, aswell as their permission lvl
     * Permission Levels: Consultant or Admin
     * "SELECT PermissionLevel FROM tbl_Login WHERE Password = password AND ConsultantID = ID"
     * @param con A connection to database
     * @param Password The unique password each login has
     * @param Username The ConsultantID, ex: LarsHein01
     * @return Returns PermissionLevel, ex: Admin
     * @throws SQLException SQLException
     */
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

    /**
     * Usable for OfficeOverview or general knowledge for admin, regarding amount of projects in play
     * "SELECT max(ProjectID) FROM tbl_Project"
     * @param con A connection to database
     * @return Returns the amount of projects
     * @throws SQLException SQLException
     */
    public static int AmountOfProjects(Connection con) throws SQLException {
        PreparedStatement p = con.prepareStatement("Select max(ProjectID) from tbl_Project");
        ResultSet rs = p.executeQuery();
        rs.next();
        int value = rs.getInt(1);
        p.close();
        return value;
    }

    /**
     * Simple query to select the name of a project
     * "SELECT ProjectName FROM tbl_Project
     * @param con A connection to database
     * @return Returns ProjectName
     * @throws SQLException SQLException
     */
    public static String ProjectName(Connection con) throws SQLException{
        PreparedStatement p = con.prepareStatement("Select ProjectName from tbl_Project");
        ResultSet rs = p.executeQuery();
        rs.next();
        String Pname = rs.getString(1);
        p.close();
        return Pname;
    }

    /**
     * Simple query to get the unique Project ID based on the Project Name
     * "SELECT ProjectID FROM tbl_Project WHERE ProjectName = Name
     * @param con A connection to database
     * @param ProjectName Name of a project, ex: Launch EVO
     * @return Returns ID
     * @throws SQLException SQLException
     */
    public static int getProjectID(Connection con, String ProjectName) throws SQLException{
        PreparedStatement p = con.prepareStatement("Select ProjectID from tbl_Project where ProjectName = ?");
        p.setString(1,ProjectName);
        ResultSet rs = p.executeQuery();
        rs.next();
        int ID = rs.getInt(1);
        p.close();
        return ID;
    }

    /**
     * Creating a new Task for the DoToday List
     * "INSERT INTO tbl_Tasks VALUES(ConsultantID,ProjectID, TaskName, AssignedPomodoros)
     * @param con A connection to database
     * @param ConsultantID The unique ConsultantID, ex: LarsHein01
     * @param ProjectID The unique ProjectID, ex: 1
     * @param TaskName The name the consultant decides this task shold be named
     * @param AssignedPomodoros The expected amount of minutes this task will take
     * @throws SQLException SQLException
     */
    public static void createTask(Connection con, String ConsultantID, int ProjectID, String TaskName, int AssignedPomodoros) throws SQLException {
        PreparedStatement p = con.prepareStatement("Insert into tbl_Tasks values(?,?,?,?,0)");
        p.setString(1, ConsultantID);
        p.setInt(2,ProjectID);
        p.setString(3,TaskName);
        p.setInt(4,AssignedPomodoros);
        p.executeUpdate();
        p.close();

    }

    /**
     * Used in Settings of Pomodoro, updates the database with the new custom times the consultant wishes to use
     * "UPDATE tbl_Consultant SET(PomodoroTime = new time, PomodoroShortBreakTime = new time, PomodoroLongBreakTime = new time)"
     * @param con A connection to database
     * @param pomodoroLength New length of consultants Pomodoro time, ex: 40min
     * @param shortBreakLength New length of consultants short break, ex: 10min
     * @param longBreakLength New length of consultants long break, ex: 20min
     * @param ConsultantID The unique ID of consultant, ex: LarsHein01
     * @throws SQLException SQLException
     */
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

    /**
     * The Admins updating of the Consultant, can change name, times, password and permission level
     * "UPDATE tbl_Consultant SET(ConsultantName = Name, PomodoroTime = time, PomodoroShortBreakTime = time, PomodoroLongBreakTime = time) WHERE ConsultantID = ID"
     * "UPDATE tbl_Login SET(Password = password, PermissionLevel = perms) WHERE ConsultantID = ID"
     * @param con A connection to database
     * @param ID The unique ConsultantID, ex: MichalJanczak01
     * @param Name The name of the Consultant, ex Michal Janczak
     * @param pomodoro New time for consultant, ex: 26min
     * @param shortBreak New short break for consultant, ex: 2min
     * @param longBreak New long break for consultant, ex: 20min
     * @param password New password for consultant, ex: M6pu8N
     * @param permissionLevel New Permission Level for consultant, ex: Going from Consultant to Admin
     * @throws SQLException SQLException
     */
    public static void updateConsultant(Connection con, String ID,String Name, int pomodoro, int shortBreak, int longBreak, String password, int permissionLevel) throws SQLException {
        PreparedStatement consultantEdit = con.prepareStatement(
                "UPDATE tbl_Consultant " +
                "SET ConsultantName=?, PomodoroTime=?, PomodoroShortBreakTime=?, PomodoroLongBreakTime=? " +
                "WHERE ConsultantID=?");
        consultantEdit.setString(1,Name);
        consultantEdit.setInt(2,pomodoro);
        consultantEdit.setInt(3,shortBreak);
        consultantEdit.setInt(4,longBreak);
        consultantEdit.setString(5,ID);
        consultantEdit.executeUpdate();
        consultantEdit.close();



        PreparedStatement loginEdit = con.prepareStatement(
                "UPDATE tbl_Login SET Password=?, PermissionLevel=? WHERE ConsultantID=?");
        loginEdit.setString(1,password);
        loginEdit.setInt(2,permissionLevel);
        loginEdit.setString(3,ID);
        loginEdit.executeUpdate();
        loginEdit.close();
    }

    /**
     * Simple query to select the current Pomodoro time for a consultant
     * "SELECT PomodoroTime FROM tbl_Consultant WHERE ConsultantID = ID"
     * @param con A connection to database
     * @param ConsultantID The unique ID of consultant, ex: CarlSjøberg01
     * @return The current pomodoro time
     * @throws SQLException SQLException
     */
    public static int setPomodoroTime(Connection con, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroTime FROM tbl_Consultant WHERE ConsultantID = ?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Simple query to select the current short break time for a consultant
     * "SELECT PomodoroShortBreakTime FROM tbl_Consultant WHERE ConsultantID = ID"
     * @param con A connection to database
     * @param ConsultantID Target consultant ID
     * @return The current short break time
     * @throws SQLException SQLException
     */
    public static int setShortBreakTime(Connection con, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroShortBreakTime FROM tbl_Consultant WHERE ConsultantID = ?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        p.close();
        return rs.getInt(1);
    }

    /**
     * Simple query to select the current long break time for a consultant
     * "SELECT PomodoroLongBreakTime FROM tbl_Consultant WHERE ConsultantID = ID"
     * @param con A connection to database
     * @param ConsultantID Target consultant ID
     * @return The current long break time
     * @throws SQLException SQLException
     */
    public static int setLongBreakTime(Connection con, String ConsultantID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT PomodoroLongBreakTime FROM tbl_Consultant WHERE ConsultantID = ?");
        p.setString(1,ConsultantID);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        p.close();
        return rs.getInt(1);
    }

    /**
     * Simple query to select the PermissionLevel for a consultant
     * "SELECT PermissionLevel FROM tbl_Login WHERE ConsultantID = ID"
     * @param con A connection to database
     * @param userName Target consultant ID
     * @return returns 1 or 2, 1 being Consultant, 2 being Admin
     * @throws SQLException SQLException
     */
    public static int getPermissionLevel (Connection con, int userName) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT PermissionLevel FROM tbl_Login WHERE ConsultantID=?");
        p.setInt(1,userName);
        p.execute();
        ResultSet rs = p.getResultSet();
        rs.next();
        p.close();
        return rs.getInt(1);
    }

    /**
     * Deactivate a consultant
     * "UPDATE tbl_StatusOfConsultant SET Status = 0 WHERE ConsultantID = ID"
     * @param con A connection to database
     * @param ID Target consultant ID
     * @throws SQLException SQLException
     */
    public static void deactivateConsultant(Connection con, String ID) throws SQLException {
        PreparedStatement p = con.prepareStatement("UPDATE tbl_StatusOfConsultant SET Status=0 WHERE ConsultantID=?");
        p.setString(1,ID);
        p.execute();
        p.close();
    }

    /**
     * Checks if a User exists within the system
     * "SELECT ConsultantID FROM tbl_Consultant WHERE ConsultantID = ID"
     * @param ID Target consultant ID
     * @return boolean true if Username exists, boolean false if Username does not exist
     * @throws SQLException SQLException
     */
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

    /**
     * Creating new login for a consultant with a default password and default permission level
     * "INSERT INTO tbl_Login VALUES(ConsultantID, 0000, 1"
     * @param con A connection to database
     * @param ID Target consultant ID
     * @throws SQLException SQLException
     */
    public static void createConsultantLogin(Connection con, String ID) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO tbl_Login VALUES (?,?,?)");
        p.setString(1, ID);
        p.setString(2,"0000");//default password
        p.setInt(3,1);//creates consultant with perms level 1
        p.execute();
        p.close();
    }

    /**
     * Creates a new project for the company, where consultants can be assigned to later
     * "INSERT INTO tbl_Project VALUES(ID,Name,Duration)"
     * @param con A connection to database
     * @param ID Target consultant ID
     * @param Name Name of project
     * @param Duration Amount of minutes project is expected to take
     * @throws SQLException SQLException
     */
    public static void createProject(Connection con, String ID, String Name, int Duration) throws SQLException {
        PreparedStatement p = con.prepareStatement("INSERT INTO tbl_Project VALUES (?,?,?)");
        p.setString(1,ID);
        p.setString(2,Name);
        p.setInt(3,Duration);
        p.execute();
        p.close();
    }

    /**
     * Returns a String Array with all the data about the Consultant<br>
     * Outputs data in following indexing: <br>
     * 0=ID <br> 1=Name <br> 2=Pomodoro <br> 3=ShortBreak <br> 4=LongBreak
     * @param con connection to DB
     * @param ID Target consultant ID
     * @return String[] of all consultant data
     * @throws SQLException SQLException
     */
    public static String[] grabConsultantData(Connection con, String ID) throws SQLException {
        PreparedStatement p = con.prepareStatement("SELECT * FROM tbl_Consultant WHERE ConsultantID=?");
        p.setString(1,ID);
        p.executeQuery();
        ResultSet rs = p.getResultSet();
        rs.next();
        String[] output = new String[5];
        output[0] = rs.getString(1); //ID
        output[1] = rs.getString(2); //Name
        output[2] = String.valueOf(rs.getInt(3)); //Pomodoro time
        output[3] = String.valueOf(rs.getInt(4)); //Short Break time
        output[4] = String.valueOf(rs.getInt(5)); //Long Break time
        rs.close();
        p.close();
        return output;
    }

    /**
     * Simple query to grab all the data within tbl_Login
     * "SELECT * FROM tbl_Login WHERE ConsultantID = ID"
     * @param con A connection to database
     * @param ID Target consultant ID
     * @return Login Data
     * @throws SQLException SQLException
     */
    public static String[] grabLoginData(Connection con, String ID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT * FROM tbl_Login WHERE ConsultantID=?");
        p.setString(1,ID);
        p.executeQuery();
        ResultSet rs = p.getResultSet();
        rs.next();
        String[] output = new String[3];
        output[0] = rs.getString(1); //ID
        output[1] = rs.getString(2); //Password
        output[2] = rs.getString(3); //PermLevel
        rs.close();
        p.close();
        return output;
    }

    /**
     * Simple query to grab all the data within tbl_Project
     * "SELECT * FROM tbl_Project WHERE ProjectID = ID"
     * @param con A connection to database
     * @param ID Target consultant ID
     * @return Project Data
     * @throws SQLException SQLException
     */
    public static String[] grabProjectData(Connection con, String ID) throws SQLException{
        PreparedStatement p = con.prepareStatement("SELECT * FROM tbl_Project WHERE ProjectID = ?");
        p.setString(1,ID);
        p.executeQuery();
        ResultSet rs = p.getResultSet();
        rs.next();
        String[] output= new String[3];
        output[0] = rs.getString(1); //ID
        output[1] = rs.getString(2); //Name
        output[2] = String.valueOf(rs.getInt(3)); //Duration
        rs.close();
        p.close();
        return output;
    }

    /**
     * Updates a project with new values for its name and its duration
     * "UPDATE tbl_Project SET(ProjectName=Name,ProjectDuration=Duration) WHERE ProjectID = ID"
     * @param con A connection to database
     * @param ID Target consultant ID
     * @param Name New Name of project
     * @param Duration New Duration of project
     * @throws SQLException SQLException
     */
    public static void updateProject(Connection con, String ID, String Name, int Duration) throws SQLException {
        PreparedStatement p = con.prepareStatement("UPDATE tbl_Project SET ProjectName=?, ProjectDuration=? WHERE ProjectID=?");
        p.setString(1,Name);
        p.setInt(2,Duration);
        p.setString(3,ID);
        p.executeUpdate();
        p.close();
    }

    //note for future, figure out how to sub-categorize these in a proper manner
    // that manner being separable when running a command i.e.
    // SQLHandler.Get.ProjectID(); for ease of search with more than 10 commands
}
