package takeyourseat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.beans.User;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;

public class MainActivity extends AppCompatActivity {



    private EditText email;
    private EditText password;
    private TextView error;
    private TextView showPass;
    private Button logIn;
    private TextView registerHere;

    private ApiService apiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.pass);
        error = (TextView)findViewById(R.id.textViewError);
        showPass = (TextView) findViewById(R.id.showPass);
        logIn = (Button)findViewById(R.id.signIn);
        registerHere = (TextView)findViewById(R.id.register);

        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        showPass.setVisibility(View.GONE);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(password.getText().length() > 0) {
                        showPass.setVisibility(View.VISIBLE);
                    } else {
                        showPass.setVisibility(View.GONE);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showPass.getText() == "SHOW PASSWORD") {
                    showPass.setText("HIDE PASSWORD");
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());

                } else {
                    showPass.setText("SHOW PASSWORD");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());
                }
            }
        });

        apiService = ApiUtils.getApiService();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()) {
                    email.setError("Please enter an email.");
                }
                if(!email.getText().toString().isEmpty() && !isValidEmail(email.getText().toString())) {
                    email.setError("Please enter a valid email.");
                }
                if(password.getText().toString().isEmpty()) {
                    email.setError("Please enter password.");
                }
                if(!password.getText().toString().isEmpty() && password.getText().toString().length() < 6) {
                    password.setError("Password must be at least 6 characters long!");
                }
                if(email != null && password != null) {
                    try {
                        authenticate(email.getText().toString(), password.getText().toString());
                    }
                    catch(Exception ex) {
                        Log.e("Error: ", ex.getMessage());
                    }
                }
            }
        });
    }

    private static boolean isValidEmail(String email) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        else
            return false;
    }

    private void authenticate(String email, final String password) {
        apiService.getUserByEmail(email).enqueue(new Callback<List<User>>() {
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

}
