package takeyourseat.fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.Reservation;
import takeyourseat.model.ReservationTable;
import takeyourseat.model.Restaurant;
import takeyourseat.model.RestaurantTable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationTablesFragment extends Fragment {

    private Button next, btn;
    private RelativeLayout relativeLayout;
    private GridLayout gridLayout;
    private ArrayList<Button> tableButtons = new ArrayList<Button>();
    private ArrayList<Button> chosenTables = new ArrayList<Button>();
    private ArrayList<ReservationTable> allReservationTables = new ArrayList<ReservationTable>();
    private ArrayList<ReservationTable> unavailableTables = new ArrayList<ReservationTable>();
    private ArrayList<ReservationTable> availableTables = new ArrayList<ReservationTable>();
    private ArrayList<RestaurantTable> allRestaurantTables = new ArrayList<RestaurantTable>();
    private ApiService apiService;
    private String apiDate, apiTime;
    private int restaurantId, userId, counter;

    public ReservationTablesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reservation_tables, container, false);

        next = (Button) v.findViewById(R.id.next2);
        apiService = ApiUtils.getApiService();

        SharedPreferences prefs = this.getActivity().getSharedPreferences("resDetails", Context.MODE_PRIVATE);
        apiDate = getActivity().getIntent().getExtras().getString("date", null); //dd-mm-yyy
        apiTime = getActivity().getIntent().getExtras().getString("time", null); //hh:mm
        restaurantId = getActivity().getIntent().getExtras().getInt("id", 0);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiYear = apiDate.split("-")[2];
                String apiMonth = apiDate.split("-")[1];
                String apiDay = apiDate.split("-")[0];

                String apiHour = apiTime.substring(0, 1);
                String apiMinute = apiTime.substring(3, 4);

                counter = 0;

                for(int i = 0; i < chosenTables.size(); i++) {
                    Reservation res = new Reservation();
                    res.setUser(userId);
                    res.setRestaurantTable(chosenTables.get(i).getId());
                    res.setStartDate(generateStartDateString(apiYear, apiMonth, apiDay, apiHour, apiMinute)); //yyyy-mm-ddThh:mm:ss
                    res.setEndDate(generateEndDateString(apiYear, apiMonth, apiDay, apiHour, apiMinute));
                    apiService.insertReservation(res).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            counter++;
                            if(counter == chosenTables.size()) {
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.content_frame, new InviteFriendsFragment());
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });

        relativeLayout = (RelativeLayout) v.findViewById(R.id.tableRelative);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("id", -1);

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

                                    handleTableView(apiDate, apiTime);
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

    private void handleTableView(String apiDate, String apiTime) {

        String apiYear = apiDate.split("-")[2];
        String apiMonth = apiDate.split("-")[1];
        String apiDay = apiDate.split("-")[0];

        String apiHour = apiTime.substring(0, 1);
        String apiMinute = apiTime.substring(3, 4);

        int apiHourInt = Integer.parseInt(apiHour);
        int apiHourIntLast = apiHourInt + 3;

        for(int i = 0; i < allReservationTables.size(); i++) {
            //iz baze: yyyy-mm-ddThh:mm:ss
            //sa api-ja: dd-mm-yyyy i hh:mm
            String dbDate = allReservationTables.get(i).getStartDate().split("T")[0];
            String dbTime = allReservationTables.get(i).getStartDate().split("T")[1];

            String dbYear = dbDate.split("-")[0];
            String dbMonth = dbDate.split("-")[1];
            String dbDay = dbDate.split("-")[2];

            String dbHour = dbTime.substring(0, 1);
            String dbMinute = dbTime.substring(3, 4);
            int dbHourInt = Integer.parseInt(dbHour);
            int dbHourLastInt = Integer.parseInt(dbHour) + 3;

            if(dbYear.equals(apiYear) && dbMonth.equals(apiMonth) && dbDay.equals(apiDay)) {
                if((apiHourInt < dbHourLastInt && apiHourInt > dbHourInt) || (apiHourIntLast > dbHourInt))
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
                    if(ifExistsInUnavailableList(btn.getText().toString())) { //ako postoji u listi zauzetih
                        btn.setEnabled(false);
                        btn.setBackgroundColor(Color.parseColor("#D12121")); //crvena boja
                    }
                    else if (ifExistsInAvailableList(btn.getText().toString())){ //ako postoji u listi slobodnih, odnosno u listi stolova za taj restoran
                        btn.setBackgroundColor(Color.parseColor("#8D6E63")); //braon boja
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Button clickedBtn = (Button)v;
                                if(!ifExists(clickedBtn)) { //ako ne postoji u listi dodati ga
                                    clickedBtn.setBackgroundColor(Color.parseColor("#58D68D")); //zelena boja
                                    chosenTables.add(clickedBtn);
                                }
                                else { //ako postoji, obrisati ga
                                    clickedBtn.setBackgroundColor(Color.parseColor("#8D6E63"));
                                    chosenTables.remove(clickedBtn);
                                }
                            }
                        });

                    }
                    else { //ako uopste ne postoji za dati restoran
                        btn.setEnabled(false);
                        btn.setBackgroundColor(Color.parseColor("#DDDDDD")); //siva boja
                    }
                }
            }
        }
    }


    private String generateStartDateString(String year, String month, String day, String hour, String minute) {
        return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":00";
    }

    private String generateEndDateString(String year, String month, String day, String hour, String minute) {
        String newDay = "";
        String newHour = "";
        if(Integer.parseInt(hour) + 3 > 24) {
            newDay = String.valueOf(Integer.parseInt(day) + 1);
            newHour = String.valueOf(Integer.parseInt(hour) + 3 - 24);
            return year + "-" + month + "-" + newDay + "T" + newHour + ":" + minute + ":00";
        }
        return year + "-" + month + "-" + day + "T" + String.valueOf(Integer.parseInt(hour) + 3) + ":" + minute + ":00";
    }

    private boolean ifExists(Button btn) {
        boolean ifExists = false;

        for (int i = 0; i < chosenTables.size(); i++) {
            if (btn.getId() == chosenTables.get(i).getId()) {
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

}
