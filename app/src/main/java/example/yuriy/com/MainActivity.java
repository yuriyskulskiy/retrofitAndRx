package example.yuriy.com;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import example.yuriy.com.api.LoginObject;
import example.yuriy.com.api.LoginResponse;
import example.yuriy.com.api.QuestService;
import example.yuriy.com.api.RetrofitHelper;
import example.yuriy.com.api.ServiceGenerator;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    QuestService mQuestService;
    public static final String TOKEN = "EAACm5v3mKgwBAGxoNtncAAGX9caoi3jOAk5SJQ4zkpROOoc3qonKCNIrZANoZAobKferxwrpKyGjnziw1PZAAzcmLYiISLd93cdCDU69AYtEnru075LGNO9zCceTeTYSUnbHirv1SInPSBalZBkfqs6Vz7uHQcDDg3NSZCY0ZCF3bu3i6nA2sbxL2tZBw7GFN8L3aK8vFVPQvXUNNLr4aLwgMDouAS1R5AZD";
    public static final String USER_ID = "1624473360973123";

    @NonNull
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mQuestService = new RetrofitHelper().getQuestService();
        mQuestService = ServiceGenerator.createService(QuestService.class);
    }

    public void onClick(View view) {
//        doLoginRequest();
        doRxLoginRequest();
    }

    private void doRxLoginRequest() {
        mCompositeDisposable.add(mQuestService.login(new LoginObject(USER_ID, TOKEN))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                    @Override
                    public void accept(LoginResponse loginResponse) throws IOException {
                        Log.e("test", "" + loginResponse.getAuthToken());
                        Log.e("test", "" + loginResponse.isNewUser());
                    }


                })

        );

    }

//    private void doLoginRequest() {
//        mQuestService.login(new LoginObject(USER_ID, TOKEN)).enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if (response.isSuccessful()) {
//
//                    response.code();
//                    Log.e("test", "" + response.code());
//                    Log.e("test", "" + response.body().getAuthToken());
//                    Log.e("test", "" + response.body().isNewUser());
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                Log.e("test", "error");
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        // DO NOT CALL .dispose()
        mCompositeDisposable.clear();
        super.onDestroy();
    }
}
