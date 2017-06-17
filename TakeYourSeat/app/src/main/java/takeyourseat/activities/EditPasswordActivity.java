package takeyourseat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.db.DatabaseHelper;
import takeyourseat.model.User;

public class EditPasswordActivity extends AppCompatActivity {

    private EditText newPassword, repeatPassword;
    private Button editBtn;
    private ApiService apiService;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        newPassword = (EditText)findViewById(R.id.changeNewPassEdit);
        repeatPassword = (EditText)findViewById(R.id.repeatNewPassEdit);

        editBtn = (Button)findViewById(R.id.changePassBtn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editPassword();
                }
                catch(Exception ex) {
                    Log.e("EditPasswordActivity", ex.getMessage());
                }
            }
        });

        apiService = ApiUtils.getApiService();

        currentUser = getCurrentUser();
    }

    private User getCurrentUser() {
        int userId = getIntent().getExtras().getInt("userId");
        try {
            currentUser = getDatabaseHelper().getUserDao().queryForEq("Id", userId).get(0);
            return currentUser;
        } catch (SQLException e) {
            Log.e("EditPasswordActivity", e.getMessage());
            return null;
        }
    }

    private void editPassword() {
        if(validate()) {
            currentUser.setPassword(newPassword.getText().toString());
            apiService.updateUser("Id", currentUser.getId().toString(), currentUser).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(!response.isSuccessful()) {
                        int statusCode = response.code();
                        Log.e("MainActivity", "Response not successful. Status code: " + statusCode);
                        Toast.makeText(EditPasswordActivity.this, "Error in changing password.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        getDatabaseHelper().getUserDao().update(currentUser);
                    } catch (SQLException e) {
                        Log.e("EditPasswordActivity", e.getMessage());
                    }
                    Toast.makeText(getApplicationContext(), "Password changed successfully.", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("EditPasswordActivity", "error loading from API");
                }
            });

        }
    }

    private boolean validate() {
        if(newPassword.getText().toString().isEmpty()){
            newPassword.setError("Please enter a password.");
            return false;
        }

        if(repeatPassword.getText().toString().isEmpty()) {
            repeatPassword.setError("You must confirm your password.");
            return true;
        }

        if(!repeatPassword.getText().toString().trim().equals(newPassword.getText().toString().trim())) {
            repeatPassword.setError("Passwords do not match!");
            return false;
        }
        return  true;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
