package blackbird.com.screenapplication.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import blackbird.com.screenapplication.service.AppService;
import blackbird.com.screenapplication.utils.ProcessUtils;

/**
 * Created by yzzhang on 2018/3/27.
 */

public class AppApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Intent intent = new Intent();
        intent.setClass(this, AppService.class);
        startService(intent);
        String curProcessName = ProcessUtils.getCurProcessName(this);
        Log.e("TAG", "AppApplication:---------------- curProcessName : " + curProcessName);


    }

    public static Context getApplicationContexts() {
        return mContext;
    }
}
