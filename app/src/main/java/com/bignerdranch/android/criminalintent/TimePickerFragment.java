package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by lex on 7/7/18.
 */

public class TimePickerFragment extends android.support.v4.app.DialogFragment {
    private static final String ARG_TIME = "time";

    public static final String EXTRA_TIME="com.bignerdranch.android.criminalintent.time";
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Date date=(Date)getArguments().getSerializable(ARG_TIME);
//        Calendar calendar= Calendar.getInstance();
//        calendar.setTime(date);
//        int hour=calendar.get(Calendar.HOUR);
//        int minute=calendar.get(Calendar.MINUTE);
//        int second=calendar.get(Calendar.SECOND);
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        final TimePicker timePicker=(TimePicker) v.findViewById(R.id.time_picker_crime);
        timePicker.setCurrentHour(date.getHours());
        timePicker.setCurrentMinute(date.getMinutes());
        return new AlertDialog.Builder(getActivity()).setTitle("Time of crime:").setView(v).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int hour=timePicker.getCurrentHour();
                int minute=timePicker.getCurrentMinute();
                date.setHours(hour);
                date.setMinutes(minute);
                sendResult(Activity.RESULT_OK, date);
            }
        }).create();
    }

    public static TimePickerFragment newInstance(Date date) {
        Bundle args=new Bundle();
        args.putSerializable(ARG_TIME, date);
        TimePickerFragment fragment=new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, Date date){
        if(getTargetFragment()==null){
            return;
        }
        Intent intent=new Intent();
        intent.putExtra(EXTRA_TIME, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
