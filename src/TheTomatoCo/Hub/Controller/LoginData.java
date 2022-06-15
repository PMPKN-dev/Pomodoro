package TheTomatoCo.Hub.Controller;

public class LoginData {
    private static LoginData instance = new LoginData();
    private int UserID = 0;
    private int PermissionLvl = 0;

    private LoginData(){

    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getPermissionLvl() {
        return PermissionLvl;
    }

    public void setPermissionLvl(int permissionLvl) {
        PermissionLvl = permissionLvl;
    }

    public static LoginData getInstance(){
        return instance;
    }
}
