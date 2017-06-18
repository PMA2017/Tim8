package takeyourseat.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private ArrayList<String> users;
    private ApiService apiService;
    ArrayAdapter<String> adapter;
    private User currentUser;


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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", -1);
        currentUser = new User();
        try {
            currentUser = getDatabaseHelper().getUserDao().queryForAll().get(0);
            //currentUser = getDatabaseHelper().getUserDao().queryForEq("id", id).get(0);
        } catch (Exception e) {
            Log.e("ProfileActivity", e.getMessage());
        }

        users = new ArrayList<>();
        apiService = ApiUtils.getApiService();
        try {
            apiService.getFriends(String.valueOf(id)).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            users.add(response.body().get(i).getName() + " " + response.body().get(i).getLastName());
                            adapter = new ArrayAdapter<String>(getActivity(), R.layout.friend_invite_row,R.id.checkedTextView, users);
                            listFriends.setAdapter(adapter);
                            registerForContextMenu(listFriends);
                        }
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
                    Fragment fragment = new AllReservationDetailsFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame,fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
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
    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
