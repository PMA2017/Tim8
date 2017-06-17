package takeyourseat.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.db.DatabaseHelper;
import takeyourseat.model.User;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;

public class MainActivity extends AppCompatActivity {



    private EditText email;
    private EditText password;
    private TextView error;
    private TextView showPass;
    private Button logIn;
    private TextView registerHere;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    String name,lastName,email1,address,pass,token,image;
    private DatabaseHelper databaseHelper;
    private int id;

    User user;

    private ApiService apiService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.pass);
            error = (TextView) findViewById(R.id.textViewError);
            showPass = (TextView) findViewById(R.id.showPass);
            logIn = (Button) findViewById(R.id.signIn);
            registerHere = (TextView) findViewById(R.id.register);
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            showPass.setVisibility(View.GONE);
            password.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (password.getText().length() > 0) {
                        showPass.setVisibility(View.VISIBLE);
                    } else {
                        showPass.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            registerHere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    MainActivity.this.startActivity(registerIntent);
                }
            });

            showPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showPass.getText() == "SHOW PASSWORD") {
                        showPass.setText("HIDE PASSWORD");
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setSelection(password.length());

                    } else {
                        showPass.setText("SHOW PASSWORD");
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        password.setSelection(password.length());
                    }
                }
            });

            apiService = ApiUtils.getApiService();

            logIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validate()) {
                        try {
                            authenticate(email.getText().toString(), password.getText().toString());
                        } catch (Exception ex) {
                            Log.e("Error: ", ex.getMessage());
                        }
                    }
                }
            });
        }


    private boolean validate() {
        boolean isValid = true;

        if(email.getText().toString().isEmpty()) {
            email.setError("Please enter an email.");
            isValid = false;
        }
        if(!email.getText().toString().isEmpty() && !isValidEmail(email.getText().toString())) {
            email.setError("Please enter a valid email.");
            isValid = false;
        }
        if(password.getText().toString().isEmpty()) {
            email.setError("Please enter password.");
            isValid = false;
        }
        if(!password.getText().toString().isEmpty() && password.getText().toString().length() < 6) {
            password.setError("Password must be at least 6 characters long!");
            isValid = false;
        }

        return isValid;
    }

    private static boolean isValidEmail(String email) {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        else
            return false;
    }

    private void authenticate(String email, final String password) {
        apiService.getUserByEmail(email).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if(response.isSuccessful()) {
                        if(response.body().size() == 1 && password.equals(response.body().get(0).getPassword())) {
                            id = response.body().get(0).getId();
                            token = response.body().get(0).getToken();
                            name = response.body().get(0).getName();
                            lastName = response.body().get(0).getLastName();
                            email1 = response.body().get(0).getEmail();
                            pass = response.body().get(0).getPassword();
                            address = response.body().get(0).getAddress();
                            image = response.body().get(0).getImage();
                            saveUserDetail(id,token,name,lastName,pass,address,email1,image);
                            saveUserInDatabase(id,token,name,lastName,email1,pass,address,image);
                            Intent homePageIntent = new Intent(MainActivity.this, HomePageActivity.class);
                            MainActivity.this.startActivity(homePageIntent);
                            error.setText("");
                        }
                        else {
                            error.setText("Incorrect username and/or password.");
                        }
                    }
                    else {
                        int statusCode = response.code();
                        Log.e("MainActivity", "Response not successful. Status code: " + statusCode);
                    }
                }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("MainActivity", "error loading from API");
            }
        });
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void saveUserInDatabase(int id, String token, String name, String lastName, String email1, String pass, String address, String image) {
        User user = new User();
        user.setId(id);
        user.setToken(token);
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email1);
        user.setPassword(pass);
        user.setAddress(address);
        user.setImage(image);

        try {
            getDatabaseHelper().getUserDao().create(user);
            Toast.makeText(this, "User inserted", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveUserDetail(int id, String token, String name, String lastName, String pass, String address, String email, String image) {
        sharedPref = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("id",id);
        editor.putString("token",token);
        editor.putString("name",name);
        editor.putString("lastName",lastName);
        editor.putString("pass",pass);
        editor.putString("address",address);
        editor.putString("email",email);
        editor.putString("image",image);
        editor.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }




}
