package takeyourseat.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anica.takeyourseat.R;

import takeyourseat.fragments.FriendRequestFragment;
import takeyourseat.fragments.InvitationFragment;

public class InviteDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_dialog);

        Bundle args = new Bundle();

        String senderFullName = getIntent().getExtras().getString("senderFullName");
        String restaurantName = getIntent().getExtras().getString("restaurantName");
        String startDate = getIntent().getExtras().getString("startDate");
        String endDate = getIntent().getExtras().getString("endDate");
        String reservationId = getIntent().getExtras().getString("reservationId");
        String friendId = getIntent().getExtras().getString("friendId");

        args.putString("senderFullName", senderFullName);
        args.putString("restaurantName", restaurantName);
        args.putString("startDate", startDate);
        args.putString("endDate", endDate);
        args.putString("reservationId", reservationId);
        args.putString("friendId", friendId);

        showCustomDialog(args);
    }

    private void showCustomDialog(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        InvitationFragment alertDialog = InvitationFragment.newInstance(args);
        alertDialog.show(fm, "fragment_alert");
    }
}
