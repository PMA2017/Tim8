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

    private EditText descriptionEdit;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String description, email, phone, website;

        if (savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            if(extras == null) {
                description = null;
                email = null;
                phone = null;
                website = null;
            } else {
                description= getArguments().getString("description");
                email = getArguments().getString("email");
                phone = getArguments().getString("phone");
                website = getArguments().getString("website");
            }
        } else {
            description = (String) savedInstanceState.getSerializable("description");
            email = (String) savedInstanceState.getSerializable("email");
            phone = (String) savedInstanceState.getSerializable("phone");
            website = (String) savedInstanceState.getSerializable("website");
        }
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_about, container, false);
        descriptionEdit = (EditText) v.findViewById(R.id.description);
        descriptionEdit.setText(description + "\nEmail:" + email + "\nPhone:" + phone + "\nWebsite:" + website);
        descriptionEdit.setFocusable(false);
        descriptionEdit.setClickable(false);
        return v;
    }

}
