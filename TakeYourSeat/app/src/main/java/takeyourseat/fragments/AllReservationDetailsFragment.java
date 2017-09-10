package takeyourseat.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.activities.HomePageActivity;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.db.DatabaseHelper;
import takeyourseat.model.CompleteReservation;
import takeyourseat.model.EnumValues;
import takeyourseat.model.Reservation;
import takeyourseat.model.ReservationFriends;
import takeyourseat.model.TableReservation;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllReservationDetailsFragment extends Fragment {

    private TextView dateView, timeView, nameView;
    private ListView friendsView;
    private Button finish;
    private String date, time;
    private ApiService apiService;
    private DatabaseHelper databaseHelper;
    private int userId;
    private ArrayList<String> tableIds;
    private int[] friendIds;

    public AllReservationDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_all_reservation_details, container, false);

        apiService = ApiUtils.getApiService();

        dateView = (TextView) v.findViewById(R.id.resDateText);
        timeView = (TextView) v.findViewById(R.id.resTimeText);
        nameView = (TextView) v.findViewById(R.id.resNameText);
        friendsView = (ListView) v.findViewById(R.id.friendsView);

        finish = (Button) v.findViewById(R.id.reserve);

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.invited_friend_item, R.id.invitedFriendView, getArguments().getStringArray("friendNames"));
        friendsView.setAdapter(adapter);

        date = getActivity().getIntent().getExtras().getString("date");
        time = getActivity().getIntent().getExtras().getString("time");
        String name = getActivity().getIntent().getExtras().getString("restaurantName");

        dateView.setText(date);
        timeView.setText(time);
        nameView.setText(name);

        tableIds = getArguments().getStringArrayList("tableIds");
        friendIds = getArguments().getIntArray("friendIds");

        userId = getDatabaseHelper().getCurrentUser().getId();

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteReservation reservation = new CompleteReservation();

                reservation.setReservation(createReservation());
                reservation.setTableIds(tableIds);
                reservation.setFriendIds(getFriendIds());

                try {
                    apiService.finishReservation(reservation).enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            boolean isSuccessful = response.isSuccessful();
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.e("On failure", t.getMessage());
                        }
                    });
                }
                catch (Exception ex) {
                    Log.e("Finish reservation", ex.getMessage());
                }
            }


        });

        return v;
    }

    private String createReservation() {
        Reservation res = new Reservation();

        res.setUser(userId);
        res.setStartDate(getArguments().getString("startDate"));
        res.setEndDate(getArguments().getString("endDate"));
        res.setRestaurantId(getActivity().getIntent().getExtras().getInt("id", 0));

        return new Gson().toJson(res);
    }

    private ArrayList<String> getFriendIds() {
        ArrayList<String> ids = new ArrayList<>();

        for(int i =0; i < friendIds.length; i++)
            ids.add(String.valueOf(friendIds[i]));

        return ids;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
