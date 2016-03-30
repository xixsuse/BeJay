package rocks.itsnotrocketscience.bejay.event.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.base.BaseFragment;
import rocks.itsnotrocketscience.bejay.dagger.ActivityComponent;

/**
 * Created by sirfunkenstine on 22/03/16.
 */
public class EventCreateFragment extends BaseFragment<ActivityComponent> implements View.OnClickListener, EventCreateContract.EventCreateView {

    @Inject EventCreateContract.EventCreatePresenter presenter;

    @Bind(R.id.etTitle) EditText etTitle;
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
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        setRetainInstance(true);
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
                break;
            case R.id.tvEndDate:
                break;
            case R.id.tvEndTime:
                break;
            case R.id.tvStartTime:
                break;
        }

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

}
