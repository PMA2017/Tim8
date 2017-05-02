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

import static com.example.anica.takeyourseat.R.*;

public class RegisterActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText username;
    EditText pass;
    EditText confirmPass;
    EditText email;
    Button signUp;
    private String firstNameText,lastNameText,usernameText,passText,confirmPassText,emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_register);

        firstName = (EditText) findViewById(R.id.firstNameReg);
        lastName = (EditText) findViewById(R.id.lastNameReg);
        username = (EditText) findViewById(id.usernameReg);
        pass = (EditText) findViewById(R.id.passReg);
        confirmPass = (EditText) findViewById(R.id.rePassReg);
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
            Toast.makeText(this,"Sign up faild",Toast.LENGTH_SHORT).show();
        } else {
            Intent logIn = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(logIn);
        }
    }

    private boolean validate() {
        boolean valid = true;
        if(firstNameText.isEmpty() || firstNameText.length() > 32) {
            firstName.setError("Please enter valid name");
            valid = false;
        }
        if(lastNameText.isEmpty() || lastNameText.length() > 32) {
            lastName.setError("Please enter valid last name");
            valid = false;
        }
        if(usernameText.isEmpty() || usernameText.length() > 10) {
            username.setError("Please enter valid username");
            valid = false;
        }
        if(passText.isEmpty()) {
            pass.setError("Please enter valid password");
            valid = false;
        }
        if(confirmPassText.isEmpty()) {
            confirmPass.setError("Please enter valid password");
            valid = false;
        }
        if(emailText.isEmpty() || !isValidEmail(emailText)) {
            email.setError("Please enter valid email");
            valid = false;
        }

        return valid;

    }

    private void initialize() {
        firstNameText = firstName.getText().toString().trim();
        lastNameText = lastName.getText().toString().trim();
        usernameText = username.getText().toString().trim();
        passText = pass.getText().toString().trim();
        confirmPassText = confirmPass.getText().toString().trim();
        emailText = email.getText().toString().trim();
    }


    private static boolean isValidEmail(String email) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }
}
