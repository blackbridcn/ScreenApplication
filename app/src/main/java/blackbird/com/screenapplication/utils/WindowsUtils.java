package blackbird.com.screenapplication.utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;

public class WindowsUtils {
    /**
     * 设置桌面壁纸
     * @param mContext
     */
    public void SetWallPaper(Context mContext,int res) {
        WallpaperManager mWallManager = WallpaperManager.getInstance(mContext);
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), res);
           // Bitmap bitmap = BitmapFactory.decodeFile(imageFilesPath);
            mWallManager.setBitmap(bitmap);
            Toast.makeText(mContext, "壁纸设置成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置锁屏壁纸
     * @param mContext
     * @param bitmap
     */
    private void SetLockWallPaper(Context mContext,int bitmap) {
        try {
            WallpaperManager mWallManager = WallpaperManager.getInstance(mContext);
            Class class1 = mWallManager.getClass();//获取类名
            Method setWallPaperMethod = class1.getMethod("setBitmapToLockWallpaper", Bitmap.class);
            Bitmap bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), bitmap);
            //BitmapFactory.decodeFile(imageFilesPath)
            //获取设置锁屏壁纸的函数
            setWallPaperMethod.invoke(mWallManager, bitmap1);
            //调用锁屏壁纸的函数，并指定壁纸的路径imageFilesPath

            Toast.makeText(mContext, "锁屏壁纸设置成功", Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
