package example.yuriy.com.api;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;


/**
 * Created by yuriy on 2/20/2018.
 */

public class ErrorHandler {

    public static void logError(Throwable e) {

        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();

            String errorMessage = (getErrorMessage(responseBody));
            Log.e("Error",errorMessage);

        } else if (e instanceof SocketTimeoutException) {
            String timeOutStr = "socket time out exception";
            Log.e("Error",timeOutStr);
        } else if (e instanceof IOException) {
            String probWithInternet = "IO exception";
            Log.e("Error",probWithInternet);

        } else {
            String unknownError = (e.getMessage());
            Log.e("Error",unknownError);
        }
    }

    private static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
