package takeyourseat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.db.DatabaseHelper;
import takeyourseat.model.User;

public class ProfilePictureActivity extends AppCompatActivity {

    private static final int SELECTED_PICTURE = 1;
    private ImageView profilePictureDetail;
    private Button change;
    private User currentUser;
    private DatabaseHelper databaseHelper;
    private ApiService apiService;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        profilePictureDetail = (ImageView) findViewById(R.id.profilePictureDetail);
        change = (Button)findViewById(R.id.changeProfPicture);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,SELECTED_PICTURE);

                SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                userId = sharedPreferences.getInt("id", -1);
                String token = sharedPreferences.getString("token","");
                currentUser = new User();
                try {
                    List<User> userr = getDatabaseHelper().getUserDao().queryBuilder().where().eq("token",token).query();
                    currentUser = userr.get(0);
                } catch (Exception e) {
                    Log.e("ProfilePicture", e.getMessage());
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PICTURE:
                if(resultCode == RESULT_OK && requestCode == SELECTED_PICTURE) {
                    Uri uri = data.getData();
                    String url = uri.toString();
                    Toast.makeText(this, url.toString(), Toast.LENGTH_LONG).show();
                    profilePictureDetail.setImageURI(Uri.parse(url));
                    currentUser.setImage(url);
                    try {
                        apiService.updateUser("Id", Integer.toString(userId), currentUser).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if (!response.isSuccessful()) {
                                    int statusCode = response.code();
                                    Log.e("ProfilePicture", "Response not successful. Status code: " + statusCode);
                                    Toast.makeText(ProfilePictureActivity.this, "Error changing profile picture.", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                try {
                                    getDatabaseHelper().getUserDao().update(currentUser);
                                } catch (SQLException e) {
                                    Log.e("ProfilePicture", e.getMessage());
                                }
                                Toast.makeText(getApplicationContext(), "Profile picture changed successfully.", Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Log.e("Profile picture", "error loading from API");
                            }
                        });
                    } catch (Exception ex) {
                        Log.e("ListFriends", ex.getMessage());
                    }
                }
                break;
            default:
                break;
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
