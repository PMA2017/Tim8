package takeyourseat.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllReservationDetailsFragment extends Fragment {

    private TextView date;
    private TextView time;
    private TextView resName;
    private String resNameText;


    public AllReservationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            resNameText = bundle.getString("resName");
        }


        View v = inflater.inflate(R.layout.fragment_all_reservation_details, container, false);
        date = (TextView) v.findViewById(R.id.resDateText);
        time = (TextView) v.findViewById(R.id.resTimeText);
        resName = (TextView) v.findViewById(R.id.resNameText);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("resDetails", Context.MODE_PRIVATE);
        String dateRes = sharedPreferences.getString("date","nothing");
        String timeRes = sharedPreferences.getString("time","nothing");
        date.setText(dateRes);
        time.setText(timeRes);
        resName.setText(resNameText);
        return v;
    }

}
