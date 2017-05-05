package tech.yibeiliu.retrofitrxjavamvpdemo.ui.topmovies;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import tech.yibeiliu.retrofitrxjavamvpdemo.R;
import tech.yibeiliu.retrofitrxjavamvpdemo.bean.Subjects;

/**
 * Created by YiBeiLiu on 2017/04/25.
 */

public class TopMoviesFragment extends Fragment implements ITopMoviesContract.IView, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private PullToRefreshAdapter pullToRefreshAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ITopMoviesContract.IPresenter mPresenter;

    public TopMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_movies, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.MAGENTA, Color.BLUE);

        initAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();//首次进入时刷新页面
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();//页面销毁时，与该页面相关的 P 层和 M 层也应该销毁
    }

    private void initAdapter() {
        pullToRefreshAdapter = new PullToRefreshAdapter();
        pullToRefreshAdapter.setOnLoadMoreListener(this, mRecyclerView);//利用 BaseQuickAdapter 实现 上拉加载更多
        pullToRefreshAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);//开启动画
        mRecyclerView.setAdapter(pullToRefreshAdapter);

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);//显示加载小圆圈
        pullToRefreshAdapter.setEnableLoadMore(false);//下拉刷新的同时不能上拉加载
        mPresenter.onRefresh("北京");
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);//上拉加载的同时不能下拉刷新
        mPresenter.onLoadMore("北京");
    }

    /**
     * 把 presenter 对象传递给 fragment (V层)
     * @param mPresenter
     */
    @Override
    public void setPresenter(ITopMoviesContract.IPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    /**
     * 下拉刷新时，从网络返回的数据
     * @param subjects
     */
    @Override
    public void responseMoviesList(List<Subjects> subjects) {
        pullToRefreshAdapter.setDataRefresh(subjects);
        mSwipeRefreshLayout.setRefreshing(false);
        pullToRefreshAdapter.setEnableLoadMore(true);
    }

    /**
     * 网络返回错误
     * @param message
     */
    @Override
    public void responseError(String message) {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), "错误信息： " + message, Toast.LENGTH_SHORT).show();
        pullToRefreshAdapter.setEnableLoadMore(true);
    }

    /**
     * 上拉加载时，没有更多数据时调用
     */
    @Override
    public void noMoreData() {
        mSwipeRefreshLayout.setEnabled(true);
        pullToRefreshAdapter.loadMoreEnd(false);
    }

    /**
     * 上拉加载时，从网络返回的数据
     * @param subjects
     */
    @Override
    public void responseMoviesListMore(List<Subjects> subjects) {
        mSwipeRefreshLayout.setEnabled(true);
        pullToRefreshAdapter.setDataMore(subjects);//给 adapter 去刷新数据
        pullToRefreshAdapter.loadMoreComplete();
    }

    /**
     * 该 adapter 采用了 BRVAH ，参见：http://www.recyclerview.org/
     * 使用该 adapter 可以很方便的简化 recyclerView 的 adapter，并且一句代码实现上拉加载，配合 SwipeRefreshLayout 下拉刷新，十分酸爽
     */

    class PullToRefreshAdapter extends BaseQuickAdapter<Subjects, BaseViewHolder> {

        public PullToRefreshAdapter() {
            super(R.layout.list_item_topmovies, new ArrayList<Subjects>());
        }

        @Override
        protected void convert(BaseViewHolder helper, Subjects item) {
            helper.setText(R.id.itemTitle, item.getTitle());
            helper.setText(R.id.itemdes, "大陆 " + item.getMainland_pubdate() + " 上映  片长: " + item.getDurations());
            Glide.with(getActivity()).load(item.getImages().getMedium()).into((ImageView) helper.getView(R.id.itemImage));
        }

        public void setDataRefresh(List<Subjects> lists) {
            this.getData().clear();
            this.getData().addAll(lists);
            notifyDataSetChanged();
        }
        public void setDataMore(List<Subjects> lists){
            this.getData().addAll(lists);
            notifyDataSetChanged();
        }
    }
}
