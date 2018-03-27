package blackbird.com.screenapplication.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by yzzhang on 2018/3/27.
 */

public class AppApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getApplicationContexts() {
        return mContext;
    }
}
