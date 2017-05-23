package takeyourseat.activities;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.beans.Restaurant;
import takeyourseat.beans.User;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;

public class HomePageActivity extends AppCompatActivity {


    private String[] navDrawerItemTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private EditText searchRes;
    private ActionBarDrawerToggle mDrawerToggle;
    private ApiService apiService;


    /*private String[] listviewTitle = new String[]{
            "Restaurant 1", "Restaurant 2", "Restaurant 3", "Restaurant 4",
            "Restaurant 5", "Restaurant 6", "Restaurant 7", "Restaurant 8",
    };*/

    private int[] listviewImage = new int[]{
            R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon,
            R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon,

    };

    /*private String[] listviewShortDescription = new String[]{
            "Restaurant 1 Description", "Restaurant 2 Description", "Restaurant 3 Description", "Restaurant 4 Description",
            "Restaurant 5 Description", "Restaurant 6 Description", "Restaurant 7 Description", "Restaurant 8 Description",
    };*/

    private List<Restaurant> restaurants;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        apiService = ApiUtils.getApiService();
        apiService.getAllRestaurants().enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if(response.isSuccessful()) {
                    List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
                    for(int i=0; i<response.body().size(); i++) {
                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("listview_title", response.body().get(i).getName());
                        hm.put("listview_description", response.body().get(i).getDescription());
                        hm.put("listview_image", Integer.toString(listviewImage[i]));
                        aList.add(hm);
                    }

                    String[] from = {"listview_image", "listview_title", "listview_description"};
                    int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

                    SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.restaurant_list_item, from, to);
                    ListView restaurantListView = (ListView)findViewById(R.id.restaurant_list_view);
                    restaurantListView.setAdapter(simpleAdapter);

                    restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            {
                                Intent detailView = new Intent(HomePageActivity.this, DetailActivity.class);
                                startActivity(detailView);
                            }
                        }
                    });
                }
                else {
                    int statusCode = response.code();
                    Log.e("HomePageActivity", "Response not successful. Status code: " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e("HomePageActivity", "Error loading from API");
            }
        });

        navDrawerItemTitles = getResources().getStringArray(R.array.nav_drawer_item_titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        searchRes = (EditText) findViewById(R.id.searchRes);

        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, navDrawerItemTitles));

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.restaurant_icon);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
               //  getActionBar().setTitle(R.string.homePage);
                getSupportActionBar().setTitle(R.string.homePage);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
             // getActionBar().setTitle(R.string.homePage);
                getSupportActionBar().setTitle(R.string.homePage);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        searchRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(HomePageActivity.this,"Searching",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Intent home = new Intent(HomePageActivity.this, HomePageActivity.class);
                    startActivity(home);
                }
                if(position == 1) {
                    Intent reservationList = new Intent(HomePageActivity.this, ReservationListActivity.class);
                    startActivity(reservationList);
                }
                if(position == 2) {
                    Intent profile = new Intent(HomePageActivity.this, ProfileActivity.class);
                    startActivity(profile);
                }
                if(position == 3) {
                    Intent friends = new Intent(HomePageActivity.this, FriendsListsActivity.class);
                    startActivity(friends);
                }

            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        /*for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_description", listviewShortDescription[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }*/


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settings = new Intent(HomePageActivity.this, SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.logOut:
                // ovde treba uzlogovati usera
                Intent logOut = new Intent(HomePageActivity.this,MainActivity.class);
                startActivity(logOut);
                break;
            case R.id.addRestaurant:
                Intent addRestaurant = new Intent(HomePageActivity.this, AddRestaurantActivity.class);
                startActivity(addRestaurant);
                break;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public void onBackPressed() {
    }
}
