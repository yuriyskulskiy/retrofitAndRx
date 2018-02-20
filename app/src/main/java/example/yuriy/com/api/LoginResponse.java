package example.yuriy.com.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Callback;

/**
 * Created by yuriy on 2/19/2018.
 */

public class LoginResponse  {

    public LoginResponse() {
    }

    @SerializedName("auth_token")
    @Expose
    private String authToken;

    @SerializedName("new_user")
    @Expose
    private boolean newUser;

    public String getAuthToken() {
        return authToken;
    }

    public boolean isNewUser() {
        return newUser;
    }
}
