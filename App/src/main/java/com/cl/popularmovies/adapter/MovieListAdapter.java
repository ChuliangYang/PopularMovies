package com.cl.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cl.popularmovies.bean.MovieListResponseBean;
import com.cl.popularmovies.helper.JointHelper;
import com.cl.popularmovies.ui.MovieDetailActivity;
import com.com.cl.popularmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieListAdapter extends RecyclerView.Adapter {
    private final int ContentView = 0;
    private final int BottomView = 1;
    private Context context;
    private List<MovieListResponseBean.ResultsBean> resultsBeans;
    private RequestOptions options;

    public MovieListAdapter(Context context, List<MovieListResponseBean.ResultsBean> resultsBeans) {
        this.context = context;
        this.resultsBeans = resultsBeans;
        configGlide();
    }

    private void configGlide() {
        options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        options.centerCrop();
        options.format(DecodeFormat.PREFER_RGB_565);
        options.placeholder(R.drawable.ic_place_holder);
        options.skipMemoryCache(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ContentView:
                return new MovieItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_recyclerview, parent, false));

            case BottomView:
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                FrameLayout linearLayout = new FrameLayout(context);
                ProgressBar progressBar = new ProgressBar(context);
                progressBar.setLayoutParams(layoutParams);
                linearLayout.addView(progressBar);
                return new BottomViewHolder(linearLayout);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieItemViewHolder) {
            Glide.with(context).load(JointHelper.jointPicURL(resultsBeans.get(position).getPoster_path())).apply(options).into(((MovieItemViewHolder) holder).iv_movie);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return BottomView;
        } else {
            return ContentView;
        }
    }

    @Override
    public int getItemCount() {
        return resultsBeans != null && resultsBeans.size() != 0 ? resultsBeans.size() + 1 : 0;
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_movie)
        ImageView iv_movie;

        public MovieItemViewHolder(View itemView) {
            super(itemView);
            if (context instanceof Activity) {
                ButterKnife.bind(this, itemView);
            }
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("MovieDetailBean", resultsBeans.get(getAdapterPosition()));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, v, "share");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    context.startActivity(intent, options.toBundle());
                } else {
                    context.startActivity(intent);
                }
            });
        }
    }

    public class BottomViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;

        public BottomViewHolder(View itemView) {
            super(itemView);
            frameLayout = (FrameLayout) itemView;

        }
    }
}
