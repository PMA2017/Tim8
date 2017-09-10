package takeyourseat.activities;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.Location;
import takeyourseat.model.Restaurant;

public class CloseByRestaurantsActivity extends AppCompatActivity {

    private ApiService apiService;
    private ArrayList<Restaurant> restaurants;
    private int distance;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_by_restaurants);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        distance = Integer.valueOf(sharedPref.getString("sync_km", "0"));
        currentLocation = getCurrentLocation();
        apiService = ApiUtils.getApiService();
        restaurants = new ArrayList<>();

        apiService.getRestaurantsWithLocation().enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Restaurant restaurant = response.body().get(i);
                        if(isInRadius(restaurant.getLatitude(), restaurant.getLongitude())) {
                            restaurants.add(restaurant);
                        }
                    }
                    // ovde uraditi prikaz
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });


}


    private boolean isInRadius(Double restaurantLatitude, Double restaurantLongitude) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), restaurantLatitude, restaurantLongitude, results);
        float distanceInMeters = results[0];
        return distanceInMeters < distance*1000;
    }

    private Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new CustomLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, locationListener);
        android.location.Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //if there are no gps satelites, fetch location from network
        if(gpsLocation == null) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L, 500.0f, locationListener);
            gpsLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        double latitude=0;
        double longitude=0;
        latitude = gpsLocation.getLatitude();
        longitude = gpsLocation.getLongitude();

        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
}

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
