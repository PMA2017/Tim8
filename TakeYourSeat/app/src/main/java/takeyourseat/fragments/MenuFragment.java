package takeyourseat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.anica.takeyourseat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import takeyourseat.adapters.ExpandableAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    ExpandableListView expandableListView;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        expandableListView = (ExpandableListView) v.findViewById(R.id.expListView);
        List<String> headings = new ArrayList<String>();
        List<String> L1 = new ArrayList<String>();
        List<String> L2 = new ArrayList<String>();
        List<String> L3 = new ArrayList<String>();
        HashMap<String,List<String>> childList = new HashMap<String, List<String>>();
        String[] heading_items = {"Appetizer","Main course","Desserts"};
        String[] l1 = {"1","2","3"};
        String[] l2 = {"4","5","6"};
        String[] l3 = {"7","8","9"};
        for(String title : heading_items)
        {
            headings.add(title);
        }

        for (String title : l1)
        {
            L1.add(title);
        }
        for (String title : l2)
        {
            L2.add(title);
        }
        for (String title : l3)
        {
            L3.add(title);
        }

        childList.put(headings.get(0),L1);
        childList.put(headings.get(1),L2);
        childList.put(headings.get(2),L3);

        ExpandableAdapter adapter = new ExpandableAdapter(getActivity(),headings,childList);
        expandableListView.setAdapter(adapter);
        return v;
    }

}
