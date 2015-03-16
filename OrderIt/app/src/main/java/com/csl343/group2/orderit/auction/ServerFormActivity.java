package com.csl343.group2.orderit.auction;





import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.csl343.group2.orderit.R;
import com.csl343.group2.orderit.utilFragments.DatePickerFragment;
import com.csl343.group2.orderit.utilFragments.TimePickerFragment;

import java.util.Calendar;
import java.util.Date;

public class ServerFormActivity extends FragmentActivity implements TimePickerFragment.OnTimeSetListener, DatePickerFragment.OnDateSetListener{

    TextView aucEndTime,aucEndDate,aucExpTime,aucExpDate;
    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_form);
        String time = String.format("%2d:%2d",c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
        aucEndTime = (TextView) findViewById(R.id.auc_end_time);
        aucEndTime.setText(time);
        aucEndTime.setOnClickListener(showTimePickerDialog);

        aucEndDate = (TextView) findViewById(R.id.auc_end_date);
        aucEndDate.setText(c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.MONTH) + "-"  + c.get(Calendar.YEAR)) ;
        aucEndDate.setOnClickListener(showDatePickerDialog);

        aucExpTime = (TextView) findViewById(R.id.auc_exp_time);
        aucExpTime.setText(c.get(Calendar.HOUR_OF_DAY) + ":"  + c.get(Calendar.MINUTE));
        aucExpTime.setOnClickListener(showTimePickerDialog);

        aucExpDate = (TextView) findViewById(R.id.auc_exp_date);
        aucExpDate.setText(c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.MONTH) + "-"  + c.get(Calendar.YEAR)) ;
        aucExpDate.setOnClickListener(showDatePickerDialog);

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
            String tag;
            if(v.getId()==R.id.auc_end_time){
                tag = "endtimepicker";
            }else{
                tag = "exptimepicker";
            }
            TimePickerFragment time = (TimePickerFragment) getSupportFragmentManager().findFragmentByTag(tag);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(time==null) {
                time = new TimePickerFragment();
                Bundle b = new Bundle();
                b.putInt("view",v.getId());
                time.setArguments(b);
                ft.add(time,tag);
            }

            ft.show(time);
            ft.commit();
        }
    };

    View.OnClickListener showDatePickerDialog = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String tag;
            if(v.getId()==R.id.auc_end_time){
                tag = "enddatepicker";
            }else{
                tag = "expdatepicker";
            }
            DatePickerFragment date = (DatePickerFragment) getSupportFragmentManager().findFragmentByTag(tag);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (date == null) {
                date = new DatePickerFragment();
                Bundle b = new Bundle();
                b.putInt("view",v.getId());
                date.setArguments(b);
                ft.add(date, tag);
            }

            ft.show(date);
            ft.commit();
        }
    };

    @Override
    public void onTimePicked(int view,int hourOfDay, int minute) {
        String time = String.format("%2d:%2d",hourOfDay,minute);
        switch(view){
            case R.id.auc_end_time:aucEndTime.setText(time);break;
            case R.id.auc_exp_time:aucExpTime.setText(time);break;
        }

    }

    @Override
    public void onDatePicked(int view,int year, int monthOfYear, int dayOfMonth) {
        String date = String.format("%2d-%2d-%4d",dayOfMonth,monthOfYear,year);
        switch(view){
            case R.id.auc_end_date:aucEndDate.setText(date);break;
            case R.id.auc_exp_date:aucExpDate.setText(date);break;
        }
    }

}
