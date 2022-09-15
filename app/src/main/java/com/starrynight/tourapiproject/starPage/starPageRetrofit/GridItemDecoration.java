package com.starrynight.tourapiproject.starPage.starPageRetrofit;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @className : GridItemDecoration
 * @description : 오늘 볼 수 있는 천체, 모든 천체 보기에서 쓰이는 grid 관련 class
 * @modification : 2022-09-15 (hyeonz) 주석추가
 * @author : hyeonz
 * @date : 2022-09-15
 * @version : 1.0
====개정이력(Modification Information)====
수정일        수정자        수정내용
-----------------------------------------
2022-09-15   hyeonz      주석추가
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int size12;
    private int size6;
    private int size8;

    public GridItemDecoration(Context context) {

        size12 = dpToPx(context, 12);
        size6 = dpToPx(context, 6);
        size8 = dpToPx(context, 8);
    }

    @Override
    public void getItemOffsets
            (@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        //상하 설정
        if (position == 0 || position == 1) {
            // 첫번 째 줄 아이템
            outRect.top = size8;
            outRect.bottom = size12;
        } else {
            outRect.bottom = size12;
        }

        // spanIndex = 0 -> 왼쪽
        // spanIndex = 1 -> 오른쪽
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        if (spanIndex == 0) {
            //왼쪽 아이템
            outRect.left = size12;
            outRect.right = size6;

        } else if (spanIndex == 1) {
            //오른쪽 아이템
            outRect.left = size6;
            outRect.right = size12;
        }

        outRect.top = size8;
        outRect.right = size12;
        outRect.bottom = size8;
        outRect.left = size12;
    }

    // dp -> pixel 단위로 변경
    private int dpToPx(Context context, int dp) {

        return (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
