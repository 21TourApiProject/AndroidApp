package com.starrynight.tourapiproject.starPage;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class StarRecyclerViewWidth extends RecyclerView.ItemDecoration {

    private final int divWidth;

    public StarRecyclerViewWidth(int divWidth) {
        this.divWidth = divWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = divWidth;
    }
}
