package example.yuriy.com;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import example.yuriy.com.api.ErrorHandler;
import example.yuriy.com.api.LoginResponse;

public class MainActivity extends AppCompatActivity {

    TextView infoTv;
    public static final String TOKEN = "EAALq5VBw9AIBADKIZB9cajW0dtaOYxtgW7Qqu8aRG45uUztb6WsvrRfeTPCiHSOwFxwkzf7GeBqF0eforqTfyaQHl9wE8gNd03bjwgI1KVWHhLg2N4BZCTJlhMr7RoZBNeD9FsiJyhIEm0Ql5N36bhTpkAASuOeBNyFJuC6HUzzI8ZCbKKhCPbXDzeKPWDEXn9aHOXNusMGYGZCyBapxSnEpCcEfiYKAZD";
    public static final String USER_ID = "1644826165604509";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        infoTv = findViewById(R.id.info);


        subscribeToLiveData();
    }

    public void onLogin(View view) {
        LoginViewModel.getViewModel(MainActivity.this).runSubscription();
    }

    private void subscribeToLiveData() {
        LoginViewModel.getViewModel(MainActivity.this).getLoginResponseLiveData().observe(this, new android.arch.lifecycle.Observer<LoginViewModel.LiveDataLoginContainer>() {
            @Override
            public void onChanged(@Nullable LoginViewModel.LiveDataLoginContainer liveDataLoginContainer) {
                if (liveDataLoginContainer == null) {
                    Log.e("onChanged", "or first start or clearedData");
                    return;
                }
                if (liveDataLoginContainer.isNoErrorResponse()) {
                    LoginResponse resultResp = liveDataLoginContainer.getLoginResponse();
                    String token = resultResp.getAuthToken();
                    boolean isNewUser = resultResp.isNewUser();

                    infoTv.setText(token);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("onChanged", "noError" + "token:" + token + " is new user = " + isNewUser);
                    //save to SharedPref, go to start activity, call finish()
                } else {

                    ErrorHandler.logError(liveDataLoginContainer.getThrowable());
                }
                LoginViewModel.getViewModel(MainActivity.this).clearLifeData();
            }
        });
    }

}
