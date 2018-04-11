package blackbird.com.screenapplication.utils;

import android.app.ActivityManager;
import android.content.Context;

public class ProcessUtils {
/*
    //获取当前进程的方法
        android.os.Process.getElapsedCpuTime();//获取消耗的时间。
        android.os.Process.myPid();//获取该进程的ID。
        android.os.Process.myTid();//获取该线程的ID。
        android.os.Process.myUid();//获取该进程的用户ID。
    //判断该进程是否支持多进程*/

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
