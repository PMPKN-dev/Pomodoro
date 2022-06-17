package TheTomatoCo.Hub.Controller;
/*
* In order to store the logged in users ID and permission level, we decided to create a class utilizing the singleton pattern, making it so theres only a singular instance of this class which contains the userID and lvl.
* This allows us to avoid the issue of creating multiple instances saving different userIDs when logging out and in of other users.
* */
public class LoginData {
    private static LoginData instance = new LoginData();
    private String UserID = "";
    private int PermissionLvl = 0;
    private String Permission = "";

    private LoginData(){

    }

    /*
    * Here we create the getter and setter of the userID so the login class can set the userID when logging in, and other classes can utilize the getter to fetch the userID.
    * */
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
/*
* The same thing happens below with Permission level as with userID, a setter and getter is created to be used by other classes.
* */
    public String getPermissionLvl() {
        if(PermissionLvl==1){
            Permission = "User";
        }else if(PermissionLvl==2){
            Permission = "Admin";
        }
        return Permission;
    }

    public void setPermissionLvl(int permissionLvl) {
        PermissionLvl = permissionLvl;
    }

    public static LoginData getInstance(){
        return instance;
    }
}
