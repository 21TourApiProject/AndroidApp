package com.starrynight.tourapiproject.weatherPage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.starrynight.tourapiproject.R;

import java.util.Calendar;

public class WtDatePickerDialog extends DialogFragment {
    private static final int MAX_HOUR = 23;
    private static final int MIN_HOUR = 00;

    private String hour[] = {"00", "01", "02", "03", "04", "05"
    , "06", "07", "08", "09", "10", "11", "12", "13", "14", "15"
    , "16", "17", "18", "19", "20", "21", "22", "23"};

    private TimePickerDialog.OnTimeSetListener listenerT;

    public Calendar cal = Calendar.getInstance();

    public void setListenerT(TimePickerDialog.OnTimeSetListener listenerT){
        this.listenerT = listenerT;
    }

    private Button btnConfirm;
    private Button btnCancel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.wt_dialog_datepicker, null);

        btnConfirm = dialog.findViewById(R.id.wt_btn_confirm);
        btnCancel = dialog.findViewById(R.id.wt_btn_cancel);

        final NumberPicker hourPicker = (NumberPicker) dialog.findViewById(R.id.hourPicker);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WtDatePickerDialog.this.getDialog().cancel();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerT.onTimeSet(null, hourPicker.getValue(), 0);
                WtDatePickerDialog.this.getDialog().cancel();
            }
        });

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(hour.length - 1);
        hourPicker.setDisplayedValues(hour);
        hourPicker.setValue(cal.get(Calendar.HOUR_OF_DAY));

        builder.setView(dialog);

        return builder.create();
    }


}
