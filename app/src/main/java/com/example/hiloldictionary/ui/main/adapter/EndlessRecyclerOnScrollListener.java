package com.example.hiloldictionary.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndlessRecyclerOnScrollListener
        extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private IAction action;
    private int firstVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true;// True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5;// The minimum amount of items to have below your current scroll position before loading more.
    private int current_page = 1;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager layoutManager,
                                           IAction action) {
        this.layoutManager = layoutManager;
        this.action = action;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            current_page++;
            action.onAction();
            loading = true;
        }
    }
}
