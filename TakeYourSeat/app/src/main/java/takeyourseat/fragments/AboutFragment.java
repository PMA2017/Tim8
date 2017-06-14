package takeyourseat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.anica.takeyourseat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private EditText description;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String restaurantDesc;
        String restaurantEmail;
        String phone;
        String website;
        if (savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            if(extras == null) {
                restaurantDesc= null;
                restaurantEmail = null;
                phone = null;
                website = null;
            } else {
                restaurantDesc= extras.getString("description");
                restaurantEmail = extras.getString("email");
                phone = extras.getString("phone");
                website = extras.getString("website");
            }
        } else {
            restaurantDesc= (String) savedInstanceState.getSerializable("description");
            restaurantEmail= (String) savedInstanceState.getSerializable("email");
            phone= (String) savedInstanceState.getSerializable("phone");
            website= (String) savedInstanceState.getSerializable("website");
        }
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_about, container, false);
        description = (EditText) v.findViewById(R.id.description);
        description.setText(restaurantDesc + "\nEmail:" + restaurantEmail + "\nPhone:" + phone + "\nWebsite:" + website);
        description.setFocusable(false);
        description.setClickable(false);
        return v;
    }

}
