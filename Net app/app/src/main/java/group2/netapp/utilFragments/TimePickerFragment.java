package group2.netapp.utilFragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    OnTimeSetListener listener;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public interface OnTimeSetListener {
        public void onTimePicked(int view, int hourOfDay, int minute);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnTimeSetListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnTimeSetListener");
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        listener.onTimePicked(getArguments().getInt("view"),hourOfDay,minute);
    }
}
