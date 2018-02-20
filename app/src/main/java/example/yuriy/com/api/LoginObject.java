package example.yuriy.com.api;

/**
 * Created by yuriy on 2/19/2018.
 */

public class LoginObject {


    private String userID;
    private String accessToken;

    public LoginObject(String userID, String accessToken) {
        this.userID = userID;
        this.accessToken = accessToken;
    }

}
