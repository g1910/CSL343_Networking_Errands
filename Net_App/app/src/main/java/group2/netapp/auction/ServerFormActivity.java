package group2.netapp.auction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import group2.netapp.R;
import group2.netapp.utilFragments.DatePickerFragment;
import group2.netapp.utilFragments.ServerConnect;
import group2.netapp.utilFragments.TimePickerFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServerFormActivity extends FragmentActivity implements TimePickerFragment.OnTimeSetListener, DatePickerFragment.OnDateSetListener, ServerConnect.OnResponseListener{

    TextView aucEndTime,aucEndDate,aucExpTime,aucExpDate;
    EditText aucDesc, aucLocation;
    Button aucStartBtn;
    Activity a = this;
    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_form);
        setUpForm();
    }

    public void setUpForm(){
        aucDesc = (EditText) findViewById(R.id.auction_desc);
        aucLocation = (EditText) findViewById(R.id.auction_location);

        String time = String.format("%02d:%02d",c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
        String date = String.format("%04d-%02d-%02d",c.get(Calendar.YEAR),c.get(Calendar.MONTH) + 1,c.get(Calendar.DAY_OF_MONTH));
        aucEndTime = (TextView) findViewById(R.id.auc_end_time);
        aucEndTime.setText(time);
        aucEndTime.setOnClickListener(showTimePickerDialog);

        aucEndDate = (TextView) findViewById(R.id.auc_end_date);
        aucEndDate.setText(date) ;
        aucEndDate.setOnClickListener(showDatePickerDialog);

        aucExpTime = (TextView) findViewById(R.id.auc_exp_time);
        aucExpTime.setText(time);
        aucExpTime.setOnClickListener(showTimePickerDialog);

        aucExpDate = (TextView) findViewById(R.id.auc_exp_date);
        aucExpDate.setText(date) ;
        aucExpDate.setOnClickListener(showDatePickerDialog);

        aucStartBtn = (Button) findViewById(R.id.auc_start_btn);
        aucStartBtn.setOnClickListener(startNewAuction);
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

    View.OnClickListener startNewAuction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("location", aucLocation.getText().toString()));
            Log.e("OrderIt",aucEndDate.getText().toString() + " " + aucEndTime.getText().toString()+":00");
            nameValuePairs.add(new BasicNameValuePair("endtime",aucEndDate.getText().toString() + " " + aucEndTime.getText().toString()+":00"));
            nameValuePairs.add(new BasicNameValuePair("expected",aucExpDate.getText().toString() + " " + aucExpTime.getText().toString()+":00"));
            nameValuePairs.add(new BasicNameValuePair("description",aucDesc.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("id_user","13"));
            ServerConnect myServer=new ServerConnect(a);

            Toast.makeText(a, "Starting a New Auction...", Toast.LENGTH_SHORT).show();
            myServer.execute(getString(R.string.IP)+"auction.php",nameValuePairs,this);
        }
    };

    @Override
    public void onTimePicked(int view,int hourOfDay, int minute) {
        String time = String.format("%02d:%02d",hourOfDay,minute);
        switch(view){
            case R.id.auc_end_time:aucEndTime.setText(time);break;
            case R.id.auc_exp_time:aucExpTime.setText(time);break;
        }

    }

    @Override
    public void onDatePicked(int view,int year, int monthOfYear, int dayOfMonth) {
        String date = String.format("%04d-%02d-%02d",year,monthOfYear+1,dayOfMonth);
        switch(view){
            case R.id.auc_end_date:aucEndDate.setText(date);break;
            case R.id.auc_exp_date:aucExpDate.setText(date);break;
        }
    }

    @Override
    public void onResponse(JSONArray j) {
        Log.d("ResponseListener","onResponseListened");
        Toast toast = Toast.makeText(this, "Auction Started Successfully", Toast.LENGTH_SHORT);
        toast.show();

        Intent i = new Intent(this,AuctionActivity.class);
        startActivity(i);
        finish();
    }
}
