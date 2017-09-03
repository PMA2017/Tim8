package takeyourseat.fragments;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.anica.takeyourseat.R;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import takeyourseat.activities.ReservationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateAndTimeFragment extends Fragment {

    private EditText dateRes;
    private EditText timeRes;
    private Button dateButton;
    private Button timeButton;
    private Button next1;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private String dateResText,timeResText;
    Calendar calendar = Calendar.getInstance();


    public DateAndTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(container != null ){
            container.removeAllViews();
        }
        View v = inflater.inflate(R.layout.fragment_date_and_time, container, false);
        dateButton = (Button) v.findViewById(R.id.dateButton);
        timeButton = (Button) v.findViewById(R.id.timeButton);
        dateRes = (EditText) v.findViewById(R.id.dateRes);
        timeRes = (EditText) v.findViewById(R.id.timeRes);
        next1 = (Button) v.findViewById(R.id.next1);
        dateRes.setEnabled(false);
        timeRes.setEnabled(false);

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateReservation()) {
                    saveDateAndTime(dateResText, timeResText);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, new ReservationTablesFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });
        return v;
    }

    private boolean validateReservation() {
        initialize();
        return validate();
    }

    private boolean validate() {
        boolean valid = true;
        if(dateResText.isEmpty()) {
            dateRes.setError("Please enter date");
            valid = false;
        }
        if(timeResText.isEmpty()) {
            timeRes.setError("Please enter time");
            valid = false;
        }
        return valid;
    }

    private void initialize() {
        dateResText = dateRes.getText().toString().trim();
        timeResText = timeRes.getText().toString().trim();
    }

    private void updateDate() {
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),R.style.DialogTheme,d,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "CANCEL", dpd);
        dpd.show();
    }

    private void updateTime() {
        TimePickerDialog tpd = new TimePickerDialog(getActivity(),R.style.DialogTheme,t,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
        tpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "CANCEL", tpd);
        tpd.show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateRes.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hour = String.valueOf(hourOfDay);
            String minutes = String.valueOf(minute);

            if(hour.length() == 1)
                hour = "0" + hour;

            if(minutes.length() == 1)
                minutes = "0" + minutes;

            timeRes.setText(hour + ":" + minutes);
        }
    };

    public void saveDateAndTime(String date, String time) {
        this.getActivity().getIntent().putExtra("date", date);
        this.getActivity().getIntent().putExtra("time", time);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", dateResText);
        outState.putString("time", timeResText);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            String date = savedInstanceState.getString("date");
            String time = savedInstanceState.getString("time");
            dateRes.setText(date);
            timeRes.setText(time);
        }
    }
}
