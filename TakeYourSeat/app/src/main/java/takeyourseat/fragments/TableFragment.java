package takeyourseat.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.example.anica.takeyourseat.R;


import java.util.List;

import takeyourseat.activities.ReservationActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private Button next;
    private RelativeLayout relativeLayout;
    private GridLayout gridLayout;
    private List<Button> tableButtons;

    public TableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_table, container, false);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.tableRelative);

        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            View view = relativeLayout.getChildAt(i);
            if (view instanceof GridLayout) {
                gridLayout = (GridLayout) view;
                break;
            }
        }

        for (int j = 0; j < gridLayout.getChildCount(); j++) {
            View view = gridLayout.getChildAt(j);
            if (view instanceof Button) {
                Button btn = (Button) view;
                if (btn.getText().toString().toLowerCase().contains("table")) {
                    btn.setBackgroundColor(Color.parseColor("#6D4C41"));
                    tableButtons.add(btn);
                }
            }
        }

        //na klik na dugme promeni mu se boja
        //taj sto ili stolovi na koje je kliknuto se salju sledecem delu

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

