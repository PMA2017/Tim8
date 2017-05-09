package takeyourseat.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.anica.takeyourseat.R;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private Button b1;
    private EditText nameRes;
    private EditText lastNameRes;
    private EditText date;
    private Button reserve;
    private EditText dateRes;
    private EditText timeRes;
    Calendar calendar = Calendar.getInstance();
    DateFormat format = DateFormat.getDateTimeInstance();


    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_table, container, false);
        b1 = (Button) v.findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_reservation,null);
                nameRes = (EditText) mView.findViewById(R.id.nameRes);
                lastNameRes = (EditText) mView.findViewById(R.id.lastNameRes);
                reserve = (Button) mView.findViewById(R.id.reserve);
                dateRes = (EditText) mView.findViewById(R.id.dateRes);
                timeRes = (EditText) mView.findViewById(R.id.timeRes);

                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();

                dateRes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDate();
                    }
                });

                timeRes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateTime();
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void updateDate() {
        new DatePickerDialog(getActivity(),d,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime() {
        new TimePickerDialog(getActivity(),t,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true).show();
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
