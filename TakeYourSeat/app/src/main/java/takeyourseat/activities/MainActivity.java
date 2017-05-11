package takeyourseat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.example.anica.takeyourseat.R;

public class MainActivity extends AppCompatActivity {


    private TextView username;
    private TextView pass;
    private Button logIn;
    private TextView registerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView)findViewById(R.id.username);
        pass = (TextView)findViewById(R.id.pass);
        logIn = (Button)findViewById(R.id.signIn);
        registerHere = (TextView)findViewById(R.id.register);

        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homePageIntent = new Intent(MainActivity.this, HomePageActivity.class);
                MainActivity.this.startActivity(homePageIntent);
            }
        });



    }
}
