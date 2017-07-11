package com.cl.popularmovies.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cl.popularmovies.bean.MovieListResponseBean;
import com.cl.popularmovies.helper.JointHelper;
import com.com.cl.popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieDetailActivity extends BaseActivity {

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
    private MovieListResponseBean.ResultsBean resultsBean;
    private RequestOptions options;

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
        }
    }

    private void configTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet transitionSet=new TransitionSet();
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
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> supportFinishAfterTransition());
    }
}
