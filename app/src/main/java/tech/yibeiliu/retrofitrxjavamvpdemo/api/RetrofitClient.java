package tech.yibeiliu.retrofitrxjavamvpdemo.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by YiBeiLiu on 2017/04/25.
 */

public class RetrofitClient {

    private APIService apiService;
    private OkHttpClient client;

    // 静态内部类实现单例
    private static class RetrofitClientHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    private RetrofitClient() {

        initInterceptor();//初始化 okhttp 拦截器

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(APIService.BASE_URL)
                .build();

        apiService = mRetrofit.create(APIService.class);
    }


    public static RetrofitClient getInstance() {
        return RetrofitClientHolder.INSTANCE;
    }

    public APIService getApiService() {
        if (apiService != null) {
            return apiService;
        } else {
            throw new NullPointerException("oh my god! apiService is null!");
        }
    }


    private void initInterceptor() {
        // okHttp 日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }


}
//    =============================================================================================

/**
 * 以下注释代码适用于 baseUrl 不唯一的情况，这种情况下，抛弃单例模式，直接用 static 修饰直接调用
 * 还有什么更好的方法吗？
 */
//
//    private static OkHttpClient mOkHttpClient;
//
//    static {
//        initOkHttpClient();
//    }
//
//    private static void initOkHttpClient() {
//
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        mOkHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .build();
//    }
//
//    /**
//     * 根据传入的 baseUrl，和 api 创建 retrofit
//     */
//    private static <T> T createApi(Class<T> clazz, String baseUrl) {
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .client(mOkHttpClient)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return retrofit.create(clazz);
//    }
//}
