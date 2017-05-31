package takeyourseat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anica.takeyourseat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationTablesFragment extends Fragment {

    private Button next;
    private Button back;


    public ReservationTablesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation_tables, container, false);
        next = (Button) v.findViewById(R.id.next2);
        back = (Button) v.findViewById(R.id.back1);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DateAndTimeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return v;
    }

}
