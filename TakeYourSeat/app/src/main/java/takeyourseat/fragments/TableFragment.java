package takeyourseat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.anica.takeyourseat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment {

    private Button b1;
    private EditText nameRes;
    private EditText lastNameRes;
    private EditText date;
    private Button reserve;


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

                builder.setView(mView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}
