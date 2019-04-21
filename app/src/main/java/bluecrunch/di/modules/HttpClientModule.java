package bluecrunch.di.modules;

import android.app.Application;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Mohamed Nabil Mohamed on 4/18/2019.
 * m.nabil.fci2015@gmail.com
 */
@Module
public class HttpClientModule  {

    public static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    @Provides
    public OkHttpClient provideHttpClient (HttpLoggingInterceptor interceptor) {
        return   new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
//                .cache(provideCache())
                //.authenticator(getAuthInterceptor())
                .build();
    }

    @Provides
    public Cache provideCache(Application application){
        File cacheDir = new File(application.getCacheDir(), "cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        return cache;
    }
    @Provides
    public AuthenticationInterceptor getAuthInterceptor(){
        return new AuthenticationInterceptor();
    }
    public  class AuthenticationInterceptor implements Authenticator {

         String AUTHORIZATION ="Authorization";

        @Nullable
        @Override
        public Request authenticate(Route route, Response response) throws IOException {

            //Timber.e("connection error "+response.toString());
            // Timber.e("Auth refuesed");
            /*

            Todo:handle get new token
            retrofit2.Response<LoginResponse> res= getApiService().
                    Login(new LoginBody(TokenProvider.getIntstance().getUsername(),
                            TokenProvider.getIntstance().getPassword()))
                    .execute();
            */

            return response.request().newBuilder()
                    //todo: set value of new Token
                    .header(AUTHORIZATION, "")
                    .build();
        }
    }
}
