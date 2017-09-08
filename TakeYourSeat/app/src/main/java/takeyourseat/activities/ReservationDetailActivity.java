package takeyourseat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anica.takeyourseat.R;


public class ReservationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);

        int id = getIntent().getIntExtra("id", -1);
    }
}
