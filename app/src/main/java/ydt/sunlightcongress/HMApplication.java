package ydt.sunlightcongress;

import android.app.Application;

import ydt.sunlightcongress.data.DataSource;

/**
 * Created by Caodongyao on 2016/11/26.
 */

public class HMApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        DataSource.init(this);
    }

    @Override
    public void onTerminate() {
        DataSource.destroy();
        super.onTerminate();
    }
}
