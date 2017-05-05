package tech.yibeiliu.retrofitrxjavamvpdemo.ui.topmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import tech.yibeiliu.retrofitrxjavamvpdemo.R;

/**
 * Created by YiBeiLiu on 2017/04/25.
 *
 * activity 作为天使类，负责 fragment(V层) 和 Presenter(P层) 的创建
 */

public class TopMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topmovies);

        initToolBar();

        TopMoviesFragment topMoviesFragment = (TopMoviesFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (topMoviesFragment == null) {
            topMoviesFragment = new TopMoviesFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, topMoviesFragment);
            transaction.commit();
        }
        // 把 view 传递给 presenter
        new TopMoviesPresenter(topMoviesFragment);

    }

    private void initToolBar() {
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TopMovies");
        setSupportActionBar(toolbar);
    }
}
