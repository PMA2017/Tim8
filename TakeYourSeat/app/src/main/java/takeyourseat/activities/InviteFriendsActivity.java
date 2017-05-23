package takeyourseat.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;


public class InviteFriendsActivity extends AppCompatActivity {


    private ListView listFriends;
    private EditText searchFriendsInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        listFriends = (ListView) findViewById(R.id.listFriends);
        searchFriendsInvite = (EditText) findViewById(R.id.searchFriendsInvite);
        listFriends.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String[] items = {"Friend 1","Friend 2","Friend 3","Friend 4","Friend 5","Friend 6","Friend 7","Friend 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.friend_invite_row,R.id.checkedTextView, items);
        listFriends.setAdapter(adapter);

        searchFriendsInvite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(InviteFriendsActivity.this,"Searching",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

