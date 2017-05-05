package tech.yibeiliu.retrofitrxjavamvpdemo.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tech.yibeiliu.retrofitrxjavamvpdemo.bean.Movies;

/**
 * Created by YiBeiLiu on 2017/04/25.
 */

public interface APIService {

    //    https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=%E5%8C%97%E4%BA%AC&start=0&count=100

    String BASE_URL = "https://api.douban.com/v2/movie/";

    @GET("in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b")
    Observable<Movies> getMoviesList(@Query("city") String city, @Query("start") int start, @Query("count") int count);
}
