package takeyourseat.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import takeyourseat.fragments.DateAndTimeFragment;
import takeyourseat.fragments.InviteFriendsFragment;
import takeyourseat.fragments.ReservationOtherDetailsFragment;
import takeyourseat.fragments.ReservationTablesFragment;

/**
 * Created by anica on 31.5.2017.
 */

public class Communicator extends FragmentPagerAdapter {

    public Communicator(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DateAndTimeFragment();
            case 1:
                return new ReservationTablesFragment();
            case 2:
                return new ReservationOtherDetailsFragment();
            case 3:
                return new InviteFriendsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
