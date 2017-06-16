package takeyourseat.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationTablesFragment extends Fragment {

    private Button next, btn;
    private RelativeLayout relativeLayout;
    private GridLayout gridLayout;
    private ArrayList<Button> tableButtons = new ArrayList<Button>();
    private ArrayList<Button> chosenTables = new ArrayList<Button>();
    private ArrayList<String> allTables = new ArrayList<String>();

    public ReservationTablesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation_tables, container, false);

        next = (Button) v.findViewById(R.id.next2);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReservationOtherDetailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        relativeLayout = (RelativeLayout) v.findViewById(R.id.tableRelative);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("resDetails", Context.MODE_PRIVATE);
        String date = prefs.getString("date", null); //dd-mm-yyy
        String time = prefs.getString("time", null); //hh:mm

        //

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
