package takeyourseat.activities;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.example.anica.takeyourseat.R;

import takeyourseat.adapters.Communicator;



public class ReservationActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        ViewPager vp = (ViewPager) findViewById(R.id.viewPagerFragment);
        Communicator com = new Communicator(getSupportFragmentManager());
        vp.setAdapter(com);
        vp.setCurrentItem(0);

    }
}




