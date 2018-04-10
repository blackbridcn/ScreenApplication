package blackbird.com.screenapplication;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
            //window.setBackgroundDrawableResource(R.drawable.ic_launcher_background);

        }
        setContentView(R.layout.activity_adb_screen);
        ButterKnife.bind(this);
       // setContentView(getLayout());
        setTitle(getResources().getString(R.string.app_name));
    }

    @OnClick({R.id.adb_off, R.id.adb_reboot, R.id.adb_volume_, R.id.adb_volume})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
}
