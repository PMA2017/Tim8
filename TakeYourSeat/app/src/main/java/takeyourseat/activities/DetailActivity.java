package takeyourseat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import takeyourseat.fragments.AboutFragment;
import takeyourseat.fragments.AllReservationDetailsFragment;
import takeyourseat.fragments.LocationFragment;
import takeyourseat.fragments.MenuFragment;
import takeyourseat.fragments.RateCommentsFragment;
import takeyourseat.fragments.TableFragment;

public class DetailActivity extends AppCompatActivity {

    private TextView resNameTextView;
    private ImageView image;
    private TabLayout tabs;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private String restaurantName;
    private int locationId;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                restaurantName= null;
            } else {
                restaurantName= extras.getString("name");
                locationId = extras.getInt("location");
                imageUrl = extras.getString("image");
                Bundle args = new Bundle();
                args.putString("resName",restaurantName);
                AllReservationDetailsFragment all = new AllReservationDetailsFragment();
                all.setArguments(args);

            }
        } else {
            restaurantName = savedInstanceState.getString("resName");
            imageUrl = savedInstanceState.getString("imageUrl");
            Bundle args = new Bundle();
            args.putString("resName",restaurantName);
            AllReservationDetailsFragment all = new AllReservationDetailsFragment();
            all.setArguments(args);

        }

        resNameTextView = (TextView) findViewById(R.id.resNameDetail);
        resNameTextView.setText(restaurantName);
        image = (ImageView)findViewById(R.id.restaurantImage);
        Picasso.with(getBaseContext()).load(imageUrl).resize(300, 200).centerInside().onlyScaleDown().into(image);
        tabs = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabs.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new MenuFragment(), "");
        viewPagerAdapter.addFrag(new TableFragment(), "");
        viewPagerAdapter.addFrag(new RateCommentsFragment(), "");
        viewPagerAdapter.addFrag(new AboutFragment(), "");
        //viewPagerAdapter.addFrag(new LocationFragment(), "");

        LocationFragment locationFragmet = new LocationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("location", locationId);
        bundle.putString("name", restaurantName);
        locationFragmet.setArguments(bundle);

        viewPagerAdapter.addFrag(locationFragmet, "");

        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(R.drawable.restaurant_icon);
        tabs.getTabAt(1).setIcon(R.mipmap.ic_launcher);
        tabs.getTabAt(2).setIcon(R.drawable.ic_comment);
        tabs.getTabAt(3).setIcon(R.drawable.ic_action_about);
        tabs.getTabAt(4).setIcon(R.drawable.ic_location);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("resName", restaurantName);
        outState.putString("imageUrl", imageUrl);
    }


}


class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}


