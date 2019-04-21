package bluecrunch.Model.source.remote;

import bluecrunch.base.BaseResponse;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by karim on 8/10/18.
 */

public interface WebServices {


    Retrofit retrofit = DaggerRetrofitComponent.create().getRetrofit();


    @GET("users")
    Single<BaseResponse<String>> exampleApiCall();
}