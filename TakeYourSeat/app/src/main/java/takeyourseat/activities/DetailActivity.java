package takeyourseat.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.List;

import takeyourseat.fragments.AboutFragment;
import takeyourseat.fragments.LocationFragment;
import takeyourseat.fragments.MenuFragment;
import takeyourseat.fragments.RateCommentsFragment;
import takeyourseat.fragments.TableFragment;

public class DetailActivity extends AppCompatActivity {

    private TextView resName;
    private TabLayout tabs;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        String restaurantName;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                restaurantName= null;
            } else {
                restaurantName= extras.getString("name");
            }
        } else {
            restaurantName= (String) savedInstanceState.getSerializable("name");
        }


        resName = (TextView) findViewById(R.id.resNameDetail);
        resName.setText(restaurantName);
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
        viewPagerAdapter.addFrag(new LocationFragment(), "");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(R.drawable.restaurant_icon);
        tabs.getTabAt(1).setIcon(R.mipmap.ic_launcher);
        tabs.getTabAt(2).setIcon(R.drawable.ic_comment);
        tabs.getTabAt(3).setIcon(R.drawable.ic_action_about);
        tabs.getTabAt(4).setIcon(R.drawable.ic_location);
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
