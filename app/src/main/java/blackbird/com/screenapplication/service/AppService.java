package blackbird.com.screenapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import blackbird.com.screenapplication.MainActivity;
import blackbird.com.screenapplication.R;
import blackbird.com.screenapplication.receiver.ScreenReciver;

public class AppService extends Service {
    private IBinder serviceBinder = new ServiceBind();

    public AppService() {
    }

    public class ServiceBind extends Binder {
        public AppService getService() {
            return AppService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        RegisterScreenReciver();
        showNotification();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在此重新启动,使服务常驻内存
        startService(new Intent(this, AppService.class));
    }

    private void RegisterScreenReciver() {
        IntentFilter screenStatus = new IntentFilter();
        screenStatus.addAction(Intent.ACTION_SCREEN_ON);
        screenStatus.addAction(Intent.ACTION_SCREEN_OFF);
        screenStatus.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(new ScreenReciver(), screenStatus);
    }

    /**
     * 启动前台通知
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification() {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        //创建通知详细信息
        Notification.Builder mBuilder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(dateNowStr)
                .setContentText("今天是个好日子，易出行、忌宅家");
        //创建点击跳转Intent
        Intent intent = new Intent(this, MainActivity.class);
        //创建任务栈Builder
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //设置跳转Intent到通知中
        mBuilder.setContentIntent(pendingIntent);
        //获取通知服务
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //构建通知
        Notification notification = mBuilder.build();
        //显示通知
        nm.notify(0, notification);
        //启动为前台服务
        startForeground(0, notification);
    }
}
