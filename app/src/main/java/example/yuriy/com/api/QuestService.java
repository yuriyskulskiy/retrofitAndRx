package example.yuriy.com.api;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by yuriy on 2/19/2018.
 */

public interface QuestService {


//    @POST("login")
//    Call<LoginResponse> login(@Body LoginObject loginObject);

    @POST("login")
    Observable<LoginResponse> login(@Body LoginObject loginObject);

}
