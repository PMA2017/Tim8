package takeyourseat.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.anica.takeyourseat.R;

import java.util.Map;

public class CloseByRestaurantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_by_restaurants);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String km = sharedPref.getString("sync_km", "");
    }

//        Map<String, ?> allEntries = getSharedPreferences("sync_km", MODE_PRIVATE).getAll();
//        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
//            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
//        }

//        rs = st.executeQuery("SELECT longititude,latitude FROM  location");
//        while (rs.next()) {
//            double venueLat =rs.getDouble("latitude");
//            double venueLng = rs.getDouble("longititude");
//
//            double latDistance = Math.toRadians(userLat - venueLat);
//            double lngDistance = Math.toRadians(userLng - venueLng);
//            double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
//                    (Math.cos(Math.toRadians(userLat))) *
//                            (Math.cos(Math.toRadians(venueLat))) *
//                            (Math.sin(lngDistance / 2)) *
//                            (Math.sin(lngDistance / 2));
//
//            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//            double dist = 6371 * c;
//            if (dist<50){
//                    /* Include your code here to display your records */
//            }
//        https://developers.google.com/maps/documentation/javascript/geometry?hl=el#Distance
}
