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
             /*   ViewParent viewParent = mContainer.getParent();
                if (viewParent != null) {
                    return;
                }

                mKeyguardLock.disableKeyguard();
                WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
                WindowManager.LayoutParams lp = generateLayoutParams();
                windowManager.addView(mContainer, lp);*/
                break;
            case Intent.ACTION_USER_PRESENT:
                Log.e("TAG", "onReceive: -------------- >>  USER_PRESENT");//解锁
                break;
        }
    }

    private WindowManager.LayoutParams generateLayoutParams(Context mContext) {
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        lp.flags |= WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.x = 0;
        lp.y = 0;
        lp.format = PixelFormat.TRANSLUCENT;
        return lp;
    }
}
