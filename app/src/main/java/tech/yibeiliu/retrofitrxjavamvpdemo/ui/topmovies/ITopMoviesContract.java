package tech.yibeiliu.retrofitrxjavamvpdemo.ui.topmovies;


import java.util.List;

import tech.yibeiliu.retrofitrxjavamvpdemo.IBasePresenter;
import tech.yibeiliu.retrofitrxjavamvpdemo.IBaseView;
import tech.yibeiliu.retrofitrxjavamvpdemo.bean.Subjects;

/**
 * Created by YiBeiLiu on 2017/04/25.
 *
 * 仿照谷歌官方demp （TodoList） 把 View 和 presenter 接口放在契约类中。
 */

public interface ITopMoviesContract {

    interface IPresenter extends IBasePresenter {

        void onDestroy();

        void onRefresh(String city);

        void onLoadMore(String city);

    }

    interface IView extends IBaseView<IPresenter> {

        void responseMoviesList(List<Subjects> subjects);

        void responseError(String message);

        void noMoreData();

        void responseMoviesListMore(List<Subjects> subjects);

    }
}
