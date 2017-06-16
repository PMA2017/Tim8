package takeyourseat.adapters;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by Nenad on 6/16/2017.
 */

public class RestaurantsListViewAdapter extends SimpleAdapter {
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public RestaurantsListViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = super.getView(position, convertView, parent);

        ImageView img = (ImageView)v.findViewById(R.id.listview_image);
        String url = (String) ((Map)getItem(position)).get("listview_image");
        Picasso.with(v.getContext()).load(url).resize(150,150).onlyScaleDown().centerInside().into(img);
        v.setTag(img);

        TextView nameTextView = (TextView)v.findViewById(R.id.listview_item_title);
        String name = (String) ((Map)getItem(position)).get("listview_title");
        nameTextView.setText(name);
        v.setTag(nameTextView);

        TextView descTextView = (TextView)v.findViewById(R.id.listview_item_short_description);
        String desc = (String) ((Map)getItem(position)).get("listview_description");
        descTextView.setText(desc);
        v.setTag(descTextView);

        return  v;
        }
    }
