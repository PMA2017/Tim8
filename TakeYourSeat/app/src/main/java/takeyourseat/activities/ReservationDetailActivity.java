package takeyourseat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.Reservation;
import takeyourseat.model.TableReservation;


public class ReservationDetailActivity extends AppCompatActivity {

    private TextView startDate;
    private TextView endDate;
    private TextView time;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);

        startDate = (TextView) findViewById(R.id.resDateText);
        endDate = (TextView) findViewById(R.id.resDateEndText);
        time = (TextView) findViewById(R.id.resTimeText);
        int id = getIntent().getIntExtra("id", -1);
        String start = getIntent().getStringExtra("startDate");
        String[] start1 = start.split("T");
        String startD = start1[0];
        String end = getIntent().getStringExtra("endDate");
        String[] end1 = end.split("T");
        String endD = end1[0];
        String t = start1[1] + "-" + end1[1];
        startDate.setText(startD);
        endDate.setText(endD);
        time.setText(t);

        apiService = ApiUtils.getApiService();


    }
}
