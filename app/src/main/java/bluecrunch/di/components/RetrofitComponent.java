package bluecrunch.di.components;


import bluecrunch.di.modules.RetrofitModule;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by amrahmed on 3/6/19.
 */

@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {

    Retrofit getRetrofit();
}
