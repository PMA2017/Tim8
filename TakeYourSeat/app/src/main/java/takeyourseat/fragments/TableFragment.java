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


import java.util.ArrayList;
import java.util.List;

import takeyourseat.activities.ReservationActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private Button next, btn;
    private RelativeLayout relativeLayout;
    private GridLayout gridLayout;
    private ArrayList<Button> tableButtons = new ArrayList<Button>();
    private ArrayList<Button> chosenTables = new ArrayList<Button>();

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
                btn = (Button) view;
                if (btn.getText().toString().toLowerCase().contains("table")) {
                    tableButtons.add(btn);
                    btn.setBackgroundColor(Color.parseColor("#8D6E63"));
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Button clickedBtn = (Button)v;
                            if(!ifExists(clickedBtn)) {
                                clickedBtn.setBackgroundColor(Color.parseColor("#58D68D"));
                                chosenTables.add(clickedBtn);
                            }
                            else {
                                clickedBtn.setBackgroundColor(Color.parseColor("#8D6E63"));
                                chosenTables.remove(clickedBtn);
                            }
                        }
                    });
                }
            }
        }

        next = (Button) v.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ovde sasd treba preneti stolove koji su odabrani dalje u rezervaciju
                Intent reservation = new Intent(getActivity(), ReservationActivity.class);
                startActivity(reservation);
            }
        });

        return v;
    }

    private boolean ifExists(Button btn) {
        boolean ifExists = false;

        for (int i = 0; i < chosenTables.size(); i++) {
            if (btn.getId() == chosenTables.get(i).getId()) {
                ifExists = true;
                break;
            }
        }

        return ifExists;
    }
}

