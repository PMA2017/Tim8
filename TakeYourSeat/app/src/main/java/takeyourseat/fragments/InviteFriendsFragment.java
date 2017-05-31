package takeyourseat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteFriendsFragment extends Fragment {

    private ListView listFriends;
    private EditText searchFriendsInvite;


    public InviteFriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_invite_friends, container, false);
        listFriends = (ListView) v.findViewById(R.id.listFriends);
        searchFriendsInvite = (EditText) v.findViewById(R.id.searchFriendsInvite);
        listFriends.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] items = {"Friend 1","Friend 2","Friend 3","Friend 4","Friend 5","Friend 6","Friend 7","Friend 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.friend_invite_row,R.id.checkedTextView, items);
        listFriends.setAdapter(adapter);

        searchFriendsInvite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(getActivity(),"Searching",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

}
