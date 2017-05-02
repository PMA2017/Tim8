package takeyourseat.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import takeyourseat.adapters.ViewPagerAdapter;
import takeyourseat.fragments.AboutFragment;
import takeyourseat.fragments.LocationFragment;
import takeyourseat.fragments.MenuFragment;
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

        tabs = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new MenuFragment(), "Menu");
        viewPagerAdapter.addFragments(new TableFragment(), "Tables");
        viewPagerAdapter.addFragments(new LocationFragment(), "Location");
        viewPagerAdapter.addFragments(new AboutFragment(), "About");
        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);

    }
}
