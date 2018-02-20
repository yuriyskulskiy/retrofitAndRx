package example.yuriy.com;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import example.yuriy.com.api.ErrorHandler;
import example.yuriy.com.api.LoginResponse;

public class MainActivity extends AppCompatActivity {


    public static final String TOKEN = "EAACm5v3mKgwBAGxoNtncAAGX9caoi3jOAk5SJQ4zkpROOoc3qonKCNIrZANoZAobKferxwrpKyGjnziw1PZAAzcmLYiISLd93cdCDU69AYtEnru075LGNO9zCceTeTYSUnbHirv1SInPSBalZBkfqs6Vz7uHQcDDg3NSZCY0ZCF3bu3i6nA2sbxL2tZBw7GFN8L3aK8vFVPQvXUNNLr4aLwgMDouAS1R5AZD";
    public static final String USER_ID = "1624473360973123";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            subscribeToLiveData();
    }

    public void onLogin(View view) {
        if(!getViewModel().isSubscribed){
            subscribeToLiveData();
        }
        getViewModel().runSubscription();
    }

    private void subscribeToLiveData() {
        getViewModel().isSubscribed = true;
        getViewModel().getLoginResponseLiveData().observe(this, new android.arch.lifecycle.Observer<LoginViewModel.LiveDataLoginContainer>() {
            @Override
            public void onChanged(@Nullable LoginViewModel.LiveDataLoginContainer liveDataLoginContainer) {
                if (liveDataLoginContainer == null) {
                    Log.e("onChanged", "or first start or clearedData");
                }
                if (liveDataLoginContainer.isNoErrorResponse()) {
                    LoginResponse resultResp = liveDataLoginContainer.getLoginResponse();
                    String token = resultResp.getAuthToken();
                    boolean isNewUser = resultResp.isNewUser();
                    Log.e("LiveData", "noError" + "token:" + token + " is new user = " + isNewUser);
                    //save to SharedPref, go to start activity, call finish()
                } else {

                    ErrorHandler.logError(liveDataLoginContainer.getThrowable());
                }

                clearViewModel();
                getViewModel().isSubscribed = false;
            }
        });
    }

    public LoginViewModel getViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private void clearViewModel() {
        getViewModel().clear();
    }




}
