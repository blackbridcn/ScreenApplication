package blackbird.com.screenapplication;

import android.app.Activity;
import android.app.admin.DeviceAdminInfo;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import blackbird.com.screenapplication.receiver.AdminReciver;
import blackbird.com.screenapplication.receiver.WipeDataAdminReciver;

public class DevicePolicyManagerActivity extends Activity implements View.OnClickListener {

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName adminReceiver, wipeDataAdminReceiver;
    private int requestCode = 1;

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
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
        this.findViewById(R.id.btn_request_lock).setOnClickListener(this);
        this.findViewById(R.id.btn_request_wipedata).setOnClickListener(this);
        this.findViewById(R.id.btn_off_lock_admin).setOnClickListener(this);
        this.findViewById(R.id.btn_off_swipe_admin).setOnClickListener(this);
        this.findViewById(R.id.disable_camera).setOnClickListener(this);
        this.findViewById(R.id.is_disablle_camera).setOnClickListener(this);
        this.findViewById(R.id.off_disable_camera).setOnClickListener(this);

        adminReceiver = new ComponentName(this, AdminReciver.class);
        wipeDataAdminReceiver = new ComponentName(this, WipeDataAdminReciver.class);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);


        getActiveAdmins();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lockscrrem:
                lockNow();
                //"com.tencent.mm"
                isDeviceOwnerApp("blackbird.com.screenapplication");
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

            case R.id.btn_request_lock:
                requestLockAdmins();
                break;

            case R.id.btn_request_wipedata:
                getWipeDataAdminPerssin();
                break;
            case R.id.btn_off_lock_admin:
                removeLockAdmin();
                break;

            case R.id.btn_off_swipe_admin:
                removeWipeAdmid();
                break;
            case R.id.disable_camera:
                voidsetCameraDisable(true);
                break;
            case R.id.is_disablle_camera:
                getCameraDisabled();
                break;
            case R.id.off_disable_camera:
                voidsetCameraDisable(false);
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void requestLockAdmins() {
        boolean active = mDevicePolicyManager.isAdminActive(adminReceiver);
        if (!active) {
            //打开DevicePolicyManager管理器，授权页面
            Intent intent = new Intent();
            //授权页面Action -->  DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            //设置DEVICE_ADMIN，告诉系统申请管理者权限的Component/DeviceAdminReceiver
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminReceiver);
            //设置 提示语--可不添加
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "DevicePolicyManager涉及的管理权限,一次性激活!");
            startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(this, "已经获取的DevicePolicyManager管理器的授权", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void getWipeDataAdminPerssin() {
        boolean active = mDevicePolicyManager.isAdminActive(wipeDataAdminReceiver);
        if (!active) {
            //打开DevicePolicyManager管理器，授权页面
            Intent intent = new Intent();
            //Action -->  DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            //设置DEVICE_ADMIN，告诉系统申请管理者权限的Component/DeviceAdminReceiver
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, wipeDataAdminReceiver);
            //设置 提示语--可不添加
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "DevicePolicyManager 申请恢复出厂设置的管理权限!");
            startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(this, "已经获取的DevicePolicyManager恢复出厂设置 管理器的授权", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 查看某个packageName是否已经授权
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void isDeviceOwnerApp(String packageName) {
        boolean deviceOwnerApp = mDevicePolicyManager.isDeviceOwnerApp(packageName);
        Log.e("TAG", "isDeviceOwnerApp: " + deviceOwnerApp);
    }

    int REQUEST_CAMERA_1 = 1;

    /**
     * \
     * 打开相机
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    private void getCameraDisabled() {
        /*boolean cameraDisabled = mDevicePolicyManager.getCameraDisabled(adminReceiver);
        if (cameraDisabled) {
            Toast.makeText(this, "相机已经被禁用了", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "未禁用相机", Toast.LENGTH_LONG).show();
        }*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, REQUEST_CAMERA_1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1 == requestCode) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA_1) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
                // Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                // Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                // ivShowPicture.setImageBitmap(bitmap);// 显示图片
            } /*else if (requestCode == REQUEST_CAMERA_2) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(mFilePath); // 根据路径获取数据
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    ivShowPicture.setImageBitmap(bitmap);// 显示图片
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();// 关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }*/
        }
    }

    private void hasGrantedPolicy(ComponentName admin, int usesPolicy) {
        mDevicePolicyManager.hasGrantedPolicy(admin, usesPolicy);//是否拥有某项设备管理权限

    }

    private void getActiveAdmins() {
        List<ComponentName> mComponentName = mDevicePolicyManager.getActiveAdmins();
        Log.e("TAg", "getActiveAdmins :-------------- " + mComponentName);
    }

    private void voidsetCameraDisable(boolean disaleCamera) {
        mDevicePolicyManager.setCameraDisabled(wipeDataAdminReceiver, disaleCamera);
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
            /**********************************************************************/
            mDevicePolicyManager.resetPassword("9527", DeviceAdminInfo.USES_POLICY_RESET_PASSWORD);
            Toast.makeText(this, "若发生改变，则将触发DeviceAdminReceiver.onPasswordChanged", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void swipPassword() {
        if (checkAdmin()) {
            /*************************************************************************/
            mDevicePolicyManager.resetPassword("123456", DeviceAdminInfo.USES_POLICY_RESET_PASSWORD);
            Toast.makeText(this, "若发生改变，则将触发DeviceAdminReceiver.onPasswordChanged", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void swipData() {
        if (checkAdmin()) {
            mDevicePolicyManager.wipeData(0);
            Toast.makeText(this, "恢复出厂设置", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeWipeAdmid() {
        if (mDevicePolicyManager.isAdminActive(wipeDataAdminReceiver)) {
            mDevicePolicyManager.removeActiveAdmin(wipeDataAdminReceiver);
            Toast.makeText(this, "收回设备管理器权限", Toast.LENGTH_SHORT).show();
        } else {
            //  getWipeDataAdminPerssin();
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }
    }


    private void removeLockAdmin() {
        if (mDevicePolicyManager.isAdminActive(adminReceiver)) {
            mDevicePolicyManager.removeActiveAdmin(adminReceiver);
            Toast.makeText(this, "收回设备管理器权限", Toast.LENGTH_SHORT).show();
        } else {
            //  getWipeDataAdminPerssin();
            Toast.makeText(this, "未授权给用户系统管理权限", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 查看是否已经获得管理者的权限
     *
     * @return resualt
     */
    private boolean checkAdmin() {
        return mDevicePolicyManager.isAdminActive(adminReceiver);
    }

}
