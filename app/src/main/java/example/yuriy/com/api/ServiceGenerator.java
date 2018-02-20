package example.yuriy.com.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ServiceGenerator {

    public static final String DOMAIN = "http://c9ff97c8.ngrok.io";  // quest domain //todo
    public static final String API_V0 = DOMAIN + "/api/v0/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_V0)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        return  retrofit.create(serviceClass);
    }

}
