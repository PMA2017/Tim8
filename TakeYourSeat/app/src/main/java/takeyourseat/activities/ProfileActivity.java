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
        currentUser.setName("Marko Markovic");
        currentUser.setAddress("Pastroviceva 8");
        currentUser.setEmail("markomark@gmail.com");

        TextView nameView = (TextView)findViewById(R.id.textViewName);
        TextView addressView = (TextView)findViewById(R.id.textViewAddress);
        TextView emailView = (TextView)findViewById(R.id.textViewEmail);

        nameView.setText(currentUser.getName());
        addressView.setText(currentUser.getAddress());
        emailView.setText(currentUser.getEmail());
    }

}
