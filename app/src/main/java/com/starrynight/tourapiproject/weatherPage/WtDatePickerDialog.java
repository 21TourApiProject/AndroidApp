package com.starrynight.tourapiproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.sql.Time;
import java.util.Calendar;

public class WtDatePickerDialog extends DialogFragment {
    private static final int MAX_MONTH = 12;
    private static final int MIN_MONTH = 1;

    private static final int MAX_DAY = 31;
    private static final int MIN_DAY = 1;

    private static final int MAX_HOUR = 12;
    private static final int MIN_HOUR = 1;

    private DatePickerDialog.OnDateSetListener listener;
    private TimePickerDialog.OnTimeSetListener listenerT;
    public Calendar cal = Calendar.getInstance();

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


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

        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.monthPicker);
        final NumberPicker dayPicker = (NumberPicker) dialog.findViewById(R.id.dayPicker);
        final NumberPicker hourPicker = (NumberPicker)dialog.findViewById(R.id.hourPicker);
        final NumberPicker amPicker = (NumberPicker)dialog.findViewById(R.id.amPicker);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WtDatePickerDialog.this.getDialog().cancel();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDateSet(null, 0, monthPicker.getValue(), dayPicker.getValue());
                listenerT.onTimeSet(null, hourPicker.getValue(), 0);
                WtDatePickerDialog.this.getDialog().cancel();
            }
        });

        monthPicker.setMinValue(MIN_MONTH);
        monthPicker.setMaxValue(MAX_MONTH);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        dayPicker.setMinValue(MIN_DAY);
        dayPicker.setMaxValue(MAX_DAY);
        dayPicker.setValue(cal.get(Calendar.DAY_OF_MONTH));

        hourPicker.setMinValue(MIN_HOUR);
        hourPicker.setMaxValue(MAX_HOUR);
        hourPicker.setValue(cal.get(Calendar.HOUR_OF_DAY));

        amPicker.setValue(cal.get(Calendar.AM_PM));

        builder.setView(dialog);

        return builder.create();
    }


}
