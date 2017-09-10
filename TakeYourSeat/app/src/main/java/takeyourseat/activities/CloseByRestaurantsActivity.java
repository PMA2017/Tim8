package takeyourseat.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.adapters.RestaurantsListViewAdapter;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.Location;
import takeyourseat.model.Restaurant;

public class CloseByRestaurantsActivity extends AppCompatActivity {

    private ApiService apiService;
    private ArrayList<Restaurant> restaurants;
    private int distance;
    private Location currentLocation;
    private RestaurantsListViewAdapter adapter;
    private ListView restaurantListView;

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
                        if(isInRadius(restaurant)) {
                            restaurants.add(restaurant);
                        }
                    }
                    setupAdapter();
                    setupClickListener();
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {

            }
        });


}

    private void setupAdapter() {

        List<HashMap<String, String>> aList = new ArrayList<>();

        for(int i=0; i<restaurants.size(); i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("listview_id", restaurants.get(i).getId().toString());
            hm.put("listview_title", restaurants.get(i).getName());
            hm.put("listview_description", getDistanceLabel(restaurants.get(i).getDistance()));
            hm.put("listview_image", restaurants.get(i).getImage());
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_description"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

        adapter = new RestaurantsListViewAdapter(CloseByRestaurantsActivity.this, aList, R.layout.restaurant_list_item, from, to);
        restaurantListView = (ListView)findViewById(R.id.restaurant_list_view);
        restaurantListView.setAdapter(adapter);
    }

    private String getDistanceLabel(Float distance) {
        if(distance < 1000) {
            return BigDecimal.valueOf(distance).setScale(2, BigDecimal.ROUND_HALF_UP) + "m";
        }
        else {
            return BigDecimal.valueOf(distance/1000).setScale(2, BigDecimal.ROUND_HALF_UP) + "km";
        }
    }

    private void setupClickListener() {

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                {
                    final String name = ((TextView) view.findViewById(R.id.listview_item_title)).getText().toString();
                    apiService.getRestaurantByName(name).enqueue(new Callback<List<Restaurant>>() {
                        @Override
                        public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                            if(response.isSuccessful()) {
                                if(response.body().size() == 1 && name.equals(response.body().get(0).getName())) {
                                    Intent detailView = new Intent(CloseByRestaurantsActivity.this, DetailActivity.class);
                                    detailView.putExtra("id", response.body().get(0).getId());
                                    detailView.putExtra("name", response.body().get(0).getName());
                                    detailView.putExtra("description", response.body().get(0).getDescription());
                                    detailView.putExtra("email", response.body().get(0).getEmail());
                                    detailView.putExtra("phone", response.body().get(0).getPhone());
                                    detailView.putExtra("website", response.body().get(0).getWebste());
                                    detailView.putExtra("image", response.body().get(0).getImage());
                                    detailView.putExtra("location", response.body().get(0).getLocation());
                                    startActivity(detailView);
                                }
                            }
                            else {
                                int statusCode = response.code();
                                Log.e("CloseByActivity", "Response not successful. Status code: " + statusCode);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                            Log.e("Detail", "error loading from API");
                        }
                    });

                }
            }
        });

    }


    private boolean isInRadius(Restaurant restaurant) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), restaurant.getLatitude(), restaurant.getLongitude(), results);
        float distanceInMeters = results[0];
        restaurant.setDistance(distanceInMeters);
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
