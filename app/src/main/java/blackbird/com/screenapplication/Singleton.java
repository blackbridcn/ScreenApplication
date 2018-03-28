package blackbird.com.screenapplication;

/**
 * Created by yuzhu on 2018/3/28.
 */

public class Singleton {

    private volatile static Singleton instance = null;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
