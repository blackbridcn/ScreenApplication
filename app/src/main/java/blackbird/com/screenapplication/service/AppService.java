package blackbird.com.screenapplication.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import blackbird.com.screenapplication.receiver.ScreenReciver;

public class AppService extends Service {
    public AppService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("AppService", "onCreate: -------------- >>" );
        RegisterScreenReciver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("AppService", "onStartCommand: -------------- >>" );
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.e("AppService", "onDestroy: -------------- >>" );
        super.onDestroy();
    }

    private void RegisterScreenReciver() {
        IntentFilter screenStatus = new IntentFilter();
        screenStatus.addAction(Intent.ACTION_SCREEN_ON);
        screenStatus.addAction(Intent.ACTION_SCREEN_OFF);
        screenStatus.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(new ScreenReciver(), screenStatus);
    }
}
