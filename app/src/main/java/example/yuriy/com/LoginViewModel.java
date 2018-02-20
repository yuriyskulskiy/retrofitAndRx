package example.yuriy.com;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import example.yuriy.com.api.LoginObject;
import example.yuriy.com.api.LoginResponse;
import example.yuriy.com.api.QuestService;
import example.yuriy.com.api.ServiceGenerator;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static example.yuriy.com.MainActivity.TOKEN;
import static example.yuriy.com.MainActivity.USER_ID;

/**
 * Created by yuriy on 2/20/2018.
 */

public class LoginViewModel extends ViewModel {

    boolean isSubscribed = true;

    private Observable<LoginResponse> mLoginResponseObservable;
    private Disposable disposable;

    private MutableLiveData<LiveDataLoginContainer> loadedLoginData;

    public LoginViewModel() {
        super();
        QuestService mQuestService = ServiceGenerator.createService(QuestService.class);
        mLoginResponseObservable =
                mQuestService.login((new LoginObject(USER_ID, TOKEN)));
    }

    public LiveData<LiveDataLoginContainer> getLoginResponseLiveData() {
        if (loadedLoginData == null) {
            loadedLoginData = new MutableLiveData<LiveDataLoginContainer>();
        }
        return loadedLoginData;
    }

    public void runSubscription() {
        safeDispose(disposable);

        disposable = mLoginResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse value) {
                        Log.e("RXT", "onNext ");
                        loadedLoginData.setValue(new LiveDataLoginContainer(value, null));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RXT", "onError ");
                        loadedLoginData.setValue(new LiveDataLoginContainer(null, e));
                    }

                    @Override
                    public void onComplete() {
                        //do nothing
                        Log.e("RXT", "onComplete ");
                    }
                });
    }

    public void clear() {
        loadedLoginData = new MutableLiveData<LiveDataLoginContainer>();  //   new empty live data needs new callback to refresh ui
        safeDispose(disposable);
    }

    private void safeDispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static class LiveDataLoginContainer {


        private boolean noErrorResponse = true;
        private LoginResponse loginResponse;
        private Throwable throwable;


        public LiveDataLoginContainer(LoginResponse loginResponse, Throwable throwable) {
            this.loginResponse = loginResponse;
            if (throwable != null) {
                noErrorResponse = false;
                this.throwable = throwable;
            }
        }


        public LoginResponse getLoginResponse() {
            return loginResponse;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public boolean isNoErrorResponse() {
            return noErrorResponse;
        }
    }
}
