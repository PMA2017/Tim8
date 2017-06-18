package takeyourseat.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class OtherFriendsFragment extends Fragment {

    private EditText searchOthers;
    private ListView listOthers;
    private ArrayList<String> users;
    private ApiService apiService;
    ArrayAdapter<String> adapter;
    private User currentUser;
    private User clickedUser;
    private List<User> userList;
    private AlertDialog.Builder alert;
    private DatabaseHelper databaseHelper;

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
            apiService.getNonFriends(String.valueOf(id)).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            users.add(response.body().get(i).getName() + " " + response.body().get(i).getLastName());
                            adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_other_row,R.id.othersTextView,users);
                            listOthers.setAdapter(adapter);
                        }
                        userList = response.body();
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

        listOthers.setLongClickable(true);
        listOthers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position,  long id) {
                //Do your tasks here
                clickedUser = userList.get(position);
                alert = new AlertDialog.Builder(
                        getActivity());
                alert.setTitle("Send friend request");
                alert.setMessage("Are you sure to send friend request to " + clickedUser.getName() + " " + clickedUser.getLastName() + "?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        int test = currentUser.getId();
                        try {
                            sendFriendRequest(clickedUser.getId());
                        }
                        catch (Exception ex) {
                            Log.e("OtherFriendsFragment", ex.getMessage());
                        }

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });

        searchOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                OtherFriendsFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("search",searchOthers.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String search = savedInstanceState.getString("search");
            searchOthers.setText(search);
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void sendFriendRequest(int friendId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("id", -1);
        apiService.sendFriendRequest(Integer.toString(userId), Integer.toString(friendId)).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()) {
                    int statusCode = response.code();
                    Log.e("OtherFriendsActivity", "Response not successful. Status code: " + statusCode);
                    Toast.makeText(getContext(), "Error in sending friend request.", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(), "Friend request sent.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("FriendRequestFragment", "error loading from API");
            }
        });
    }
}
