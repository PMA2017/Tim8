package takeyourseat.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.adapters.RestaurantsListViewAdapter;
import takeyourseat.db.DatabaseHelper;
import takeyourseat.model.Restaurant;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.User;

public class HomePageActivity extends AppCompatActivity {


    private String[] navDrawerItemTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private EditText searchRes;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView restaurantListView;
    private ApiService apiService;
    List<HashMap<String, String>> aList;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;
    private DatabaseHelper databaseHelper;
    private RestaurantsListViewAdapter adapter;


    private int[] listviewImage = new int[]{
            R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon,
            R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon,

    };

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
                    aList = new ArrayList<HashMap<String, String>>();
                    for(int i=0; i<response.body().size(); i++) {
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("listview_id", response.body().get(i).getId().toString());
                        hm.put("listview_title", response.body().get(i).getName());
                        hm.put("listview_description", response.body().get(i).getDescription());
                        hm.put("listview_image", response.body().get(i).getImage());
                        aList.add(hm);
                    }

                    String[] from = {"listview_image", "listview_title", "listview_description"};
                    int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

                    //SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.restaurant_list_item, from, to);
                    adapter = new RestaurantsListViewAdapter(HomePageActivity.this,aList, R.layout.restaurant_list_item, from, to);
                    restaurantListView = (ListView)findViewById(R.id.restaurant_list_view);
                    //restaurantListView.setAdapter(simpleAdapter);
                    restaurantListView.setAdapter(adapter);

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
                                                int resId = response.body().get(0).getId();
                                                sharedPrefs = getSharedPreferences("restaurantId", Context.MODE_PRIVATE);
                                                editor = sharedPrefs.edit();
                                                editor.putInt("resId", resId);
                                                editor.commit();
                                                String resName = response.body().get(0).getName();
                                                String description = response.body().get(0).getDescription();
                                                String email = response.body().get(0).getEmail();
                                                String phone = response.body().get(0).getPhone();
                                                String website = response.body().get(0).getWebste();
                                                String image = response.body().get(0).getImage();
                                                int location = response.body().get(0).getLocation();
                                                Intent detailView = new Intent(HomePageActivity.this, DetailActivity.class);
                                                detailView.putExtra("name",resName);
                                                detailView.putExtra("description",description);
                                                detailView.putExtra("email",email);
                                                detailView.putExtra("phone",phone);
                                                detailView.putExtra("website",website);
                                                detailView.putExtra("image", image);
                                                detailView.putExtra("location", location);
                                                startActivity(detailView);
                                            }
                                        }
                                        else {
                                            int statusCode = response.code();
                                            Log.e("MainActivity", "Response not successful. Status code: " + statusCode);
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
                HomePageActivity.this.adapter.getFilter().filter(s);
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
                /*SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                int id = sharedPreferences.getInt("id",0);
                String id1 = String.valueOf(id);
                Toast.makeText(this, id1, Toast.LENGTH_SHORT).show();
                //User user = getDatabaseHelper().getUserDao().queryForId(id);
                try {
                    getDatabaseHelper().getUserDao().deleteById(id);
                    Toast.makeText(this, "deleteAll", Toast.LENGTH_SHORT).show();
                    Intent logOut = new Intent(HomePageActivity.this,MainActivity.class);
                    startActivity(logOut);
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/
                 deleteAll();
                Toast.makeText(this, "deleteAll", Toast.LENGTH_SHORT).show();
                Intent logOut = new Intent(HomePageActivity.this,MainActivity.class);
                startActivity(logOut);

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("search",searchRes.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            String search = savedInstanceState.getString("search");
            searchRes.setText(search);
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void deleteAll()
    {
        try
        {
            for(User user : getDatabaseHelper().getUserDao().queryForAll())
            {
                Dao<User, Integer> dao = getDatabaseHelper().getUserDao();
                dao.delete(user);
            }
        }
        catch(Exception e)
        {
            Log.e(HomePageActivity.class.getName(), "error on delete");
        }
        finally
        {
            getDatabaseHelper().close();
        }
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

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
