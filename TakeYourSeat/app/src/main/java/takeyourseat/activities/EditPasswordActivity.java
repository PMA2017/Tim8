package takeyourseat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anica.takeyourseat.R;

public class EditPasswordActivity extends AppCompatActivity {

    private EditText newPassword, repeatPassword;
    private String newPasswordText, repeatPasswordText;
    private Button editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        newPassword = (EditText)findViewById(R.id.changeNewPassEdit);
        repeatPassword = (EditText)findViewById(R.id.repeatNewPassEdit);

        newPasswordText = newPassword.toString().trim();
        repeatPasswordText = repeatPassword.toString().trim();

        editBtn = (Button)findViewById(R.id.changePassBtn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPassword();
            }
        });
    }

    private void editPassword() {
        if(validate()) {
            //change password
        }
    }

    private boolean validate() {
        boolean isValid = true;

        if(newPasswordText.isEmpty()) {
            newPassword.setError("Please enter a password.");
            isValid = false;
        }

        if(repeatPasswordText.isEmpty()) {
            repeatPassword.setError("You must confirm your password.");
            isValid = false;
        }

        if(!newPasswordText.isEmpty() && !repeatPasswordText.isEmpty() && !repeatPasswordText.equals(newPasswordText)) {
            repeatPassword.setError("Passwords do not match!");
            isValid = false;
        }

        return isValid;
    }
}
