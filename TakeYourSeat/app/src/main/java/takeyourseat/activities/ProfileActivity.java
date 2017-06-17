package takeyourseat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ProfileActivity extends AppCompatActivity {


    private TextView nameView, addressView, emailView;
    private EditText nameEdit, addressEdit, emailEdit;
    private Button saveBtn, cancelBtn, editProfileBtn, editPasswordBtn;
    private ImageView profilePicture;
    private ApiService apiService;
    private DatabaseHelper databaseHelper;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameView = (TextView)findViewById(R.id.textViewName);
        addressView = (TextView)findViewById(R.id.textViewAddress);
        emailView = (TextView)findViewById(R.id.textViewEmail);

        nameEdit = (EditText)findViewById(R.id.editTextName);
        addressEdit = (EditText)findViewById(R.id.editTextAddress);
        emailEdit = (EditText)findViewById(R.id.edittTextEmail);

        editProfileBtn = (Button)findViewById(R.id.editProfile);
        editPasswordBtn = (Button)findViewById(R.id.editPassword);
        saveBtn = (Button)findViewById(R.id.saveProfile);
        cancelBtn = (Button)findViewById(R.id.cancelProfile);
        profilePicture = (ImageView) findViewById(R.id.profilePicture);

        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", -1);
        currentUser = new User();
        try {
            currentUser = getDatabaseHelper().getUserDao().queryForEq("Id", id).get(0);
        } catch (SQLException e) {
            Log.e("ProfileActivity", e.getMessage());
        }

        String name = currentUser.getName();
        String lastName = currentUser.getLastName();
        String pass = currentUser.getPassword();
        String email = currentUser.getEmail();
        String address = currentUser.getAddress();

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilePicture = new Intent(ProfileActivity.this, ProfilePictureActivity.class);
                startActivity(profilePicture);
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUserDetails();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        editPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPassword();
            }
        });

        nameView.setText(name + " " + lastName);
        addressView.setText(address);
        emailView.setText(email);

        apiService = ApiUtils.getApiService();
    }

    private void editPassword() {
        Intent editPassword = new Intent(ProfileActivity.this, EditPasswordActivity.class);
        editPassword.putExtra("userId", currentUser.getId());
        startActivity(editPassword);
    }

    private void editUserDetails() {

        toggleButtonAndTextVisibility(View.GONE, View.VISIBLE);

        nameEdit.setText(nameView.getText().toString().trim());
        addressEdit.setText(addressView.getText().toString().trim());
        emailEdit.setText(emailView.getText().toString().trim());
    }

    private void save() {
        String name = nameEdit.getText().toString();
        String address = addressEdit.getText().toString();
        String email = emailEdit.getText().toString();

        currentUser.setName(name);
        currentUser.setAddress(address);
        currentUser.setEmail(email);

        try {
            apiService.updateUser("Id", currentUser.getId().toString(), currentUser).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(!response.isSuccessful()) {
                        int statusCode = response.code();
                        Log.e("MainActivity", "Response not successful. Status code: " + statusCode);
                        Toast.makeText(ProfileActivity.this, "Error in updating user details.", Toast.LENGTH_LONG);
                        return;
                    }

                    try {
                        getDatabaseHelper().getUserDao().update(currentUser);
                    } catch (SQLException e) {
                        Log.e("ProfileActivity", e.getMessage());
                    }
                    nameView.setText(currentUser.getName());
                    addressView.setText(currentUser.getAddress());
                    emailView.setText(currentUser.getEmail());
                    toggleButtonAndTextVisibility(View.VISIBLE, View.GONE);
                    Toast.makeText(ProfileActivity.this, "Profile updated successfully.",Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("ProfileActivity", "error loading from API");
                }
            });
        }
        catch (Exception ex) {
            Log.e("ProfileActivity", ex.getMessage());
        }

    }

    private void cancel() {
        toggleButtonAndTextVisibility(View.VISIBLE, View.GONE);
    }

    private void toggleButtonAndTextVisibility(int viewVisibility, int editVisibility) {
        nameView.setVisibility(viewVisibility);
        addressView.setVisibility(viewVisibility);
        emailView.setVisibility(viewVisibility);

        nameEdit.setVisibility(editVisibility);
        addressEdit.setVisibility(editVisibility);
        emailEdit.setVisibility(editVisibility);

        editProfileBtn.setVisibility(viewVisibility);
        editPasswordBtn.setVisibility(viewVisibility);

        saveBtn.setVisibility(editVisibility);
        cancelBtn.setVisibility(editVisibility);
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
