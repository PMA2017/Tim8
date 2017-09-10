package takeyourseat.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;


public class InvitationFragment extends DialogFragment {

    private ApiService apiService;

    private String reservationId;
    private String friendId;

    public InvitationFragment() {
        // Required empty public constructor
    }

    public static InvitationFragment newInstance(Bundle args) {
        InvitationFragment frag = new InvitationFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        apiService = ApiUtils.getApiService();
        String title = "New invitation";
        String senderFullName = getArguments().getString("senderFullName");
        String restaurantName = getArguments().getString("restaurantName");
        String startDate = getArguments().getString("startDate");
        String endDate = getArguments().getString("endDate");
        reservationId = getArguments().getString("reservationId");
        friendId = getArguments().getString("friendId");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Accept invitation from " + senderFullName + "?\n\nRestaurant: " + restaurantName+"\nStart: " + startDate + "\nEnd: " + endDate);

        alertDialogBuilder.setPositiveButton("YES",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                     updateStatus();
                }
                catch (Exception ex) {
                    Log.e("InvitationFragment", ex.getMessage());
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

    private void updateStatus() {

        apiService.updateInvitationStatus(reservationId, friendId).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                //Toast.makeText(getActivity(), "Invitation accepted.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("Invitation update: ", t.getMessage());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invitation, container, false);
    }


}
