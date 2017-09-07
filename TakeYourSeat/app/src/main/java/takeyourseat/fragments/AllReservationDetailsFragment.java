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
    private int counter, friendCounter, reservationId;

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
        counter = 0;
        friendCounter = 0;

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < tableIds.size(); i++) {
                    //ovde treba dodati samo jednu rezervaciju
                    //i vise torki u tablereservation gde ce reservationid biti isti (ukoliko ima vise stolova)
                    apiService.insertReservation(createReservation()).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()) {
                                reservationId = Integer.valueOf(response.body().replaceAll("\\.0*$", ""));
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("error", "error in inserting reservation");
                        }
                    });

//                    for(int i = 0; i < tableIds.size(); i++) {
//                        counter++;
//                        if(counter == tableIds.size()) {
//                            for(int j = 0; j < friendIds.length; j++) {
//                                friendCounter++;
//                                if(friendCounter == friendIds.length) {
//                                    //otici na home activity
//                                }
//                                else {
//                                    apiService.insertReservationFriends(createReservationFriends(reservationId, friendIds[j])).enqueue(new Callback<String>() {
//                                        @Override
//                                        public void onResponse(Call<String> call, Response<String> response) {
//                                            Log.d("ins", "Inserted reservationfriends");
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<String> call, Throwable t) {
//                                            Log.e("error", "error in inserting reservationfriends");
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                        else {
//                            apiService.insertTableReservation(createTableReservation(Integer.valueOf(tableIds.get(i)), reservationId)).enqueue(new Callback<String>() {
//                                @Override
//                                public void onResponse(Call<String> call, Response<String> response) {
//                                    Log.d("ins", "Inserted tablereservation");
//                                }
//
//                                @Override
//                                public void onFailure(Call<String> call, Throwable t) {
//                                    Log.e("error", "error in inserting tablereservation");
//                                }
//                            });
//                        }
//                    }
//
//                }

//                    apiService.insertReservation(createReservation(Integer.valueOf(tableIds.get(i)))).enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            if(response.isSuccessful()) {
//                                int id = Integer.valueOf(response.body().replaceAll("\\.0*$", ""));
//                                apiService.insertReservationFriends(createReservationFriends(id)).enqueue(new Callback<String>() {
//                                    @Override
//                                    public void onResponse(Call<String> call, Response<String> response) {
//                                        counter++;
//                                        if(counter == tableIds.size()) {
//                                            Toast.makeText(getActivity(), "Reservation successfully finished.", Toast.LENGTH_LONG);
//                                            Intent intent = new Intent(getActivity(), HomePageActivity.class);
//                                            startActivity(intent);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<String> call, Throwable t) {
//                                        Log.e("Detail", "Error inserting ReservationFriends using API");
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
//                            Log.e("Detail", "error loading from API");
//                        }
//                    });
                }
            }
        });

        return v;
    }

    private Reservation createReservation() {
        Reservation res = new Reservation();

        res.setUser(userId);
        res.setStartDate(getArguments().getString("startDate"));
        res.setEndDate(getArguments().getString("endDate"));

        return res;
    }

    private ReservationFriends createReservationFriends(int reservationId, int friendId) {
        ReservationFriends reservationFriends = new ReservationFriends();

        reservationFriends.setReservationId(reservationId);
        reservationFriends.setUserId(friendId);
        reservationFriends.setStatus(EnumValues.SENT_REQUEST_STATUS);

        return reservationFriends;
    }

    private TableReservation createTableReservation(int tableId, int reservationId) {
        TableReservation tableReservation = new TableReservation();

        tableReservation.setTableId(tableId);
        tableReservation.setReservationId(reservationId);

        return tableReservation;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
