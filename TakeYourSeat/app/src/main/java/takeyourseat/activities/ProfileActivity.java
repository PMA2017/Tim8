package takeyourseat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import takeyourseat.models.User;

public class ProfileActivity extends AppCompatActivity {

    public User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUser = new User();
        currentUser.setFirstName("Ime");
        currentUser.setLastName("Prezime");

        TextView firstNameView = (TextView)findViewById(R.id.textViewFirstName);
        TextView lastNameView = (TextView)findViewById(R.id.textViewLastName);

        firstNameView.setText(currentUser.getFirstName());
        lastNameView.setText(currentUser.getLastName());

    }

}
