package takeyourseat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import takeyourseat.activities.InviteFriendsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFriendsFragment extends Fragment {

    private EditText searchOthers;
    private ListView listOthers;


    public OtherFriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_other_friends, container, false);
        listOthers = (ListView) v.findViewById(R.id.othersList);
        searchOthers = (EditText) v.findViewById(R.id.searchFriendsOthers);
        String[] items = {"Friend 1","Friend 2","Friend 3","Friend 4","Friend 5","Friend 6","Friend 7","Friend 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_other_row,R.id.othersTextView,items);
        listOthers.setAdapter(adapter);
        registerForContextMenu(listOthers);

        searchOthers.addTextChangedListener(new TextWatcher() {
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.friends_menu_add, menu);
    }
}
