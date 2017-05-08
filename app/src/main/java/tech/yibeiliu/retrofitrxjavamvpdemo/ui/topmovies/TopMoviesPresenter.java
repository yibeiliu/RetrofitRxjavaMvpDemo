package tech.yibeiliu.retrofitrxjavamvpdemo.ui.topmovies;

import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tech.yibeiliu.retrofitrxjavamvpdemo.bean.Movies;
import tech.yibeiliu.retrofitrxjavamvpdemo.model.HttpModelImpl;
import tech.yibeiliu.retrofitrxjavamvpdemo.model.IHttpModel;

/**
 * Created by YiBeiLiu on 2017/04/25.
 */

public class TopMoviesPresenter implements ITopMoviesContract.IPresenter {

    private CompositeDisposable disposableSet; //Rxjava2.0新增，通过他可以管理订阅
    private ITopMoviesContract.IView mView;
    private final int COUNT = 10;
    private int start = 0;  // 请求起始
    private int total = 0;  // 服务器共有 total 个电影数据
    private IHttpModel mHttpModelImpl;


    public TopMoviesPresenter(Context context,ITopMoviesContract.IView view) {
        this.mView = view;
        disposableSet = new CompositeDisposable();
        mView.setPresenter(this);
        mHttpModelImpl = HttpModelImpl.getInstance();

    }


    @Override
    public void unSubscribe() {
        disposableSet.clear();
    }

    @Override
    public void onDestroy() {
        unSubscribe();//解除订阅同时取消网络访问
        mView = null;//防止内存泄漏
    }

    /**
     * 下拉刷新
     *
     * @param city
     */
    @Override
    public void onRefresh(String city) {
        start = 0;//刷新时，从 0 开始加载
        //访问 M 层访问网络
        Observer<Movies> observer = new Observer<Movies>() {
            @Override
            public void onSubscribe(Disposable d) {
                //注册该 observer，方便取消订阅与取消访问
                disposableSet.add(d);
            }

            @Override
            public void onNext(Movies movies) {

                if (mView != null) {  //如果 fragment 销毁了，但是网络访问回来的结果又调用了 mView.responseMoviesList 导致空指针异常
                    //把获得的 movies 对象返回给 view 展示
                    mView.responseMoviesList(movies.getSubjects());
                    total = movies.getTotal();//记录一下服务器上总共有多少个数据，方便本地判断是否还有更多数据需要加载
                }
            }

            @Override
            public void onError(Throwable e) {
                //错误处理
                if (mView != null) {
                    mView.responseError(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
            }
        };

        mHttpModelImpl.requestMoviesList(city, start, COUNT, observer);
    }

    /**
     * 上拉加载更多
     *
     * @param city
     */
    @Override
    public void onLoadMore(String city) {
        start += COUNT;//往后推 COUNT 个数据
        if (start > total) {
            // 没有更多的数据了
            if (mView != null) {
                mView.noMoreData();
            }
            return;
        }

        Observer<Movies> observer = new Observer<Movies>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableSet.add(d);
            }

            @Override
            public void onNext(Movies movies) {
                total = movies.getTotal();
                if (mView != null) {
                    mView.responseMoviesListMore(movies.getSubjects());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mView.responseError(e.getMessage());
                }
            }

            @Override
            public void onComplete() {
            }
        };


        mHttpModelImpl.requestMoviesListMore(city, start, COUNT, observer);
    }
}
