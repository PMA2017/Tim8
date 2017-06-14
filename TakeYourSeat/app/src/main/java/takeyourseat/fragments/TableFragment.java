package takeyourseat.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anica.takeyourseat.R;


import takeyourseat.activities.ReservationActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private Button next;


    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_table, container, false);
        next = (Button) v.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reservation = new Intent(getActivity(), ReservationActivity.class);
                startActivity(reservation);
            }
        });
        return v;
    }
}

