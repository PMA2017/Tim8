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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsListFragment extends Fragment {


    private EditText searchFriends;
    private ListView listMyFriends;
    private AlertDialog dialog;
    private boolean showRemoveDialog;
    private ArrayList<String> users;
    private ApiService apiService;
    ArrayAdapter<String> adapter;
    private AlertDialog.Builder alert;
    private List<User> userList;
    private User clickedUser;
    private int userId;


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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", -1);


        users = new ArrayList<>();
        userList = new ArrayList<>();
        apiService = ApiUtils.getApiService();
        try {
            apiService.getFriends(String.valueOf(userId)).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            users.add(response.body().get(i).getName() + " " + response.body().get(i).getLastName());
                            adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_friend_row,R.id.friendsTextView, users);
                            listMyFriends.setAdapter(adapter);
                            registerForContextMenu(listMyFriends);
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

        listMyFriends.setLongClickable(true);
        listMyFriends.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position,  long id) {
                //Do your tasks here
                clickedUser = userList.get(position);
                alert = new AlertDialog.Builder(
                        getActivity());
                alert.setTitle("Remove friend!!");
                alert.setMessage("Are you sure to remove this friend?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        apiService.deleteFriendship(Integer.toString(userId), Integer.toString(clickedUser.getId())).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.isSuccessful()) {
                                    if (!response.body()) {
                                        Toast.makeText(getActivity(), "Failed to delete", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Deleted: ", Toast.LENGTH_LONG).show();
                                        users.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                else {
                                    Log.e("Delete", "Error in response: " + response.code());
                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Log.e("Delete", "Error loading from API");
                            }
                        });


                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                showRemoveDialog = true;
                alert.show();

                return true;
            }
        });

       searchFriends.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                FriendsListFragment.this.adapter.getFilter().filter(s);
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
            alert.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("search",searchFriends.getText().toString());
        if(alert != null) {
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
