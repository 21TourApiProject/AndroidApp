package com.starrynight.tourapiproject.postItemPage;

import android.view.View;

import com.starrynight.tourapiproject.TouristspotPage.SearchAdapter;

public interface OnPostItemClickListener {
    public void onItemClick(Post_point_item_Adapter.ViewHolder holder, View view, int position);
}
