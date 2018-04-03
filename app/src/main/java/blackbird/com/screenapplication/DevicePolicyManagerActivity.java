package blackbird.com.screenapplication;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import blackbird.com.screenapplication.receiver.AdminReciver;

public class DevicePolicyManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName adminReceiver;
    private int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_policy_manager);
        this.findViewById(R.id.btn_lockscrrem).setOnClickListener(this);
        this.findViewById(R.id.btn_set_pasword).setOnClickListener(this);
        this.findViewById(R.id.btn_swipe_pasword).setOnClickListener(this);
        this.findViewById(R.id.btn_set_time).setOnClickListener(this);
        this.findViewById(R.id.btn_wipe_data).setOnClickListener(this);
        this.findViewById(R.id.btn_wisp_lock_time).setOnClickListener(this);
        this.findViewById(R.id.btn_swipe_admin).setOnClickListener(this);
        adminReceiver = new ComponentName(this, AdminReciver.class);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
          // new DeviceAdminInfo();
            //Lollipop DevicePolicyManager学习（上）
            //https://blog.csdn.net/guiyu_1985/article/details/42778655


            //Android屏幕锁定详解（一）
        //http://blog.51cto.com/mzh3344258/748998
        lockScreen();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lockscrrem:
                lockNow();
                break;
            case R.id.btn_set_pasword:
                resetPassword();
                break;
            case R.id.btn_swipe_pasword:
                swipPassword();
                break;
            case R.id.btn_set_time:
                setMaximumTimeToLock();
                break;
            case R.id.btn_wisp_lock_time:
                swipTimeToLock();
                break;
            case R.id.btn_wipe_data:
                swipData();
                break;

            case R.id.btn_swipe_admin:
                swipAdmid();
                break;

        }
    }

    private void setMaximumTimeToLock() {
        if (checkAdmin()) {
            mDevicePolicyManager.setMaximumTimeToLock(adminReceiver, 3000);
            Toast.makeText(this, "锁屏设置将不可用", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void swipTimeToLock() {
        if (checkAdmin()) {
            mDevicePolicyManager.setMaximumTimeToLock(adminReceiver, Long.MAX_VALUE);
            Toast.makeText(this, "锁屏设置将不可用", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void lockNow() {
        if (checkAdmin()) {
            mDevicePolicyManager.lockNow();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword() {
        if (checkAdmin()) {
            mDevicePolicyManager.resetPassword("9527", 0);
            Toast.makeText(this, "若发生改变，则将触发DeviceAdminReceiver.onPasswordChanged", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void swipPassword() {
        if (checkAdmin()) {
            mDevicePolicyManager.resetPassword("", 0);
            Toast.makeText(this, "若发生改变，则将触发DeviceAdminReceiver.onPasswordChanged", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }
    private void swipData(){
        if (checkAdmin()) {
            mDevicePolicyManager.wipeData( 0);
            Toast.makeText(this, "恢复出厂设置", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void swipAdmid() {
        if (checkAdmin()) {
            mDevicePolicyManager.removeActiveAdmin(adminReceiver);
            Toast.makeText(this, "收回设备管理器权限", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void lockScreen() {
        if (!checkAdmin()) {
            //打开DevicePolicyManager管理器，授权页面
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminReceiver);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "DevicePolicyManager管理权限,一次性激活!");
            startActivityForResult(intent, requestCode);
        } /*else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }*/
    }

    /**
     * 查看是否已经获得管理者的权限
     * @return resualt
     */
    private boolean checkAdmin() {
        return mDevicePolicyManager.isAdminActive(adminReceiver);
    }
}
