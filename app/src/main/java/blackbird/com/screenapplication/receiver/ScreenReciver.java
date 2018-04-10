package blackbird.com.screenapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by yzzhang on 2018/3/27.
 */

public class ScreenReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context mContext, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_ON:
                Log.e("TAG", "onReceive: -------------- >>  SCREEN_ON");
                break;
            case Intent.ACTION_SCREEN_OFF:
                Log.e("TAG", "onReceive: -------------- >>  SCREEN_OFF");
                break;
            case Intent.ACTION_USER_PRESENT:
                Log.e("TAG", "onReceive: -------------- >>  USER_PRESENT");//解锁
                break;
        }
    }
}
