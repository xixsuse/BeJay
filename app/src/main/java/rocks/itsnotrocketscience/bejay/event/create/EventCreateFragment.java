package rocks.itsnotrocketscience.bejay.event.create;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;


import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.managers.DateTimeUtils;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.map.MapActivity;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.widgets.DatePickerDialogFragment;
import rocks.itsnotrocketscience.bejay.widgets.DateTimeSetListener;
import rocks.itsnotrocketscience.bejay.widgets.TimePickerDialogFragment;

/**
 * Created by sirfunkenstine on 22/03/16.
 *
 */
public class EventCreateFragment extends BaseFragment<ActivityComponent> implements View.OnClickListener, EventCreateContract.EventCreateView, DateTimeSetListener {

    @Inject EventCreateContract.EventCreatePresenter presenter;
    @Inject DatePickerDialogFragment datePickerDialogFragment;
    @Inject TimePickerDialogFragment timePickerDialogFragment;
    @Inject Launcher launcher;

    private final static int START_DATE = 0;
    private final static int START_TIME = 1;
    private final static int END_DATE = 2;
    private final static int END_TIME = 3;

    @Bind(R.id.etTitle) EditText etTitle;
    @Bind(R.id.etPlace) EditText etPlace;
    @Bind(R.id.etDetails) EditText etDetails;
    @Bind(R.id.etGPS) EditText etGPS;
    @Bind(R.id.tvStartDate) TextView tvStartDate;
    @Bind(R.id.tvStartTime) TextView tvStartTime;
    @Bind(R.id.tvEndDate) TextView tvEndDate;
    @Bind(R.id.tvEndTime) TextView tvEndTime;

    public static Fragment newInstance() {
        return new EventCreateFragment();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_create, container, false);
        ButterKnife.bind(this, view);
        tvStartDate.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndDate.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        etGPS.setOnClickListener(this);
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create_event, menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_create) {
            presenter.processEvent(getEventObjectFromViews() );
        }
        return super.onOptionsItemSelected(item);
    }

    private Event getEventObjectFromViews() {
        Event event = new Event();
        event.setPlace(etPlace.getText().toString());
        event.setDetails(etDetails.getText().toString());
        event.setTitle(etTitle.getText().toString());
        event.setStartDate(DateTimeUtils.getFormattedDateTime(tvStartDate.getText().toString(), tvStartTime.getText().toString()));
        event.setEndDate(DateTimeUtils.getFormattedDateTime(tvEndDate.getText().toString(), tvEndTime.getText().toString()));
        Double lat = Double.valueOf(etGPS.getText().toString().split("/")[0]);
        Double lng =  Double.valueOf(etGPS.getText().toString().split("/")[1]);
        event.setGps(new LatLng(lat,lng));
        return event;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStartDate:
                showDateDialog(START_DATE);
                break;
            case R.id.tvEndDate:
                showDateDialog(END_DATE);
                break;
            case R.id.tvStartTime:
                showTimeDialog(START_TIME);
                break;
            case R.id.tvEndTime:
                showTimeDialog(END_TIME);
                break;
            case R.id.etGPS:
                launcher.openMapActivityForResult(this);
                break;
        }
    }

    private void showDateDialog(int id) {
        datePickerDialogFragment.setDateSetListener(this);
        datePickerDialogFragment.setId(id);
        datePickerDialogFragment.show(getFragmentManager(), "DatePicker");
    }

    private void showTimeDialog(int id) {
        timePickerDialogFragment.setTimeSetListener(this);
        timePickerDialogFragment.setId(id);
        timePickerDialogFragment.show(getFragmentManager(), "TimePicker");
    }

    @Override public void setProgressVisible(boolean visible) {
    }

    @Override public void showError(String error, int resource) {
        ((TextView) ButterKnife.findById(getActivity(), resource)).setError(error);
    }

    @Override public void showToastError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override public void onInitialDateSet(String date) {
        tvStartDate.setText(date);
    }

    @Override public void onInitialTimeSet(String time) {
        tvStartTime.setText(time);
    }

    @Override public void finish() {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), "Event Created", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    @Override public void onResume() {
        super.onResume();
        presenter.onViewAttached(this);
    }

    @Override public void onPause() {
        super.onPause();
        presenter.onViewDetached();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override public void onDateSet(int id, String datetime) {
        switch (id) {
            case START_DATE:
                tvStartDate.setText(datetime);
                break;
            case END_DATE:
                tvEndDate.setText(datetime);
                break;
            case START_TIME:
                tvStartTime.setText(datetime);
                break;
            case END_TIME:
                tvEndTime.setText(datetime);
                break;
            default:
                break;
        }
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            etPlace.setText(data.getStringExtra(MapActivity.PLACE));
            LatLng latLng = data.getParcelableExtra(MapActivity.POSITION);
            etGPS.setText(String.format("%s/%s", latLng.latitude, latLng.longitude));
        }
    }
}
