package takeyourseat.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anica.takeyourseat.R;

import takeyourseat.fragments.FriendRequestFragment;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Bundle args = new Bundle();

        String userId = getIntent().getExtras().getString("userId");
        String friendId = getIntent().getExtras().getString("friendId");
        String userName = getIntent().getExtras().getString("userName");
        String userLastName = getIntent().getExtras().getString("userLastName");

        args.putString("userId", userId);
        args.putString("friendId", friendId);
        args.putString("userName", userName);
        args.putString("userLastName", userLastName);

        showCustomDialog(args);
    }

    private void showCustomDialog(Bundle args) {
        FragmentManager fm = getSupportFragmentManager();
        FriendRequestFragment alertDialog = FriendRequestFragment.newInstance(args);
        alertDialog.show(fm, "fragment_alert");
    }
}
