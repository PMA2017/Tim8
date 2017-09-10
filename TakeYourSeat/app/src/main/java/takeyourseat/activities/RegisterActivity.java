package takeyourseat.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.model.User;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import static com.example.anica.takeyourseat.R.*;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText confirmPass;
    private EditText email;
    private EditText address;
    private Button signUp;
    private String firstNameText, lastNameText, passwordText, confirmPassText, emailText, addressText;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_register);

        firstName = (EditText) findViewById(R.id.firstNameReg);
        lastName = (EditText) findViewById(R.id.lastNameReg);
        password = (EditText) findViewById(R.id.passReg);
        confirmPass = (EditText) findViewById(R.id.rePassReg);
        address = (EditText) findViewById(R.id.addressReg);
        email = (EditText) findViewById(R.id.emailReg);
        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        apiService = ApiUtils.getApiService();
    }

    private void register() {
        initialize();
        if(!validate())
            Toast.makeText(this,"Sign up failed!",Toast.LENGTH_SHORT).show();
        else
            registerUser(firstNameText, lastNameText,passwordText, emailText, addressText);
    }

    private boolean validate() {
        boolean isValid = true;

        if(firstNameText.isEmpty()) {
            firstName.setError("Please enter a name.");
            isValid = false;
        }

        if(lastNameText.isEmpty()) {
            lastName.setError("Please enter a last name.");
            isValid = false;
        }

        if(passwordText.isEmpty()) {
            password.setError("Please enter a password.");
            isValid = false;
        }

        if(!passwordText.isEmpty() && passwordText.length() < 6) {
            password.setError("Password must be at least 6 characters long!");
            isValid = false;
        }

        if(confirmPassText.isEmpty()) {
            confirmPass.setError("You must confirm your password!");
            isValid = false;
        }

        if(emailText.isEmpty()) {
            email.setError("Please enter an email.");
            isValid = false;
        }

        if(!emailText.isEmpty() && !isValidEmail(emailText)) {
            email.setError("Please enter a valid email.");
            isValid = false;
        }

        if(!confirmPassText.isEmpty() && !passwordText.isEmpty() && !passwordText.equals(confirmPassText)) {
            confirmPass.setError("Passwords do not match!");
            isValid = false;
        }

        if(addressText.isEmpty()) {
            address.setError("Please enter an address.");
            isValid = false;
        }

        return isValid;
    }

    private void initialize() {
        firstNameText = firstName.getText().toString().trim();
        lastNameText = lastName.getText().toString().trim();
        passwordText = password.getText().toString().trim();
        confirmPassText = confirmPass.getText().toString().trim();
        emailText = email.getText().toString().trim();
        addressText = address.getText().toString().trim();
    }

    private static boolean isValidEmail(String email) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        else
            return false;
    }

    private void registerUser(String firstName, String lastName, String password, String email, String address) {
        User user = new User();
        user.setEmail(email);
        user.setAddress(address);
        user.setLastName(lastName);
        user.setName(firstName);
        user.setPassword(password);
        user.setToken(FirebaseInstanceId.getInstance().getToken());

        apiService.insertUser(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Created new user with ID: " + response.body(), Toast.LENGTH_LONG);
                        Intent logIn = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(logIn);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed to create new user", Toast.LENGTH_LONG);
                    }
                }
                else {
                    Log.e("RegisterActivity", "Error in response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("RegisterActivity", "Error loading from API");
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
