package takeyourseat.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import takeyourseat.beans.User;

public class ProfileActivity extends AppCompatActivity {

    public User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        currentUser = new User();
        currentUser.setName("Marko");
        currentUser.setLastName("Markovic");
        currentUser.setAddress("Pastroviceva 8");
        currentUser.setEmail("markomark@gmail.com");

        TextView nameView = (TextView)findViewById(R.id.textViewName);
        TextView addressView = (TextView)findViewById(R.id.textViewAddress);
        TextView emailView = (TextView)findViewById(R.id.textViewEmail);

        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name","nothing");
        String lastName = sharedPreferences.getString("lastName","nothing");
        //upotrebiti pass za promenu passworda
        String pass = sharedPreferences.getString("pass","nothing");
        String email = sharedPreferences.getString("email","nothing");
        String address = sharedPreferences.getString("address","nothing");


        nameView.setText(name + " " + lastName);
        addressView.setText(address);
        emailView.setText(email);
    }

}
