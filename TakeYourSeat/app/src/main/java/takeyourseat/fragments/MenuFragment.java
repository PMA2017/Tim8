package takeyourseat.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.adapters.ExpandableAdapter;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.MenuItem;
import takeyourseat.model.ReservationTable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    ExpandableListView expandableListView;
    private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    private ApiService apiService;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        expandableListView = (ExpandableListView) v.findViewById(R.id.expListView);

        SharedPreferences resPrefs = this.getActivity().getSharedPreferences("restaurantId", Context.MODE_PRIVATE);
        int restaurantId = resPrefs.getInt("resId", 0);

        apiService = ApiUtils.getApiService();
        apiService.getMenuItemsForRestaurant(String.valueOf(restaurantId)).enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        menuItems.add(response.body().get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                Log.e("Detail", "error loading from API");
            }
        });

        List<String> headings = new ArrayList<String>();

        List<String> appetizerList = new ArrayList<String>();
        List<String> mainList = new ArrayList<String>();
        List<String> dessertList = new ArrayList<String>();

        HashMap<String,List<String>> childList = new HashMap<String, List<String>>();

        String[] heading_items = {"Appetizer","Main course","Desserts"};

        for(int i = 0; i < menuItems.size(); i++) {
            if(menuItems.get(i).getCategory() == 1)
                appetizerList.add(menuItems.get(i).getName());
            if(menuItems.get(i).getCategory() == 2)
                mainList.add(menuItems.get(i).getName());
            if(menuItems.get(i).getCategory() == 3)
                dessertList.add(menuItems.get(i).getName());
        }

        for(String title : heading_items)
        {
            headings.add(title);
        }

        childList.put(headings.get(0),appetizerList);
        childList.put(headings.get(1),mainList);
        childList.put(headings.get(2),dessertList);

        ExpandableAdapter adapter = new ExpandableAdapter(getActivity(),headings,childList);
        expandableListView.setAdapter(adapter);
        return v;
    }

}
