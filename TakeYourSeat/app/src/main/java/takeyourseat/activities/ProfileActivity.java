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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import takeyourseat.beans.User;

public class ProfileActivity extends AppCompatActivity {


    private TextView nameView, addressView, emailView;
    private EditText nameEdit, addressEdit, emailEdit;
    private Button saveBtn, cancelBtn, editProfileBtn, editPasswordBtn;
    private ImageView profilePicture;

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
        String name = sharedPreferences.getString("name","Name");
        String lastName = sharedPreferences.getString("lastName","Last name");
        String pass = sharedPreferences.getString("pass","Password");
        String email = sharedPreferences.getString("email","Email");
        String address = sharedPreferences.getString("address","Address");

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
    }

    private void editPassword() {
        Intent editPassword = new Intent(ProfileActivity.this, EditPasswordActivity.class);
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
        //update-ovati user-a koriscenjem api-ja
        toggleButtonAndTextVisibility(View.VISIBLE, View.GONE);
        //proveriti da li treba setovati vrednost view polja na ove promenjene
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

}
