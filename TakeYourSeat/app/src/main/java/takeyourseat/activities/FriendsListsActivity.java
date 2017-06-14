package takeyourseat.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import takeyourseat.adapters.ViewPagerAdapter;
import takeyourseat.dialogs.RemoveFriendsDialog;
import takeyourseat.fragments.FriendsListFragment;
import takeyourseat.fragments.OtherFriendsFragment;

public class FriendsListsActivity extends AppCompatActivity {

    private TabLayout tabFriends;
    private ViewPager view;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_lists);


        tabFriends = (TabLayout) findViewById(R.id.tabFriends);
        view = (ViewPager) findViewById(R.id.viewPagerFriends);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new FriendsListFragment(), "My friends");
        viewPagerAdapter.addFragments(new OtherFriendsFragment(), "Others");
        view.setAdapter(viewPagerAdapter);
        tabFriends.setupWithViewPager(view);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
