package tech.yibeiliu.retrofitrxjavamvpdemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by YiBeiLiu on 2017/04/27.
 */

public class NetworkUtils {
    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    private static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}
