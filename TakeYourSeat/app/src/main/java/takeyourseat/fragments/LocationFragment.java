package takeyourseat.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.dialogs.LocationDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback,LocationListener {


    private GoogleMap map;
    private MapView mapView;
    private View view;
    private LocationManager locationManager;
    private AlertDialog dialog;
    private String provider;
    private ApiService apiService;

    public LocationFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        apiService = ApiUtils.getApiService();
    }

    private void showLocatonDialog(){
        if(dialog == null){
            dialog = new LocationDialog(getActivity()).prepareDialog();
        }else{
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }

        dialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_location, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        final int locationId = getArguments().getInt("location");

        apiService.getLocationById(Integer.toString(locationId)).enqueue(new Callback<List<takeyourseat.model.Location>>() {
            @Override
            public void onResponse(Call<List<takeyourseat.model.Location>> call, Response<List<takeyourseat.model.Location>> response) {
                if(!response.isSuccessful()) {
                    int statusCode = response.code();
                    Log.e("MainActivity", "Response not successful. Status code: " + statusCode);
                    Toast.makeText(getContext(), "Error in fetching location of restaurant.", Toast.LENGTH_LONG).show();
                    return;
                }
                takeyourseat.model.Location resturantLocation = response.body().get(0);
                String restaurantName = getArguments().getString("name");
                LatLng coordinates = new LatLng(resturantLocation.getLatitude(), resturantLocation.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(coordinates).title(restaurantName));


                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinates, 15);
                map.animateCamera(yourLocation);
            }

            @Override
            public void onFailure(Call<List<takeyourseat.model.Location>> call, Throwable t) {
                Log.e("EditPasswordActivity", "error loading from API");
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onLocationChanged(Location location) {

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