package bluecrunch.di.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bluecrunch.Model.source.remote.WebServices;
import bluecrunch.baseapp.BuildConfig;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hari on 23/11/17.
 */
@Module(includes = OkHttpClientModule.class)
public class RetrofitModule {

    @Provides
    public WebServices servicesApi(Retrofit retrofit) {
        return retrofit.create(WebServices.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


}
