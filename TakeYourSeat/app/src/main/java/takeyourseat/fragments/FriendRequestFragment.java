package takeyourseat.fragments;



import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.activities.EditPasswordActivity;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendRequestFragment extends DialogFragment {

    private ApiService apiService;

    private String userId;
    private String friendId;

    public FriendRequestFragment() {
        // Required empty public constructor
    }

    public static FriendRequestFragment newInstance(Bundle args) {
        FriendRequestFragment frag = new FriendRequestFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        apiService = ApiUtils.getApiService();
        String title = "Friend request";
        String userName = getArguments().getString("userName");
        String userLastName = getArguments().getString("userLastName");
        userId = getArguments().getString("userId");
        friendId = getArguments().getString("friendId");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Accept " + userLastName + " " + userLastName + " " + "as a friend?");

        alertDialogBuilder.setPositiveButton("YES",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    createFriendship();
                }
                catch (Exception ex) {
                    Log.e("FriendRequestFragment", ex.getMessage());
                }
            }
        });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });

        return alertDialogBuilder.create();

    }

    private void createFriendship() {
        apiService.addFriendship(userId, friendId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()) {
                    int statusCode = response.code();
                    Log.e("MainActivity", "Response not successful. Status code: " + statusCode);
                    Toast.makeText(getContext(), "Error in approving friend request.", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(), "Friend request approved.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("FriendRequestFragment", "error loading from API");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_request, container, false);
    }

}
