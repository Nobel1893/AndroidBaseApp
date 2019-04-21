package bluecrunch.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import java.lang.ref.WeakReference;

import javax.xml.transform.Result;

import bluecrunch.baseapp.R;
import bluecrunch.utils.SingleLiveEvent;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by root on 06/05/18.
 */

public abstract class BaseViewModel<N> extends ViewModel {

    private WeakReference<N> mNavigator;
    private CompositeDisposable mCompositeDisposable;
    public SingleLiveEvent<BaseMessage> message = new SingleLiveEvent<>();
    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    public BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
        new BaseApiHandler<Result>(){

            @Override
            public void onSuccess(BaseResponse<Result> response) {
                super.onSuccess(response);

            }
        };
    }


    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }


    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }


    public abstract class BaseApiHandler<T> implements SingleObserver<BaseResponse<T>> {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(BaseResponse<T> response) {


            /*
            *   401 Authentication Error
                403 Authorization Error
                404 Not Found like URL or some query and nothing exist
                419 Expired
                422 "any" Validation Error
                200 Done or Success for any operation has been done
                50x where x is a number which means some kind of server error and its message should be handled from Mobile
            */

            switch (response.code){
                case 401:
                case 403:
                case 404:
                case 419:
                case 422:
                case 500: {
                    BaseMessage message=new BaseMessage();
                    message.message=response.error;
                    message.titleId= R.string.error;
                    message.posTextId= R.string.ok;
                    BaseViewModel.this.message.setValue(message);
                    break;
                }
            }
        }

        @Override
        public void onError(Throwable e) {
            if(!isConnectingToInternet()){
                BaseMessage mMessage=new BaseMessage();
                mMessage.messageId=R.string.no_internet_warning;
                mMessage.titleId= R.string.error;
                mMessage.posTextId= R.string.ok;
                message.postValue(mMessage);

            }else{
                BaseMessage mMessage=new BaseMessage();
                mMessage.message=e.getLocalizedMessage();
                mMessage.titleId= R.string.error;
                mMessage.posTextId= R.string.ok;
                message.postValue(mMessage);
            }
        }
    };

    public static boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                BaseApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


/*
    public void apiSample(){
        WebServices webServices;
        webServices.exampleApiCall().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseApiHandler<String>(){
                    @Override
                    public void onSuccess(BaseResponse<String> response) {
                        super.onSuccess(response);
                        //rest of handling
                    }
                });
    }
*/


}
