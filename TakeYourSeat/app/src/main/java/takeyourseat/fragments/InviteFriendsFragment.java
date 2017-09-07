package takeyourseat.fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.db.DatabaseHelper;
import takeyourseat.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class  InviteFriendsFragment extends Fragment {

    private ListView listFriends;
    private EditText searchFriendsInvite;
    private Button next;
    private DatabaseHelper databaseHelper;
    private ArrayList<User> friends;
    private ArrayList<User> invitedFriends = new ArrayList<User>();
    private ApiService apiService;
    ArrayAdapter<User> adapter;
    private User currentUser;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();

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
        next = (Button) v.findViewById(R.id.next4);
        searchFriendsInvite = (EditText) v.findViewById(R.id.searchFriendsInvite);
        listFriends.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        currentUser = getDatabaseHelper().getCurrentUser();

        friends = new ArrayList<>();
        apiService = ApiUtils.getApiService();

        try {
            apiService.getFriends(String.valueOf(currentUser.getId())).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            friends.add(response.body().get(i));
                        }
                        adapter = new ArrayAdapter<>(getActivity(), R.layout.friend_invite_row, R.id.checkedTextView, friends);
                        listFriends.setAdapter(adapter);
                        registerForContextMenu(listFriends);
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.e("ListFriends", "error loading from API");
                }
            });
        }
        catch (Exception ex) {
            Log.e("ListFriends", ex.getMessage());
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arguments = getArguments();

                arguments.putStringArray("friendNames", getNames());
                arguments.putIntArray("friendIds", getIds());

                AllReservationDetailsFragment fragment = new AllReservationDetailsFragment();
                fragment.setArguments(arguments);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        listFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) parent.getItemAtPosition(position);

                if(ifExistsInInvitedFriendsList(user.getId()))
                    invitedFriends.remove(user);
                else
                    invitedFriends.add(user);
            }
        });

        searchFriendsInvite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                InviteFriendsFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private boolean ifExistsInInvitedFriendsList(int id) {
        boolean exists = false;

        for(int i = 0; i < invitedFriends.size(); i++)
            if(id == invitedFriends.get(i).getId()) {
                exists = true;
                break;
            }

        return exists;
    }

    private String[] getNames() {
        String[] names = new String[invitedFriends.size()];

        for(int i = 0; i < invitedFriends.size(); i++) {
            names[i] = invitedFriends.get(i).getName() + " " + invitedFriends.get(i).getLastName();
        }

        return names;
    }

    private int[] getIds() {
        int[] ids = new int[invitedFriends.size()];

        for(int i = 0; i < invitedFriends.size(); i++) {
            ids[i] = invitedFriends.get(i).getId();
        }

        return ids;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
