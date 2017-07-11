package com.cl.popularmovies.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.cl.popularmovies.Interface.OnLoadMoreListener;

//支持GridLayoutManager的footview加载更多,并且自动管理footview的显示与隐藏

public class CustomRecyclerView extends RecyclerView {
    private Boolean isLoading = false;
    private OnLoadMoreListener onLoadMoreListener;
    private Boolean isScroll = false;
    private int beforeLoadAmount = -1;
    private int scrollFromBottomViewTop = 0;
    private Boolean startCount = false;
    private GridLayoutManager gridLayoutManager;
    private Boolean showBottomView = true;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        if (getLayoutManager() != null && getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (getAdapter().getItemViewType(position)) {
                        case 0:
                            return 1;
                        case 1:
                            return 2;
                    }
                    return 0;
                }
            });
        }
        super.onAttachedToWindow();
    }

    private void init() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != SCROLL_STATE_IDLE) {
                    isScroll = true;
                    showBottomView = true;
                } else {
                    isScroll = false;
                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                gridLayoutManager = (GridLayoutManager) getLayoutManager();
                if (!isScroll) {
                    hideBottomView();
                    Log.e("recyclerview", "---------------------------------------------------控件没有滑动---------------------------------------");

                }
                if (beforeLoadAmount < getAdapter().getItemCount() && beforeLoadAmount != -1) {
                    isLoading = false;
                    beforeLoadAmount = -1;
                    hideBottomView();
                }
                if (showBottomView) {
                    if (gridLayoutManager != null && getAdapter() != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() >= (getAdapter().getItemCount() - 2)) {
                        if (getBottomView() != null) {
                            getBottomView().setVisibility(View.VISIBLE);
                            Log.e("recyclerview", "---------------------------------------------------显示footview---------------------------------------");
                            showBottomView = false;
                        }
                    }
                }
                if (isBottomViewVisible() && isScroll) {//recyclerview初始化会在用户未滑动的情况下自动调用几次onScrolled（），而且此时由于没有数据，底部view算是可见，加入isScroll来准确判断用户操作引发的滑动，避免因为这种BUG引发的误判
                    Log.e("recyclerview", "------------------------------------footview可见---------------------------------------");
                    if (startCount) {
                        scrollFromBottomViewTop += dy;
                        Log.e("scrollFromBottomViewTop", String.valueOf(scrollFromBottomViewTop));
                    }
                    if (!isLoading && (scrollFromBottomViewTop >= (getBottomView().getHeight() * 2 / 3))) {
                        Log.e("recyclerview", "---------------------------------------------------footview超过一半可见，开始刷新-----------------------------------------");
                        Log.e("recyclerview", "BottomView的高度为" + getBottomView().getHeight());
                        onLoadMoreListener.onLoadMore();
                        isLoading = true;
                        beforeLoadAmount = getAdapter().getItemCount();
                        startCount = false;
                        scrollFromBottomViewTop = 0;
                    }
                    startCount = true;
                }
            }
        });
    }

    private Boolean isBottomViewVisible() {
        gridLayoutManager = (GridLayoutManager) getLayoutManager();
        return gridLayoutManager.findLastVisibleItemPosition() != NO_POSITION && gridLayoutManager.findLastVisibleItemPosition() == (getAdapter().getItemCount() - 1);

    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    private View getBottomView() {
        if ((getLayoutManager()).findViewByPosition(getAdapter().getItemCount() - 1) != null) {
            return (getLayoutManager()).findViewByPosition(getAdapter().getItemCount() - 1);
        } else {
            return null;
        }

    }

    public void hideBottomView() {
        if (gridLayoutManager != null && getAdapter() != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() >= (getAdapter().getItemCount() - 2)) {
            if (getBottomView() != null) {
                getBottomView().setVisibility(View.GONE);
            }
            showBottomView = false;
        }

    }
}
