package TheTomatoCo.Hub.Controller;

public class LoginData {
    private static LoginData instance = new LoginData();
    private int UserID = 0;

    private LoginData(){

    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public static LoginData getInstance(){
        return instance;
    }
}
