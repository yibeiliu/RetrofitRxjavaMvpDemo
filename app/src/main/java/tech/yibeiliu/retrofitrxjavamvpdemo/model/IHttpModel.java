package tech.yibeiliu.retrofitrxjavamvpdemo.model;

import io.reactivex.Observer;
import tech.yibeiliu.retrofitrxjavamvpdemo.bean.Movies;

/**
 * Created by YiBeiLiu on 2017/04/25.
 *
 * 为了体现单一职责原则，将访问网络的具体代码抽出放在 HttpModelImpl 中，让 presenter 只负责调度（主持人的角色）
 */

public interface IHttpModel {
    void requestMoviesList(String city, int start, int count, Observer<Movies> observer);
    void requestMoviesListMore(String city, int start, int count, Observer<Movies> observer);
}
