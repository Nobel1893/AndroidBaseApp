package bluecrunch.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bluecrunch.utils.Constants;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by amrahmed on 3/6/19.
 */

@Module
public class RetrofitModule {


    @Provides
    public Gson provideGson() {
        return  new GsonBuilder()
            .setLenient()
            .create();
    }


    @Provides
    public HttpLoggingInterceptor getLoggingInterceptor(){
      return  new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    @Provides
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient ) {
        return   new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build();
    }

}
