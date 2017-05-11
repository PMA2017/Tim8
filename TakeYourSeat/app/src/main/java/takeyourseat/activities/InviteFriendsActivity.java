package takeyourseat.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.anica.takeyourseat.R;


public class InviteFriendsActivity extends AppCompatActivity {


    ListView listFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        listFriends = (ListView) findViewById(R.id.listFriends);
        listFriends.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] items = {"Friend 1","Friend 2","Friend 3","Friend 4","Friend 5","Friend 6","Friend 7","Friend 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.friend_invite_row,R.id.checkedTextView, items);
        listFriends.setAdapter(adapter);
    }
}

