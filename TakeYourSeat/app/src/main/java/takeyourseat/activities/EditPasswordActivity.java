package takeyourseat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

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
    private TextView showPass,showRePass;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        newPassword = (EditText)findViewById(R.id.changeNewPassEdit);
        repeatPassword = (EditText)findViewById(R.id.repeatNewPassEdit);

        showPass = (TextView) findViewById(R.id.showPassProfile);
        showRePass = (TextView) findViewById(R.id.showRePassProfile);

        showPass.setVisibility(View.GONE);
        showRePass.setVisibility(View.GONE);
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (newPassword.getText().length() > 0) {
                    showPass.setVisibility(View.VISIBLE);
                } else {
                    showPass.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        repeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (repeatPassword.getText().length() > 0) {
                    showRePass.setVisibility(View.VISIBLE);
                } else {
                    showRePass.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPass.getText() == "SHOW PASSWORD") {
                    showPass.setText("HIDE PASSWORD");
                    newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    newPassword.setSelection(newPassword.length());

                } else {
                    showPass.setText("SHOW PASSWORD");
                    newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    newPassword.setSelection(newPassword.length());
                }
            }
        });

        showRePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showRePass.getText() == "SHOW PASSWORD") {
                    showRePass.setText("HIDE PASSWORD");
                    repeatPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    repeatPassword.setSelection(newPassword.length());

                } else {
                    showRePass.setText("SHOW PASSWORD");
                    repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    repeatPassword.setSelection(newPassword.length());
                }
            }
        });

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


        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        currentUser = getCurrentUser(token);
    }

    private User getCurrentUser(String token) {
        userId = getIntent().getExtras().getInt("userId");
        try {
            List<User> userr = getDatabaseHelper().getUserDao().queryBuilder().where().eq("token",token).query();
            currentUser = userr.get(0);
            return currentUser;
        } catch (SQLException e) {
            Log.e("EditPasswordActivity", e.getMessage());
            return null;
        }
    }

    private void editPassword() {
        if(validate()) {
            currentUser.setPassword(newPassword.getText().toString());
            apiService.updateUser("Id", Integer.toString(userId), currentUser).enqueue(new Callback<Boolean>() {
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

        if(newPassword.getText().toString().length() < 6){
            newPassword.setError("Password must be at least 6 characters long!");
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
