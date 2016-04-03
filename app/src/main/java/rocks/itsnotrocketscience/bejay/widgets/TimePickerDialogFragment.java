package rocks.itsnotrocketscience.bejay.widgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by sirfunkenstine on 03/04/16.
 */
public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    DateTimeSetListener dateSetListener;
    private int id = -1;

    @Override public void onTimeSet(TimePicker view, int hour, int minute) {
        if (dateSetListener != null) {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("hh mm a");
            DateTime dateTime = new DateTime(1,1,1,hour,minute);
            dateSetListener.onDateSet(id, dateTime.toLocalTime().toString(fmt));
        }
    }

    @Inject
    public TimePickerDialogFragment() {}

    public void setTimeSetListener(DateTimeSetListener dateSetListener) {
        this.dateSetListener = dateSetListener;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        return new TimePickerDialog(getActivity(), this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
    }

    public static TimePickerDialogFragment newInstance() {
        return new TimePickerDialogFragment();
    }
}