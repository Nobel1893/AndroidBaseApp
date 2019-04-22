package bluecrunch.di.modules;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hari on 23/11/17.
 */
@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Named("application_context")
    @Provides
    public Context context() {
        return context.getApplicationContext();
    }
}
