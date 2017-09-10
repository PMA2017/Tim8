package takeyourseat.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.example.anica.takeyourseat.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.db.DatabaseHelper;
import takeyourseat.model.Reservation;
import takeyourseat.model.ReservationTable;
import takeyourseat.model.RestaurantTable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationTablesFragment extends Fragment {

    private Button next, btn;
    private RelativeLayout relativeLayout;
    private GridLayout gridLayout;
    private ArrayList<Button> tableButtons = new ArrayList<Button>();
    private ArrayList<Button> chosenTableButtons = new ArrayList<Button>();

    private ArrayList<ReservationTable> allReservationTables = new ArrayList<ReservationTable>();
    private ArrayList<ReservationTable> unavailableTables = new ArrayList<ReservationTable>();
    private ArrayList<ReservationTable> availableTables = new ArrayList<ReservationTable>();
    private ArrayList<RestaurantTable> allRestaurantTables = new ArrayList<RestaurantTable>();
    private ArrayList<RestaurantTable> chosenTables = new ArrayList<RestaurantTable>();

    private ApiService apiService;
    private String apiDate, apiTime;
    private int restaurantId, userId, counter;
    private DatabaseHelper databaseHelper;
    private Date date;

    public final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static final long HOUR = 3600*1000;

    public ReservationTablesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation_tables, container, false);

        next = (Button) v.findViewById(R.id.next2);
        apiService = ApiUtils.getApiService();

        apiDate = getActivity().getIntent().getExtras().getString("date", null); //yyyy-mm-dd
        apiTime = getActivity().getIntent().getExtras().getString("time", null); //hh:mm
        restaurantId = getActivity().getIntent().getExtras().getInt("id", 0);

        try {
            date = formatter.parse(apiDate + "T" + apiTime + ":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userId = getDatabaseHelper().getCurrentUser().getId();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> tableIds = new ArrayList<String>();
                getChosenTablesFromButtons();

                for(int i = 0; i < chosenTables.size(); i++) {
                    tableIds.add(String.valueOf(chosenTables.get(i).getId()));
                }

                Bundle args = new Bundle();
                args.putString("startDate", formatter.format(date));
                args.putString("endDate", formatter.format(new Date(date.getTime() + 3 * HOUR)));
                args.putStringArrayList("tableIds", tableIds);

                InviteFriendsFragment fragment = new InviteFriendsFragment();
                fragment.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        relativeLayout = (RelativeLayout) v.findViewById(R.id.tableRelative);

        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            View view = relativeLayout.getChildAt(i);
            if (view instanceof GridLayout) {
                gridLayout = (GridLayout) view;
                break;
            }
        }

        try {
            apiService.getReservationTables(restaurantId).enqueue(new Callback<List<ReservationTable>>() {
                @Override
                public void onResponse(Call<List<ReservationTable>> call, Response<List<ReservationTable>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            allReservationTables.add(response.body().get(i));
                        }

                        apiService.getRestaurantTables(String.valueOf(restaurantId)).enqueue(new Callback<List<RestaurantTable>>() {
                            @Override
                            public void onResponse(Call<List<RestaurantTable>> call, Response<List<RestaurantTable>> response) {
                                if (response.isSuccessful()) {
                                    for (int i = 0; i < response.body().size(); i++) {
                                        allRestaurantTables.add(response.body().get(i));
                                    }
                                    handleTableView();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<RestaurantTable>> call, Throwable t) {
                                Log.e("Detail", "error loading from API");
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<ReservationTable>> call, Throwable t) {
                    Log.e("Detail", "error loading from API");
                }
            });
        } catch (Exception e) {

        }

        return v;
    }

    private void handleTableView() {
        for(int i = 0; i < allReservationTables.size(); i++) {
            Date startDate = new Date();

            try {
                startDate = formatter.parse(allReservationTables.get(i).getStartDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(!startDate.after(date) && !(new Date(startDate.getTime() + 3 * HOUR)).before(date)) {
                unavailableTables.add(allReservationTables.get(i));
            }
        }

        for(int i = 0; i < allRestaurantTables.size(); i++) {
            if(!ifTableIsUnavailable(allRestaurantTables.get(i).getNumber())) {
                ReservationTable table = new ReservationTable();
                table.setId(allRestaurantTables.get(i).getId());
                table.setNumber(allRestaurantTables.get(i).getNumber());
                availableTables.add(table);
            }
        }

        for (int j = 0; j < gridLayout.getChildCount(); j++) {
            View view = gridLayout.getChildAt(j);
            if (view instanceof Button) {
                btn = (Button) view;
                if (btn.getText().toString().toLowerCase().contains("table")) {
                    tableButtons.add(btn);
                    if(ifExistsInUnavailableList(btn.getText().toString())) {
                        btn.setEnabled(false);
                        btn.setBackgroundColor(Color.parseColor("#D12121"));
                    }
                    else if (ifExistsInAvailableList(btn.getText().toString())){
                        btn.setBackgroundColor(Color.parseColor("#8D6E63"));
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Button clickedBtn = (Button)v;
                                if(!ifExists(clickedBtn)) {
                                    clickedBtn.setBackgroundColor(Color.parseColor("#58D68D"));
                                    chosenTableButtons.add(clickedBtn);
                                }
                                else {
                                    clickedBtn.setBackgroundColor(Color.parseColor("#8D6E63"));
                                    chosenTableButtons.remove(clickedBtn);
                                }
                            }
                        });
                    }
                    else {
                        btn.setEnabled(false);
                        btn.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    }
                }
            }
        }
    }

    private boolean ifExists(Button btn) {
        boolean ifExists = false;

        for (int i = 0; i < chosenTableButtons.size(); i++) {
            if (btn.getId() == chosenTableButtons.get(i).getId()) {
                ifExists = true;
                break;
            }
        }

        return ifExists;
    }

    private boolean ifExistsInUnavailableList(String tableName) {
        boolean ifExists = false;

        for(int i = 0; i < unavailableTables.size(); i++) {
            if(tableName.equals("Table " + unavailableTables.get(i).getNumber())) {
                ifExists = true;
                break;
            }
        }

        return ifExists;
    }

    private boolean ifExistsInAvailableList(String tableName) {
        boolean ifExists = false;

        for(int i = 0; i < availableTables.size(); i++) {
            if(tableName.equals("Table " + availableTables.get(i).getNumber())) {
                ifExists = true;
                break;
            }
        }

        return ifExists;
    }

    private boolean ifTableIsUnavailable(int number) {
        boolean ifExists = false;

        for(int i = 0; i < unavailableTables.size(); i++) {
            if(number == unavailableTables.get(i).getNumber()) {
                ifExists = true;
                break;
            }
        }
        return ifExists;
    }

    private void getChosenTablesFromButtons() {
        for(int i = 0; i < allRestaurantTables.size(); i++) {
            if(ifExistsInChosenTables(allRestaurantTables.get(i).getNumber()))
                chosenTables.add(allRestaurantTables.get(i));
        }
    }

    private boolean ifExistsInChosenTables(int number) {
        boolean ifExists = false;

        for(int i = 0; i < chosenTableButtons.size(); i++) {
            if(("Table " + number).equals(chosenTableButtons.get(i).getText())) {
                ifExists = true;
                break;
            }
        }
        return ifExists;
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
