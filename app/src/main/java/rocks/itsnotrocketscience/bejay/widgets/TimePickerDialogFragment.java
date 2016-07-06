package rocks.itsnotrocketscience.bejay.widgets;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.managers.DateTimeUtils;

/**
 * Created by sirfunkenstine on 03/04/16.
 */
public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    DateTimeSetListener dateSetListener;
    private int id = -1;

    @Override public void onTimeSet(TimePicker view, int hour, int minute) {
        if (dateSetListener != null) {
            String time = DateTimeUtils.getFormattedLocalTime(hour, minute);
            dateSetListener.onDateSet(id, time);
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