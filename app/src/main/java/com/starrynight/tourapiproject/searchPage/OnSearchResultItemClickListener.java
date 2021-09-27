package com.starrynight.tourapiproject.searchPage;

import android.view.View;


public interface OnSearchResultItemClickListener {
    public void onItemClick(SearchResultAdapter.ViewHolder holder, View view, int position);
}
