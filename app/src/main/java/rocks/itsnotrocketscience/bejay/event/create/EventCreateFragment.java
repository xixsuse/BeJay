package rocks.itsnotrocketscience.bejay.event.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;


import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.widgets.DatePickerDialogFragment;
import rocks.itsnotrocketscience.bejay.widgets.DateTimeSetListener;
import rocks.itsnotrocketscience.bejay.widgets.TimePickerDialogFragment;

/**
 * Created by sirfunkenstine on 22/03/16.
 */
public class EventCreateFragment extends BaseFragment<ActivityComponent> implements View.OnClickListener, EventCreateContract.EventCreateView, DateTimeSetListener {

    @Inject EventCreateContract.EventCreatePresenter presenter;
    @Inject DatePickerDialogFragment datePickerDialogFragment;
    @Inject TimePickerDialogFragment timePickerDialogFragment;

    private final static int START_DATE = 0;
    private final static int START_TIME = 1;
    private final static int END_DATE = 2;
    private final static int END_TIME = 3;
    Event event;

    @Bind(R.id.etTitle) EditText etTitle;
    @Bind(R.id.etPlace) EditText etPlace;
    @Bind(R.id.etGPS) EditText etGPS;
    @Bind(R.id.tvStartDate) TextView tvStartDate;
    @Bind(R.id.tvStartTime) TextView tvStartTime;
    @Bind(R.id.tvEndDate) TextView tvEndDate;
    @Bind(R.id.tvEndTime) TextView tvEndTime;
    @Bind(R.id.textInputLayoutTitle) TextInputLayout textInputLayoutTitle;

    public static Fragment newInstance() {
        return new EventCreateFragment();
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

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_create_event, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        event = new Event();
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textInputLayoutTitle.setError("Title must be greater then 3 characters");
        RxTextView.textChanges(etTitle)
                .map(inputText -> (inputText.length() != 0))
                .subscribe(isValid -> textInputLayoutTitle.setErrorEnabled(!isValid));
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
                showGPSDialog();
                break;
        }
    }

    private void showGPSDialog() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Choose GpsLocation")
                .setContentText("Use Current Location Or Pick From Map?")
                .setCancelText("Choose Current")
                .setConfirmText("Pick From Map")
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> {
                    sDialog.cancel();
                })
                .setConfirmClickListener(sDialog -> {
                    sDialog.cancel();
                })
                .show();
    }


    private void showDateDialog(int id) {
        datePickerDialogFragment.setDateSetListener(this);
        datePickerDialogFragment.setId(id);
        datePickerDialogFragment.show(getFragmentManager(), "DatePicker");
    }

    private void showTimeDialog(int id) {
        timePickerDialogFragment.setTimeSetListener(this);
        timePickerDialogFragment.setId(id);
        timePickerDialogFragment.show(getFragmentManager(), "DatePicker");
    }

    @Override public void setProgressVisible(boolean visible) {
    }

    @Override public void showError(String error) {
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
                event.setStartDate(datetime);
                tvStartDate.setText(datetime);
                break;
            case END_DATE:
                event.setEndDate(datetime);
                tvEndDate.setText(datetime);
                break;
            case START_TIME:
                event.setStartTime(datetime);
                tvStartTime.setText(datetime);
                break;
            case END_TIME:
                event.setEndTime(datetime);
                tvEndTime.setText(datetime);
                break;
            default:
                break;
        }
    }
}
