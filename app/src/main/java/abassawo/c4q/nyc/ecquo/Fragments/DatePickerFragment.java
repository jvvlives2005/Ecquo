package abassawo.c4q.nyc.ecquo.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import abassawo.c4q.nyc.ecquo.Model.sPlanner;
import abassawo.c4q.nyc.ecquo.R;

/**
 * Created by c4q-Abass on 9/10/15.
 */
public class DatePickerFragment extends DialogFragment {
    public static final String EXTRA_DATE = "abassawo.c4q.nyc.ecquo.Fragments.DatePickerFragment";

    private Date mStartDate;

    public static DatePickerFragment newInstance(Date date)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mStartDate = sPlanner.get(getActivity().getApplicationContext()).getTodaysDate();

        // Create a calendar to get year,month,day
        final Calendar cal = Calendar.getInstance();
        cal.setTime( mStartDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);


        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                final Calendar cal = Calendar.getInstance();
                cal.setTime(mStartDate);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                // Translate picked date to Date
                mStartDate = new GregorianCalendar(year, monthOfYear, dayOfMonth, hour, minute).getTime();

                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE,  mStartDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;

        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mStartDate);


        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}
