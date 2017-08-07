package com.cl.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cl.popularmovies.bean.ReviewsResponseBean;
import com.com.cl.popularmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ReviewsResponseBean.ResultsBean> results;

    public ReviewsAdapter(Context context, List<ReviewsResponseBean.ResultsBean> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReviewsHolder) {
            ((ReviewsHolder) holder).tvName.setText(results.get(position).getAuthor());
            ((ReviewsHolder) holder).tvReview.setText(results.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_review)
        TextView tvReview;

        public ReviewsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
