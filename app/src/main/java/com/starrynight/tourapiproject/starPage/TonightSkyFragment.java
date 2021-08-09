package com.starrynight.tourapiproject.starPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.starrynight.tourapiproject.MainActivity;
import com.starrynight.tourapiproject.R;

public class TonightSkyFragment extends Fragment {

    //    private BottomAppBar bottomAppBar;
    private LinearLayout bottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;

    private EditText editText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tonight_sky, container, false);

        // bottomSheet 설정
        editText = v.findViewById(R.id.edit_search);
        editText.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        editText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        bottomSheetBehavior.setPeekHeight(editText.getBottom()+ 50);
                    }
                }
        );

        bottomSheet = v.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setPeekHeight(editText.getBottom());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        ImageButton backBtn =(ImageButton) v.findViewById(R.id.star_bacK_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}