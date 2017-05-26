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

import takeyourseat.activities.InviteFriendsActivity;
import takeyourseat.activities.ReservationActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private Button b1;

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
                //AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                Intent reservation = new Intent(getActivity(), ReservationActivity.class);
                startActivity(reservation);


                //builder.setView(mView);
                //dialog = builder.create();
                //dialog.show();




            }
        });
        // Inflate the layout for this fragment
        return v;
    }






}

