package com.cl.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cl.popularmovies.bean.TrailersResponseBean;
import com.cl.popularmovies.constant.Network;
import com.com.cl.popularmovies.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailersAdapter extends RecyclerView.Adapter {


    private Context context;
    private List<TrailersResponseBean.ResultsBean> results;

    public TrailersAdapter(Context context, List<TrailersResponseBean.ResultsBean> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailers_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TrailerViewHolder) {
            ((TrailerViewHolder) holder).tv_trailer.setText("Trailer" + (position + 1));
        }
    }

    @Override
    public int getItemCount() {
        return results != null ? results.size() : 0;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_trailer)
        TextView tv_trailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(Network.YOUTUBE_BASE_URL + results.get(getAdapterPosition()).getKey());
                intent.setData(content_url);
                context.startActivity(intent);
            });
        }
    }
}
