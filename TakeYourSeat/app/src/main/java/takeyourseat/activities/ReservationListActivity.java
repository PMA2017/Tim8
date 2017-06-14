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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anica.takeyourseat.R;

import takeyourseat.dialogs.DeleteDialog;

public class ReservationListActivity extends AppCompatActivity {


    private ListView resList;
    private AlertDialog dialogDel;
    private AlertDialog dialogRes;
    private TextView resRes;
    private TextView resDate;
    private TextView resTime;
    private EditText invitedFriends;
    private boolean showDeleteDialog;
    private boolean showResDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        if(savedInstanceState != null) {
            showDeleteDialog = savedInstanceState.getBoolean("showDeleteDialog");
            showResDetail = savedInstanceState.getBoolean("showResDetail");
        }
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
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewRes: {
               showResDetail();

            }
            case R.id.delRes: {
                showDeleteDialog();
            }
        }
            return super.onContextItemSelected(item);

    }
    private void showDeleteDialog(){
        showDeleteDialog = true;
        showResDetail = false;
        dialogDel = new DeleteDialog(ReservationListActivity.this).prepareDialog();
        dialogDel.show();
    }

    private void showResDetail() {
        showResDetail = true;
        showDeleteDialog = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = LayoutInflater.from(this).inflate(R.layout.activity_reservation_detail, null);
        builder.setView(mView);
        dialogRes = builder.create();
        dialogRes.show();
        resRes = (TextView) mView.findViewById(R.id.resNameText);
        resDate = (TextView) mView.findViewById(R.id.resDateText);
        resTime = (TextView) mView.findViewById(R.id.resTimeText);
        invitedFriends = (EditText) mView.findViewById(R.id.resFriendsText);
        invitedFriends.setEnabled(false);
        invitedFriends.setFocusable(false);
        invitedFriends.setClickable(false);

        resRes.setText("Restaurant 1");
        resDate.setText("12-05-2017");
        resTime.setText("16:00");
        invitedFriends.setText("Marko,Janko,Goran");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (showDeleteDialog) {
            showDeleteDialog();
        }
        if(showResDetail) {
            showResDetail();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(dialogDel != null && dialogDel.isShowing()) {
            outState.putBoolean("showDeleteDialog",showDeleteDialog);
            outState.putBoolean("showResDetail",showResDetail);
        } else if(dialogRes != null && dialogRes.isShowing()) {
            outState.putBoolean("showResDetail",showResDetail);
            outState.putBoolean("showDeleteDialog",showDeleteDialog);
        }
    }
}


