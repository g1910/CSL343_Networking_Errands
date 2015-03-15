package com.csl343.group2.orderit.auction;





import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.csl343.group2.orderit.R;
import com.csl343.group2.orderit.utilFragments.TimePickerFragment;

import java.util.Calendar;
import java.util.Date;

public class ServerFormActivity extends FragmentActivity implements TimePickerFragment.OnTimeSetListener{

    TextView aucEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_form);
        aucEndTime = (TextView) findViewById(R.id.auc_end_time);
        aucEndTime.setText(Calendar.getInstance().getTime().toString());
        aucEndTime.setOnClickListener(showTimePickerDialog);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_server_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener showTimePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TimePickerFragment time = (TimePickerFragment) getSupportFragmentManager().findFragmentByTag("timepicker");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(time==null) {
                time = new TimePickerFragment();
                ft.add(time,"timepicker");
            }

            ft.show(time);
            ft.commit();
        }
    };



    @Override
    public void onTimePicked(int hourOfDay, int minute) {
        aucEndTime.setText(hourOfDay + ":" + minute);
    }
}
