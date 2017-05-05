package tech.yibeiliu.retrofitrxjavamvpdemo;

import android.app.Application;

/**
 * Created by YiBeiLiu on 2017/04/25.
 */

public class App extends Application {
    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static App getApplication() {
        return application;
    }


}