package takeyourseat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import takeyourseat.db.DatabaseHelper;
import takeyourseat.model.User;

public class SplashScreenActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

            Thread myThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                        try {
                            users = getDatabaseHelper().getUserDao().queryForAll();
                            if(users.size() > 0) {
                                Intent intent = new Intent(SplashScreenActivity.this, HomePageActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            };
            myThread.start();

    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

}
