package takeyourseat.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
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

import takeyourseat.adapters.Communicator;
import takeyourseat.beans.Reservation;


public class ReservationActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        ViewPager vp = (ViewPager) findViewById(R.id.viewPagerFragment);
        Communicator com = new Communicator(getSupportFragmentManager());
        vp.setAdapter(com);
        vp.setCurrentItem(0);

    }
}




