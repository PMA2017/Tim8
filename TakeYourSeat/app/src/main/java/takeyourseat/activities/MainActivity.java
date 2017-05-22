package takeyourseat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.example.anica.takeyourseat.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.beans.User;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;

public class MainActivity extends AppCompatActivity {


    private TextView username;
    private TextView pass;
    private TextView error;
    private Button logIn;
    private TextView registerHere;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView)findViewById(R.id.username);
        pass = (TextView)findViewById(R.id.pass);
        error = (TextView)findViewById(R.id.textViewError);
        logIn = (Button)findViewById(R.id.signIn);
        registerHere = (TextView)findViewById(R.id.register);

        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        apiService = ApiUtils.getApiService();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username != null && pass != null) {
                    try {
                        authenticate(username.getText().toString(), pass.getText().toString());
                    }
                    catch(Exception ex) {
                        Log.e("Error: ", ex.getMessage());
                    }
                }
            }
        });
    }

    private void authenticate(String username, final String password) {
        apiService.getUserByEmail(username).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    if(response.body().size() == 1 && password.equals(response.body().get(0).getPassword())) {
                        Intent homePageIntent = new Intent(MainActivity.this, HomePageActivity.class);
                        MainActivity.this.startActivity(homePageIntent);
                        error.setText("");
                    }
                    else {
                        error.setText("Incorrect username and/or password.");
                    }
                }
                else {
                    int statusCode = response.code();
                    Log.e("MainActivity", "Response not successful. Status code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("MainActivity", "error loading from API");
            }
        });
    }
}
