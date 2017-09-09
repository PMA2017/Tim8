package takeyourseat.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.Location;
import takeyourseat.model.ReservationTable;
import takeyourseat.model.Restaurant;

public class CloseByRestaurantsActivity extends AppCompatActivity {

    private ApiService apiService;
    private ArrayList<Restaurant> restaurants;
    private boolean isGPSEnabled, isNetworkEnabled;
    private int distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_by_restaurants);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        distance = Integer.valueOf(sharedPref.getString("sync_km", "0"));

        apiService = ApiUtils.getApiService();

        apiService.getAllRestaurants().enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if(response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        apiService.getLocationById(String.valueOf(response.body().get(i).getLocation())).enqueue(new Callback<List<Location>>() {
                            @Override
                            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                                if(response.isSuccessful()) {
                                    Location loc = response.body().get(0);

                                    //ovde sad treba proveravati da li upada ova vrednost i tek onda dodati u listu restorana
                                    //restaurants.add(response.body().get(i));
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Location>> call, Throwable t) {

                            }
                        });
                    }

                    LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

                    isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                    LocationListener locationListener = new CustomLocationListener();

                    if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
                    }

                    LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });

//        https://developers.google.com/maps/documentation/javascript/geometry?hl=el#Distance
}

    private boolean isInRadius(int latitude, int longitude) {
//        double latDistance = Math.toRadians(userLat - latitude);
//        double lngDistance = Math.toRadians(userLng - longitude);
//
//        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2))
//                    + (Math.cos(Math.toRadians(userLat)))
//                    * (Math.cos(Math.toRadians(venueLat)))
//                    * (Math.sin(lngDistance / 2))
//                    * (Math.sin(lngDistance / 2));
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
        double dist = 6371 * c;
        if(dist < distance) {

        }
//
        return true;
    }

//    private Location getLocationForRestaurant() {
//
//    }

    private class CustomLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(android.location.Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

}
