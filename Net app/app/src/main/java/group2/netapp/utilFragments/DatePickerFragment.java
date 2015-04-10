package group2.netapp.utilFragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    OnDateSetListener listener;


    public DatePickerFragment() {
        // Required empty public constructor
    }



    public interface OnDateSetListener {
        public void onDatePicked(int view, int year, int monthOfYear, int dayOfMonth);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year,month,day);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnDateSetListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnDateSetListener");
        }
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        listener.onDatePicked(getArguments().getInt("view"),year,monthOfYear,dayOfMonth);
    }
}
