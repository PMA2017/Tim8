package takeyourseat.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.anica.takeyourseat.R;

public class ProfilePictureActivity extends AppCompatActivity {

    private static final int SELECTED_PICTURE = 1;
    private ImageView profilePictureDetail;
    private Button change;

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
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PICTURE:
                if(resultCode == RESULT_OK && requestCode == SELECTED_PICTURE) {
                    Uri url = data.getData();
                    profilePictureDetail.setImageURI(url);
                }
                break;
            default:
                break;
        }
    }
}
