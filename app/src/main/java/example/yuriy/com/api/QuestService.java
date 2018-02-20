package example.yuriy.com.api;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yuriy on 2/19/2018.
 */

public interface QuestService {
    public static final String DOMAIN = "http://c9ff97c8.ngrok.io";
    public static final String API_V0 = DOMAIN + "/api/v0/";

//    @POST("login")
//    Call<LoginResponse> login(@Body LoginObject loginObject);

    @POST("login")
    Single<LoginResponse> login(@Body LoginObject loginObject);

}
