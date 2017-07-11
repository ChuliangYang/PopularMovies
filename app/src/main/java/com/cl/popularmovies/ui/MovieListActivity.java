package com.cl.popularmovies.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cl.popularmovies.Interface.OnReceiveMoviesList;
import com.cl.popularmovies.adapter.MovieListAdapter;
import com.cl.popularmovies.bean.MovieListResponseBean;
import com.cl.popularmovies.net.RequestMovieList;
import com.cl.popularmovies.widget.CustomRecyclerView;
import com.com.cl.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListActivity extends BaseActivity implements OnReceiveMoviesList {
    private final int SORT_POP = 1;
    private final int SORT_RATED = 2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_movie_list)
    CustomRecyclerView rv_movie_list;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private List<MovieListResponseBean.ResultsBean> resultsBeans = new ArrayList();
    private MovieListAdapter movieListAdapter;
    private MovieListActivity MovieListActivity;
    private int page = 1;
    private int sortType = SORT_POP;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieListActivity = this;
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        setUpToolBar();
        configAlertDialog();
        setUpSwipeLayout();
        setUpRecyclerView();
        requestMoviesList();
    }

    private void configAlertDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_exit);
        builder.setMessage(R.string.exit_message);
        builder.setNegativeButton(R.string.button_cancel, (dialog, which) -> alertDialog.dismiss());
        builder.setPositiveButton(R.string.button_confirm, (dialog, which) -> {
            System.exit(0);
            finish();
        });
    }

    private void setUpSwipeLayout() {
        swipeRefresh.setOnRefreshListener(() -> {
            switch (sortType) {
                case SORT_POP:
                    page = 1;
                    new RequestMovieList(MovieListActivity, RequestMovieList.SortType.SORT_POPULAR, MovieListActivity).showProgressBar(false).request(page);
                    break;
                case SORT_RATED:
                    page = 1;
                    new RequestMovieList(MovieListActivity, RequestMovieList.SortType.SORT_RATED, MovieListActivity).showProgressBar(false).request(page);
                    break;
            }
        });
        swipeRefresh.setColorSchemeResources(android.R.color.holo_red_light);
    }

    private void requestMoviesList() {
        new RequestMovieList(this, RequestMovieList.SortType.SORT_POPULAR, this).request(page);
    }

    private void setUpToolBar() {
        toolbar.setTitle(R.string.movie_list_activity);
        setSupportActionBar(toolbar);
    }

    private void setUpRecyclerView() {
        rv_movie_list.setLayoutManager(new GridLayoutManager(this, 2));
        rv_movie_list.setHasFixedSize(true);
        movieListAdapter = new MovieListAdapter(this, resultsBeans);
        rv_movie_list.setAdapter(movieListAdapter);
        rv_movie_list.setOnLoadMoreListener(() -> {
            switch (sortType) {
                case SORT_POP:
                    new RequestMovieList(MovieListActivity, RequestMovieList.SortType.SORT_POPULAR, MovieListActivity).showProgressBar(false).request(++page);
                    break;
                case SORT_RATED:
                    new RequestMovieList(MovieListActivity, RequestMovieList.SortType.SORT_RATED, MovieListActivity).showProgressBar(false).request(++page);
                    break;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        rv_movie_list.hideBottomView();
        switch (item.getItemId()) {
            case R.id.action_popular:
                page = 1;
                sortType = SORT_POP;
                new RequestMovieList(MovieListActivity, RequestMovieList.SortType.SORT_POPULAR, MovieListActivity).request(page);
                break;
            case R.id.action_rated:
                page = 1;
                sortType = SORT_RATED;
                new RequestMovieList(MovieListActivity, RequestMovieList.SortType.SORT_RATED, MovieListActivity).request(page);
                break;
        }
        return true;
    }

    @Override
    public void onSuccess(List<MovieListResponseBean.ResultsBean> movieBeans) {
        swipeRefresh.setRefreshing(false);
        if (movieBeans != null && movieBeans.size() >= 0) {
            if (page == 1) {
                resultsBeans.clear();
            }
            resultsBeans.addAll(movieBeans);
            if (page == 1) {
                movieListAdapter.notifyDataSetChanged();
            } else {
                movieListAdapter.notifyItemRangeInserted(resultsBeans.size() - movieBeans.size(), movieBeans.size());
            }
//            Log.d("recyclerview", "------------------------------------adapter数据刷新---------------------------------------");
//            Log.d("recyclerview", "从第" + String.valueOf(resultsBeans.size() - movieBeans.size() + 1) + "个开始添加共20个");
//            Log.d("recyclerview", "------------------------------------adapter数据刷新---------------------------------------");
        }
    }

    @Override
    public void onFailed(Object MovieBeans) {
        swipeRefresh.setRefreshing(false);
        Log.e("recyclerview", "---------------------------------------------------请求失败隐藏footview----------------------------------");
        rv_movie_list.hideBottomView();
        Toast.makeText(this, "request failed,please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        alertDialog = builder.show();
    }

}
