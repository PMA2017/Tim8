package takeyourseat.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import takeyourseat.model.ReservationTable;
import takeyourseat.model.Restaurant;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationTablesFragment extends Fragment {

    private Button next, btn;
    private RelativeLayout relativeLayout;
    private GridLayout gridLayout;
    private ArrayList<Button> tableButtons = new ArrayList<Button>();
    private ArrayList<Button> chosenTables = new ArrayList<Button>();
    private ArrayList<ReservationTable> allTables = new ArrayList<ReservationTable>();
    private ArrayList<ReservationTable> availableTables = new ArrayList<ReservationTable>();
    private ArrayList<ReservationTable> unavailableTables = new ArrayList<ReservationTable>();
    private ApiService apiService;
    private String apiDate, apiTime;

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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReservationOtherDetailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        relativeLayout = (RelativeLayout) v.findViewById(R.id.tableRelative);

        SharedPreferences prefs = this.getActivity().getSharedPreferences("resDetails", Context.MODE_PRIVATE);
        apiDate = prefs.getString("date", null); //dd-mm-yyy
        apiTime = prefs.getString("time", null); //hh:mm
        SharedPreferences resPrefs = this.getActivity().getSharedPreferences("restaurantId", Context.MODE_PRIVATE);
        int restaurantId = resPrefs.getInt("resId", 0);

        apiService = ApiUtils.getApiService();
        apiService.getReservationTables(restaurantId).enqueue(new Callback<List<ReservationTable>>() {
            @Override
            public void onResponse(Call<List<ReservationTable>> call, Response<List<ReservationTable>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        allTables.add(response.body().get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ReservationTable>> call, Throwable t) {
                Log.e("Detail", "error loading from API");
            }
        });

        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            View view = relativeLayout.getChildAt(i);
            if (view instanceof GridLayout) {
                gridLayout = (GridLayout) view;
                break;
            }
        }

        String apiYear = apiDate.split("-")[2];
        String apiMonth = apiDate.split("-")[1];
        String apiDay = apiDate.split("-")[0];

        String apiHour = apiTime.substring(0, 1);
        String apiMinute = apiTime.substring(3, 4);

        int apiHourInt = Integer.parseInt(apiHour);

        for(int i = 0; i < allTables.size(); i++) {
            //iz baze: yyyy-mm-ddThh:mm:ss
            //sa api-ja: dd-mm-yyyy i hh:mm
            String dbDate = allTables.get(i).getStartDate().split("T")[0];
            String dbTime = allTables.get(i).getStartDate().split("T")[1];

            String dbYear = dbDate.split("-")[0];
            String dbMonth = dbDate.split("-")[1];
            String dbDay = dbDate.split("-")[2];

            String dbHour = dbTime.substring(0, 1);
            String dbMinute = dbTime.substring(3, 4);
            int dbHourInt = Integer.parseInt(dbHour);
            int dbHourLastInt = Integer.parseInt(dbHour) + 3;

            if(dbYear.equals(apiYear) && dbMonth.equals(apiMonth) && dbDay.equals(apiDay)) {
                if(apiHourInt < dbHourLastInt && apiHourInt > dbHourInt)
                    unavailableTables.add(allTables.get(i));
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
                        btn.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    }
                    else {
                        btn.setBackgroundColor(Color.parseColor("#8D6E63"));
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Button clickedBtn = (Button)v;
                                if(!ifExists(clickedBtn)) {
                                    clickedBtn.setBackgroundColor(Color.parseColor("#58D68D"));
                                    chosenTables.add(clickedBtn);
                                }
                                else {
                                    clickedBtn.setBackgroundColor(Color.parseColor("#8D6E63"));
                                    chosenTables.remove(clickedBtn);
                                }
                            }
                        });
                    }
                }
            }
        }

        return v;
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
            if(tableName.equals("Table " + unavailableTables.get(i).getNumber()))
                ifExists = true;
        }

        return ifExists;
    }

}
