package takeyourseat.activities;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.anica.takeyourseat.R;

public class ReservationListActivity extends AppCompatActivity {


    ListView resList;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        resList = (ListView) findViewById(R.id.listReservations);
        String[] items = {"Reservation 1","Reservation 2","Reservation 3","Reservation 4","Reservation 5","Reservation 6","Reservation 7","Reservation 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.reservation_list_item,R.id.reservationTextView, items);
        resList.setAdapter(adapter);
        registerForContextMenu(resList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.reservation_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.viewRes:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View mView = LayoutInflater.from(this).inflate(R.layout.activity_reservation_detail,null);
                builder.setView(mView);
                dialog = builder.create();
                dialog.show();
        }
        return super.onContextItemSelected(item);
    }
}
