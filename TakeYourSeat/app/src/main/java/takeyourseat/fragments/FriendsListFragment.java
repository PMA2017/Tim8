package takeyourseat.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsListFragment extends Fragment {


    ListView listMyFriends;

    public FriendsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frView = inflater.inflate(R.layout.fragment_friends_list, container, false);
        listMyFriends = (ListView) frView.findViewById(R.id.listMyFriends);
        String[] items = {"Friend 1","Friend 2","Friend 3","Friend 4","Friend 5","Friend 6","Friend 7","Friend 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_friend_row,R.id.friendsTextView, items);
        listMyFriends.setAdapter(adapter);
        return frView;
    }
}
