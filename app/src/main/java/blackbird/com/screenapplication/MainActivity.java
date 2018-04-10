package blackbird.com.screenapplication;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import blackbird.com.screenapplication.application.AppApplication;
import blackbird.com.screenapplication.receiver.AdminReciver;
import blackbird.com.screenapplication.service.AppService;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.adb_cmd).setOnClickListener(this);
        this.findViewById(R.id.DevicePolicyManager).setOnClickListener(this);
        Intent intent = new Intent();
        intent.setClass(this, AppService.class);
        startService(intent);

        //openScreenOn();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.adb_cmd) {
            Intent intent = new Intent(this, AdbScreenActivity.class);
            startActivity(intent);

        } else if (id == R.id.DevicePolicyManager) {
            Intent intent = new Intent(this, DevicePolicyManagerActivity.class);
            startActivity(intent);
        }

    }

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    public void openScreenOn() {
        if (powerManager == null) {
            powerManager = (PowerManager) AppApplication.getApplicationContexts().getSystemService(Context.POWER_SERVICE);
        }
        if (wakeLock == null) {
            wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");
        }
        boolean ifOpen = powerManager.isScreenOn();
        if (!ifOpen) {
            //屏幕会持续点亮
            wakeLock.acquire();
            //释放锁，屏幕熄灭。
            wakeLock.release();
        }
    }


    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    KeyguardManager.KeyguardLock kl;

    /**
     * 唤醒 解锁
     */
    private void wakeAndUnlock(boolean b) {
        if (b) {
            // 获取电源管理器对象
            pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            // 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
            wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                    | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            // 点亮屏幕
            wl.acquire();
            // 得到键盘锁管理器对象
            km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            kl = km.newKeyguardLock("unLock");
            // 解锁
            kl.disableKeyguard();
        } else {
            // 锁屏
            kl.reenableKeyguard();
            // 释放wakeLock，关灯
            wl.release();
            // failCount++;
        }

    }


    /**
     * 唤醒手机屏幕并解锁
     * <p>
     * 注意：一旦一个应用开启了超级管理员权限,是不能直接在  设置--->应用程序里  进行删除的(删除失败)
     * <p>
     * 必须要在  设置--->位置和安全--->选择设备管理器  里取消要删除应用的激活  然后再去应用程序里删除
     * <p>
     * <p>
     * <uses-permission android:name="android.permission.WAKE_LOCK" />
     * <uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
     */
    public static void wakeUpAndUnlock() {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) AppApplication.getApplicationContexts()
                .getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) AppApplication.getApplicationContexts()
                .getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (1 == requestCode) {;
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
