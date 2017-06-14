package takeyourseat.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import java.util.List;

import takeyourseat.activities.FriendsListsActivity;
import takeyourseat.activities.HomePageActivity;
import takeyourseat.activities.ReservationListActivity;
import takeyourseat.dialogs.DeleteDialog;
import takeyourseat.dialogs.RemoveFriendsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsListFragment extends Fragment {


    private EditText searchFriends;
    private ListView listMyFriends;
    private AlertDialog dialog;
    private boolean showRemoveDialog;


    public FriendsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            showRemoveDialog = savedInstanceState.getBoolean("showRemoveDialog");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frView = inflater.inflate(R.layout.fragment_friends_list, container, false);
        listMyFriends = (ListView) frView.findViewById(R.id.listMyFriends);
        searchFriends = (EditText) frView.findViewById(R.id.searchFriends);
        String[] items = {"Friend 1","Friend 2","Friend 3","Friend 4","Friend 5","Friend 6","Friend 7","Friend 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_friend_row,R.id.friendsTextView, items);
        listMyFriends.setAdapter(adapter);
        registerForContextMenu(listMyFriends);

       searchFriends.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //search
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return frView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (showRemoveDialog) {
            showRemoveFriendsDialog();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.friends_menu_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete: {
                showRemoveFriendsDialog();
                // delete from friends list
            }
        }
        return super.onContextItemSelected(item);
    }


    private void showRemoveFriendsDialog(){
        dialog = new RemoveFriendsDialog(getActivity()).prepareDialog();
        showRemoveDialog = true;
        dialog.show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("search",searchFriends.getText().toString());
        if(dialog != null && dialog.isShowing()) {
            outState.putBoolean("showRemoveDialog",showRemoveDialog);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String search = savedInstanceState.getString("search");
            searchFriends.setText(search);
        }
    }

}
