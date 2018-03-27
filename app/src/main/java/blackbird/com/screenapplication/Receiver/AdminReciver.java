package blackbird.com.screenapplication.Receiver;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

/**
 * Created by yzzhang on 2018/3/27.
 */

public class AdminReciver extends DeviceAdminReceiver {

    @Override
    public DevicePolicyManager getManager(Context context) {
        Log.e("AdminReciver", "getManager: <<--------------- >>getManager");
        return super.getManager(context);
    }

    @Override
    public ComponentName getWho(Context context) {
        Log.e("AdminReciver", "getWho: <<--------------- >>getWho");
        return super.getWho(context);
    }
}
