package tech.yibeiliu.retrofitrxjavamvpdemo.model;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import tech.yibeiliu.retrofitrxjavamvpdemo.api.RetrofitClient;
import tech.yibeiliu.retrofitrxjavamvpdemo.bean.Movies;

/**
 * Created by YiBeiLiu on 2017/04/25.
 * <p>
 * 为了体现单一职责原则，将访问网络的具体代码抽出放在 HttpModelImpl 中，让 presenter 只负责调度（主持人的角色）
 */

public class HttpModelImpl implements IHttpModel {


    private HttpModelImpl() {
    }

    private static class HttpModelImplHolder{
        private static IHttpModel instance = new HttpModelImpl();
    }

    //静态内部类实现单例模式
    public static IHttpModel getInstance() {
        return HttpModelImplHolder.instance;
    }

    /**
     * 下拉刷新
     *
     * @param city
     * @param start
     * @param count
     */
    @Override
    public void requestMoviesList(String city, int start, int count, Observer<Movies> observer) {
        RetrofitClient.getInstance().getApiService()
                .getMoviesList(city, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 上拉加载更多
     *
     * @param city
     * @param start
     * @param count
     */
    @Override
    public void requestMoviesListMore(String city, int start, int count, Observer<Movies> observer) {
        RetrofitClient.getInstance()
                .getApiService()
                .getMoviesList(city, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
