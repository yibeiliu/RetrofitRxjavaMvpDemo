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

    private static volatile Object apiService;
    private OkHttpClient client;

    private RetrofitClient() {
        initInterceptor();//初始化 okhttp 拦截器
    }

    private static class RetrofitClientHolder {         //静态内部类实现单例模式
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return RetrofitClientHolder.INSTANCE;
    }

    /**
     * 保持 apiService 单例
     * @param clazz
     * @param baseUrl
     * @param <T>
     * @return
     */
    public <T> T getAPIService(Class<T> clazz, String baseUrl) {
        if (apiService == null) {
            synchronized (RetrofitClient.class) {
                if (apiService == null) {
                    Retrofit mRetrofit = new Retrofit.Builder()
                            .client(client)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(baseUrl)
                            .build();
                    apiService = mRetrofit.create(clazz);
                }
            }
        }
        return (T) apiService;
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