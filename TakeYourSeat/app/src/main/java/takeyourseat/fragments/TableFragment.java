package takeyourseat.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.anica.takeyourseat.R;


import java.util.Calendar;

import takeyourseat.activities.DetailActivity;
import takeyourseat.activities.FriendsActivity;
import takeyourseat.activities.HomePageActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private Button b1;
    private EditText firstNameRes;
    private EditText lastNameRes;
    private EditText dateRes;
    private EditText timeRes;
    private Button reserve;
    private Button cancel;
    private Button dateButton;
    private Button timeButton;
    private Button inviteButton;

    private String firstNameText,lastNameText,dateResText,timeResText;
    Calendar calendar = Calendar.getInstance();
    AlertDialog dialog;


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
                firstNameRes = (EditText) mView.findViewById(R.id.nameRes);
                lastNameRes = (EditText) mView.findViewById(R.id.lastNameRes);
                reserve = (Button) mView.findViewById(R.id.reserve);
                dateButton = (Button) mView.findViewById(R.id.dateButton);
                timeButton = (Button) mView.findViewById(R.id.timeButton);
                inviteButton = (Button) mView.findViewById(R.id.inviteButton);
                cancel = (Button) mView.findViewById(R.id.cancel);
                dateRes = (EditText) mView.findViewById(R.id.dateRes);
                timeRes = (EditText) mView.findViewById(R.id.timeRes);

                dateRes.setEnabled(false);
                timeRes.setEnabled(false);

                builder.setView(mView);
                dialog = builder.create();
                dialog.show();

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

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                reserve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reserve();
                    }
                });

                inviteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent friendsView = new Intent(getContext(), FriendsActivity.class);
                        startActivity(friendsView);
                    }
                });


            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    private void reserve() {
        initialize();
        if(!validate()) {
            Toast.makeText(getActivity(),"Reservation failed",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"Reservation successful",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    private boolean validate() {
        boolean valid = true;
        if(firstNameText.isEmpty() || firstNameText.length() > 32) {
            firstNameRes.setError("Please enter valid name");
            valid = false;
        }
        if(lastNameText.isEmpty() || lastNameText.length() > 32) {
            lastNameRes.setError("Please enter valid last name");
            valid = false;
        }
        if(dateResText.isEmpty()) {
            dateRes.setError("Please enter valid date");
            valid = false;
        }
        if(timeResText.isEmpty() ) {
            timeRes.setError("Please enter valid time");
            valid = false;
        }

        return valid;

    }

    private void initialize() {
        firstNameText = firstNameRes.getText().toString().trim();
        lastNameText = lastNameRes.getText().toString().trim();
        dateResText = dateRes.getText().toString().trim();
        timeResText = timeRes.getText().toString().trim();
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
