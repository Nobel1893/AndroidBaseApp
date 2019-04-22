package bluecrunch.di.components;


import bluecrunch.Model.source.remote.WebServices;
import bluecrunch.di.modules.RetrofitModule;
import dagger.Component;


/**
 * Created by Hari on 23/11/17.
 */

@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {

    WebServices getWebServices();
}
