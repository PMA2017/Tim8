package takeyourseat.activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private String[] navDrawerItemTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;


    private String[] listviewTitle = new String[]{
            "Restaurant 1", "Restaurant 2", "Restaurant 3", "Restaurant 4",
            "Restaurant 5", "Restaurant 6", "Restaurant 7", "Restaurant 8",
    };

    private int[] listviewImage = new int[]{
            R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon,
            R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon, R.drawable.restaurant_icon,

    };

    private String[] listviewShortDescription = new String[]{
            "Restaurant 1 Description", "Restaurant 2 Description", "Restaurant 3 Description", "Restaurant 4 Description",
            "Restaurant 5 Description", "Restaurant 6 Description", "Restaurant 7 Description", "Restaurant 8 Description",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        navDrawerItemTitles = getResources().getStringArray(R.array.nav_drawer_item_titles);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);


        drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, navDrawerItemTitles));

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 2) {
                    Intent profile = new Intent(HomePageActivity.this, ProfileActivity.class);
                    startActivity(profile);
                }
                if (position == 4) {
                    Intent settings = new Intent(HomePageActivity.this, SettingsActivity.class);
                    startActivity(settings);
                }

            }

        });

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_description", listviewShortDescription[i]);
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
}
