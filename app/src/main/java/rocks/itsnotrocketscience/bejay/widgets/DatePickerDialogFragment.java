package rocks.itsnotrocketscience.bejay.widgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import org.joda.time.DateTime;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by sirfunkenstine on 03/04/16.
 */
public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    DateTimeSetListener dateSetListener;
    private int id = -1;

    @Inject
    public DatePickerDialogFragment() {}

    public void setDateSetListener(DateTimeSetListener dateSetListener) {
        this.dateSetListener = dateSetListener;
    }

    public void setId(int id){
        this.id=id;
    }

    @NonNull @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override public void onDateSet(DatePicker view, int year, int month, int day) {
        if (dateSetListener != null) {
            DateTime dateTime = new DateTime(year,month,day,0,0);
            dateSetListener.onDateSet(id, dateTime.toLocalDate().toString());
        }
    }

    public static DatePickerDialogFragment newInstance() {
        return new DatePickerDialogFragment();
    }
}