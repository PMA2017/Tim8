package takeyourseat.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;


import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import java.util.Calendar;

import takeyourseat.beans.Reservation;


public class ReservationActivity extends AppCompatActivity  {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        firstNameRes = (EditText) findViewById(R.id.nameRes);
        lastNameRes = (EditText) findViewById(R.id.lastNameRes);
        reserve = (Button) findViewById(R.id.reserve);
        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        inviteButton = (Button) findViewById(R.id.inviteButton);
        cancel = (Button) findViewById(R.id.cancel);
        dateRes = (EditText) findViewById(R.id.dateRes);
        timeRes = (EditText) findViewById(R.id.timeRes);

        dateRes.setEnabled(false);
        timeRes.setEnabled(false);

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
                finish();
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
                Intent friendsView = new Intent(ReservationActivity.this, InviteFriendsActivity.class);
                startActivity(friendsView);
            }
        });



    }

    private void reserve() {
        initialize();
        if(!validate()) {
            Toast.makeText(ReservationActivity.this,"Reservation failed",Toast.LENGTH_SHORT).show();
        } else {
            // ovde treba da se doda nova rezervacija sa svim poljima
            Toast.makeText(ReservationActivity.this,"Reservation successful",Toast.LENGTH_SHORT).show();
            finish();
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
        new DatePickerDialog(ReservationActivity.this,d,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime() {
        new TimePickerDialog(ReservationActivity.this,t,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true).show();
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




