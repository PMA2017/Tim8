package takeyourseat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import takeyourseat.beans.User;

import static com.example.anica.takeyourseat.R.*;

public class RegisterActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText username;
    EditText pass;
    EditText confirmPass;
    EditText email;
    EditText address;
    Button signUp;
    private String firstNameText, lastNameText, usernameText, passText, confirmPassText, emailText, addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_register);

        firstName = (EditText) findViewById(R.id.firstNameReg);
        lastName = (EditText) findViewById(R.id.lastNameReg);
        username = (EditText) findViewById(id.usernameReg);
        pass = (EditText) findViewById(R.id.passReg);
        confirmPass = (EditText) findViewById(R.id.rePassReg);
        address = (EditText) findViewById(id.addressReg);
        email = (EditText) findViewById(R.id.emailReg);
        signUp = (Button) findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        initialize();
        if(!validate()) {
            Toast.makeText(this,"Sign up failed!",Toast.LENGTH_SHORT).show();
        } else {
            registerUser(firstNameText, lastNameText, usernameText, passText, emailText, addressText);
            Intent logIn = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(logIn);
        }
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

        if(usernameText.isEmpty()) {
            username.setError("Please enter an username.");
            isValid = false;
        }

        if(!usernameText.isEmpty() && usernameText.length() <  6 && usernameText.length() > 20) {
            username.setError("Username must be between 6 and 20 characters long!");
            isValid = false;
        }

        if(passText.isEmpty()) {
            pass.setError("Please enter a password.");
            isValid = false;
        }

        if(!passText.isEmpty() && passText.length() < 6) {
            pass.setError("Password must be at least 6 characters long!");
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

        if(!confirmPassText.isEmpty() && !passText.isEmpty() && !passText.equals(confirmPassText)) {
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
        usernameText = username.getText().toString().trim();
        passText = pass.getText().toString().trim();
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

    private void registerUser(String firstName, String lastName, String username, String password, String email, String address) {
        User user = new User();
        user.setEmail(email);
        user.setAddress(address);
        user.setLastName(lastName);
        user.setName(firstName);
        user.setPassword(password);
        user.setEmail(username);
        //ovde treba da se setuje uloga za obicnog korisnika
        // user.setRole();
        //i korisnik treba da se doda u bazu
    }
}
