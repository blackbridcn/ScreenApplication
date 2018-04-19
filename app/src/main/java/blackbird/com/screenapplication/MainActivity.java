package blackbird.com.screenapplication;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import android.view.View;

import blackbird.com.screenapplication.service.AppService;
import blackbird.com.screenapplication.utils.ProcessUtils;

public class MainActivity extends Activity implements View.OnClickListener {
    private AppService appService;
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.adb_cmd).setOnClickListener(this);
        this.findViewById(R.id.DevicePolicyManager).setOnClickListener(this);
        mServiceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                AppService.ServiceBind bind = (AppService.ServiceBind) service;
                appService = bind.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                appService = null;
            }
        };
        Intent intent = new Intent();
        intent.setClass(this,AppService.class);
        bindService(intent,mServiceConnection, Service.BIND_AUTO_CREATE);
        String curProcessName = ProcessUtils.getCurProcessName(this);
        Log.e("TAG", "MainActivity:---------------- curProcessName : " + curProcessName);
        int uid = Process.myUid();
        Log.e("TAG", ": --------------------------- MainActivity UID :" + uid);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(appService!=null){
            appService.unbindService(mServiceConnection);
        }
    }
}
