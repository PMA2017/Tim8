package takeyourseat.adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import takeyourseat.activities.HomePageActivity;

/**
 * Created by Nenad on 6/16/2017.
 */

public class RestaurantsListViewAdapter extends SimpleAdapter {

    Context mContext;
    int layoutResourceId;


    public RestaurantsListViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);

        this.layoutResourceId = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        Map item = (Map)getItem(position);
        String id = (String)item.get("listview_id");

        ImageView img = (ImageView)convertView.findViewById(R.id.listview_image);
        String url = (String) item.get("listview_image");
        Picasso.with(convertView.getContext()).load(url).resize(150,150).onlyScaleDown().centerInside().into(img);
        img.setTag("resImage" + id);

        TextView nameTextView = (TextView)convertView.findViewById(R.id.listview_item_title);
        String name = (String) item.get("listview_title");
        nameTextView.setText(name);
        nameTextView.setTag("resName" + id);

        TextView descTextView = (TextView)convertView.findViewById(R.id.listview_item_short_description);
        String desc = (String) item.get("listview_description");
        descTextView.setText(desc);
        descTextView.setTag("resDesc" + id);

        convertView.setTag(id);

        return convertView;
        }
    }
