package takeyourseat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.anica.takeyourseat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationOtherDetailsFragment extends Fragment {

    private EditText firstNameRes;
    private EditText lastNameRes;
    private Button next;
    private String firstNameText,lastNameText;


    public ReservationOtherDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation_other_details, container, false);
        firstNameRes = (EditText) v.findViewById(R.id.nameRes);
        lastNameRes = (EditText) v.findViewById(R.id.lastNameRes);
        next = (Button) v.findViewById(R.id.next3);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean next = reserve();
                if (next) {
                    Fragment fragment = new InviteFriendsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    //
                }
            }
        });
        return v;
    }

    private boolean reserve() {
        initialize();
        if(!validate()) {
            return false;
        } else {
            return true;
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

        return valid;

    }

    private void initialize() {
        firstNameText = firstNameRes.getText().toString().trim();
        lastNameText = lastNameRes.getText().toString().trim();
    }

}
