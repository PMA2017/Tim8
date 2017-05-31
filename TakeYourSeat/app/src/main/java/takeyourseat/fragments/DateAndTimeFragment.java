package takeyourseat.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.anica.takeyourseat.R;

import java.util.Calendar;

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

                Fragment fragment = new ReservationTablesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
            dateRes.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            timeRes.setText(hourOfDay + ":" + minute);
        }
    };

}
