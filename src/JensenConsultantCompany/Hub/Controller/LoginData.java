package JensenConsultantCompany.Hub.Controller;

public class LoginData {
    private static LoginData instance = new LoginData();
    private String UserID = "";
    private int PermissionLvl = 0;
    private String Permission = "";

    private LoginData(){

    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

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
