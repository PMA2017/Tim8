package takeyourseat.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

import takeyourseat.adapters.ViewPagerAdapter;
import takeyourseat.dialogs.RemoveFriendsDialog;
import takeyourseat.fragments.FriendsListFragment;
import takeyourseat.fragments.OtherFriendsFragment;
import takeyourseat.fragments.TableFragment;

public class FriendsListsActivity extends AppCompatActivity {

    private TabLayout tabFriends;
    private ViewPager view;
    private ViewPagerAdapter viewPagerAdapter;
    private AlertDialog dialog;




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
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete: {
                showRemoveFriendsDialog();
                // delete from friends list
            }

            case R.id.add: {
                Toast.makeText(this,"Add friend",Toast.LENGTH_SHORT).show();
                // add to friends list
            }

        }
        return super.onContextItemSelected(item);
    }


    private void showRemoveFriendsDialog(){
        if(dialog == null){
            dialog = new RemoveFriendsDialog(FriendsListsActivity.this).prepareDialog();
        }else{
            if(dialog.isShowing()){
                dialog.dismiss();
            }
        }

        dialog.show();
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
