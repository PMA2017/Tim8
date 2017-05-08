package takeyourseat.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Button;

import com.example.anica.takeyourseat.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ReservationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button dateButton;
    private EditText dateRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        dateButton = (Button) findViewById(R.id.dateButton);
        dateRes = (EditText) findViewById(R.id.dateRes);
    }


    public void datePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");

    }

    private void setDate(final Calendar calendar) {
        final DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        dateRes.setText(df.format(calendar.getTime()));
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year,month,dayOfMonth);
        setDate(cal);
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);

        }
    }
}
