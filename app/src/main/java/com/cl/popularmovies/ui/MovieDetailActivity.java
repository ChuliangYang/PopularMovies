package com.cl.popularmovies.ui;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cl.popularmovies.Interface.OnReceiveReviewsData;
import com.cl.popularmovies.Interface.OnReceiveTrailersData;
import com.cl.popularmovies.adapter.ReviewsAdapter;
import com.cl.popularmovies.adapter.TrailersAdapter;
import com.cl.popularmovies.bean.MovieListResponseBean;
import com.cl.popularmovies.bean.ReviewsResponseBean;
import com.cl.popularmovies.bean.TrailersResponseBean;
import com.cl.popularmovies.constant.Network;
import com.cl.popularmovies.database.contract.FavoriteMoviesContract;
import com.cl.popularmovies.database.contract.FavoriteMoviesContract.FavoriteMoviesEntry;
import com.cl.popularmovies.helper.JointHelper;
import com.cl.popularmovies.net.RequestTrailerAndReviewList;
import com.com.cl.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends BaseActivity implements OnReceiveTrailersData, OnReceiveReviewsData {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cv_title)
    CardView cvTitle;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.tv_vote)
    TextView tvVote;
    @BindView(R.id.tv_synopsis)
    TextView tvSynopsis;
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailers;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;
    @BindView(R.id.tv_trailers)
    TextView tvTrailers;
    @BindView(R.id.tv_reviews)
    TextView tvReviews;
    @BindView(R.id.cb_favorite)
    CheckBox cbFavorite;
    private MovieListResponseBean.ResultsBean resultsBean;
    private RequestOptions options;
    private List<TrailersResponseBean.ResultsBean> trailersBeans = new ArrayList();
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private List<ReviewsResponseBean.ResultsBean> reviewsBeans = new ArrayList();
    private ContentValues contentValues = new ContentValues();
    private boolean isFavorite = false;
    private ContentResolver contentResolver;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_movie_detail);
        ButterKnife.bind(this);
        setUpToolBar();
        configGlide();
        configTransition();
        if (getIntent().getParcelableExtra("MovieDetailBean") != null && getIntent().getParcelableExtra("MovieDetailBean") instanceof MovieListResponseBean.ResultsBean) {
            resultsBean = getIntent().getParcelableExtra("MovieDetailBean");
        }
        if (resultsBean != null) {
            tvTitle.setText(resultsBean.getOriginal_title());
            Glide.with(this).load(JointHelper.jointPicURL(resultsBean.getPoster_path())).apply(options).into(ivPoster);
            tvYear.setText(resultsBean.getRelease_date());
            tvVote.setText(JointHelper.jointVoteAverage(resultsBean.getVote_average()));
            tvSynopsis.setText(resultsBean.getOverview());

            contentValues.put(FavoriteMoviesEntry._ID, resultsBean.getId());
            contentValues.put(FavoriteMoviesEntry.COLUM_POSTER_PATH, resultsBean.getPoster_path());
            contentValues.put(FavoriteMoviesEntry.COLUM_ORIGINAL_TITLE, resultsBean.getOriginal_title());
            contentValues.put(FavoriteMoviesEntry.COLUM_OVER_VIEW, resultsBean.getOverview());
            contentValues.put(FavoriteMoviesEntry.COLUM_RELEASE_DATA, resultsBean.getRelease_date());
            contentValues.put(FavoriteMoviesEntry.COLUM_VOTE_AVERAGE, resultsBean.getVote_average());

            new RequestTrailerAndReviewList(this, resultsBean.getId(), this, this).request();
        }
        rvTrailers.setHasFixedSize(true);
        rvTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        trailersAdapter = new TrailersAdapter(this, trailersBeans);
        rvTrailers.setAdapter(trailersAdapter);

        rvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewsAdapter = new ReviewsAdapter(this, reviewsBeans);
        rvReviews.setAdapter(reviewsAdapter);
        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> isFavorite = isChecked);
        checkedIfFavorite();
    }

    private void checkedIfFavorite() {
        contentResolver = getContentResolver();
        Cursor query = contentResolver.query(Uri.parse(FavoriteMoviesContract.BASE_CONTENT_URI + "/" + FavoriteMoviesContract.FAVORITE_MOVIES_PATH + "/" + resultsBean.getId()), new String[]{FavoriteMoviesEntry._ID}, null, null, null);
        cbFavorite.setChecked(query.moveToFirst());
        query.close();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFavorite) {
            contentResolver.insert(Uri.parse(FavoriteMoviesContract.BASE_CONTENT_URI + "/" + FavoriteMoviesContract.FAVORITE_MOVIES_PATH), contentValues);
        } else {
            contentResolver.delete(Uri.parse(FavoriteMoviesContract.BASE_CONTENT_URI + "/" + FavoriteMoviesContract.FAVORITE_MOVIES_PATH + "/" + resultsBean.getId()), null, null);
        }

    }

    private void configTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.addTransition(new ChangeImageTransform());
            transitionSet.addTransition(new ChangeBounds());
            getWindow().setSharedElementEnterTransition(transitionSet);
            getWindow().setSharedElementExitTransition(transitionSet);
            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Fade());
            getWindow().setAllowEnterTransitionOverlap(false);
            getWindow().setAllowReturnTransitionOverlap(false);
        }
    }

    private void configGlide() {
        options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        options.centerCrop();
        options.format(DecodeFormat.PREFER_ARGB_8888);
    }

    private void setUpToolBar() {
        toolbar.setTitle(R.string.movie_detail_activity);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> supportFinishAfterTransition());
    }

    @Override
    public void onReceiveTrailersSuccess(List resultsBeans) {
        if (resultsBeans != null && resultsBeans.size() > 0) {
            tvTrailers.setVisibility(View.VISIBLE);
            this.trailersBeans.addAll(resultsBeans);
            trailersAdapter.notifyDataSetChanged();
        } else {
            tvTrailers.setVisibility(View.GONE);
        }

    }

    @Override
    public void onReceiveTrailersFailed(Object failed) {

    }

    @Override
    public void onReceiveReviewsSuccess(List resultsBean) {
        if (resultsBean != null && resultsBean.size() > 0) {
            tvReviews.setVisibility(View.VISIBLE);
            reviewsBeans.addAll(resultsBean);
            reviewsAdapter.notifyDataSetChanged();
        } else {
            tvReviews.setVisibility(View.GONE);
        }


    }

    @Override
    public void onReceiveReviewsFailed(Object failed) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (resultsBean != null && trailersBeans.size() > 0) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, String.format("%s Trailer URL : %s", resultsBean.getOriginal_title(), Network.YOUTUBE_BASE_URL + trailersBeans.get(0).getKey()));
                    Log.e("share text", String.format("%s Trailer URL : %s", resultsBean.getTitle(), Network.YOUTUBE_BASE_URL + trailersBeans.get(0).getKey()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, "share the first trailer url to"));
                }
                break;
        }
        return true;
    }
}
