package com.starrynight.tourapiproject.observationPage;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starrynight.tourapiproject.R;
import com.starrynight.tourapiproject.observationPage.observationPageRetrofit.CourseTouristPoint;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
* @className : ObserveCourseViewAdapter.java
* @description : 관측지 코스 어댑터
* @modification : gyul chyoung (2022-08-30) 주석추가
* @author : 2022-08-30
* @date : gyul chyoung
* @version : 1.0
     ====개정이력(Modification Information)====
  수정일        수정자        수정내용    -----------------------------------------
   gyul chyoung       2022-08-30       주석추가
 */

public class ObserveCourseViewAdapter extends RecyclerView.Adapter<ObserveCourseViewAdapter.MyViewHolder> {

    private static final String TAG = "course adapter";
    private Context context;
    private List<CourseTouristPoint> touristPointList;

    public ObserveCourseViewAdapter(Context context, List<CourseTouristPoint> touristPointList) {
        this.context = context;
        this.touristPointList = touristPointList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.observation__course_slider, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ObserveCourseViewAdapter.MyViewHolder holder, int position) {
        CourseTouristPoint courseTouristPoint = touristPointList.get(position);
        if (courseTouristPoint.getFirstImage() != null)
            holder.bindImage(courseTouristPoint.getFirstImage());
        holder.bindText(courseTouristPoint);
        holder.setOutlineButton(position);
    }

    @Override
    public int getItemCount() {
        return touristPointList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView tp_img;
        private TextView name_txt;
        private TextView tp_type_txt;
        private TextView overview_txt;
        private TextView address_txt;
        private TextView operating_txt;
        private TextView parking_txt;
        private TextView menu_txt;
        private LinearLayout menu_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tp_img = itemView.findViewById(R.id.course_tp_img);
            name_txt = itemView.findViewById(R.id.course_tpname_txt);
            tp_type_txt = itemView.findViewById(R.id.course_tptype_txt);
            overview_txt = itemView.findViewById(R.id.course_outline_txt);
            address_txt = itemView.findViewById(R.id.course_tpaddress_txt);
            operating_txt = itemView.findViewById(R.id.course_tpoperating_txt);
            parking_txt = itemView.findViewById(R.id.course_tpparking_txt);
            menu_name = itemView.findViewById(R.id.course_tpmenu_name);
            menu_txt = itemView.findViewById(R.id.course_tpmenu_txt);

        }

        private void setOutlineButton(int position) {
            TextView outline_btn = itemView.findViewById(R.id.course_outline_btn);

            ViewTreeObserver vto = overview_txt.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Layout l = overview_txt.getLayout();
                    if (l != null) {
                        int lines = l.getLineCount();
                        if (lines > 0)
                            if (l.getEllipsisCount(lines - 1) > 0) {
                                outline_btn.setVisibility(View.VISIBLE);
                                Log.d(TAG, "텍스트 줄넘침");
                            }
                    }
                }
            });

            //개요 더보기 버튼 클릭시 팝업띄움
            outline_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), OutlinePopActivity.class);
                    intent.putExtra("outline", touristPointList.get(position).getOverview());
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void bindImage(String imageURL) {
            Glide.with(context)
                    .load(imageURL)
                    .into(tp_img);

        }

        public void bindText(CourseTouristPoint courseTouristPoint) {
            name_txt.setText(courseTouristPoint.getTitle());
            tp_type_txt.setText(courseTouristPoint.getCat3Name());
            overview_txt.setText(courseTouristPoint.getOverview());
            address_txt.setText(courseTouristPoint.getAddr());
            operating_txt.setText(courseTouristPoint.getUseTime());
            parking_txt.setText(courseTouristPoint.getParking());
            if (courseTouristPoint.getContentTypeId() == 39) {
                menu_txt.setVisibility(View.VISIBLE);
                menu_txt.setText(courseTouristPoint.getTreatMenu());
                menu_name.setVisibility(View.VISIBLE);
            }
        }
    }


}
