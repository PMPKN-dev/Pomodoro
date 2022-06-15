package TheTomatoCo.Hub.Controller;

public class LoginData {
    private static LoginData instance = new LoginData();
    private String UserID = "";
    private int PermissionLvl = 0;

    private LoginData(){

    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
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
