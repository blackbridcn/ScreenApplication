package blackbird.com.screenapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import blackbird.com.screenapplication.application.AppApplication;
import blackbird.com.screenapplication.utils.AndroidRootUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdbScreenActivity extends Activity {

    @BindView(R.id.adb_off)
    Button adbOff;
    @BindView(R.id.adb_reboot)
    Button adbReboot;
    @BindView(R.id.adb_volume_)
    Button adbVolume_;
    @BindView(R.id.adb_volume)
    Button adbVolume;
    @BindView(R.id.adb_on)
    Button adbOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adb_screen);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.adb_off, R.id.adb_reboot, R.id.adb_volume_, R.id.adb_volume, R.id.adb_on})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.adb_on:
                openScreenOn();
                break;

            case R.id.adb_off:
                boolean root = AndroidRootUtils.checkDeviceRoot();
                if (root) {
                    AndroidRootUtils.execRootCmd("input keyevent 26");
                } else {
                    Toast.makeText(this, "您的手机还未获取root权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.adb_reboot:
                if (AndroidRootUtils.checkDeviceRoot()) {
                    AndroidRootUtils.execRootCmd("reboot");
                } else {
                    Toast.makeText(this, "您的手机还未获取root权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.adb_volume_:
                if (AndroidRootUtils.checkDeviceRoot()) {
                    AndroidRootUtils.execRootCmd("input keyevent 24");
                } else {
                    Toast.makeText(this, "您的手机还未获取root权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.adb_volume:
                if (AndroidRootUtils.checkDeviceRoot()) {
                    AndroidRootUtils.execRootCmd("input keyevent 25");
                } else {
                    Toast.makeText(this, "您的手机还未获取root权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;

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
            //释放锁，以便2分钟后熄屏。
            wakeLock.release();
        }
    }

}
