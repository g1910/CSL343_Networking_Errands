package group2.netapp.auction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import group2.netapp.R;
import group2.netapp.utilFragments.DatePickerFragment;
import group2.netapp.utilFragments.ServerConnect;
import group2.netapp.utilFragments.TimePickerFragment;

public class ServerFormActivity extends FragmentActivity implements TimePickerFragment.OnTimeSetListener, DatePickerFragment.OnDateSetListener, ServerConnect.OnResponseListener{

    TextView aucEndTime,aucEndDate,aucExpTime,aucExpDate;
    EditText aucDesc, aucLocation, minPrice,orderLimit;
    Button aucStartBtn;
    Activity a = this;
    Calendar c = Calendar.getInstance();

    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        idUser = sp.getString("id", "");

        setContentView(R.layout.activity_server_form);
        setUpForm();
    }

    public void setUpForm(){
        aucDesc = (EditText) findViewById(R.id.auction_desc);
        aucLocation = (EditText) findViewById(R.id.auction_location);
        minPrice = (EditText) findViewById(R.id.auc_min_price);
        orderLimit = (EditText) findViewById(R.id.orderLimit);

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
            if(v.getId()== R.id.auc_end_time){
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
            if(v.getId()== R.id.auc_end_time){
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
            if(validate()) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("location", aucLocation.getText().toString()));
                Log.e("OrderIt", aucEndDate.getText().toString() + " " + aucEndTime.getText().toString() + ":00");
                nameValuePairs.add(new BasicNameValuePair("endtime", aucEndDate.getText().toString() + " " + aucEndTime.getText().toString() + ":00"));
                nameValuePairs.add(new BasicNameValuePair("expected", aucExpDate.getText().toString() + " " + aucExpTime.getText().toString() + ":00"));
                nameValuePairs.add(new BasicNameValuePair("description", aucDesc.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("id_user", idUser));
                nameValuePairs.add(new BasicNameValuePair("min_price", minPrice.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("order_limit", orderLimit.getText().toString()));

                ServerConnect myServer = new ServerConnect(a);

                Toast.makeText(a, "Starting a New Auction...", Toast.LENGTH_SHORT).show();
                myServer.execute(getString(R.string.IP) + "auction.php", nameValuePairs, this);
            }
        }
    };

    public boolean validate(){
        Timestamp currentTime, endTime, expectedTime,maxEnTime,maxExTime;

        Calendar maxEndDate,maxExpDate,minExpDate;
        maxEndDate = Calendar.getInstance();
        maxEndDate.add(Calendar.DAY_OF_MONTH,3);
        maxExpDate = Calendar.getInstance();
        maxExpDate.add(Calendar.DAY_OF_MONTH,4);

        currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
        maxEnTime = new Timestamp(maxEndDate.getTime().getTime());
        maxExTime = new Timestamp(maxExpDate.getTime().getTime());

        endTime = Timestamp.valueOf(aucEndDate.getText().toString()+ " "+aucEndTime.getText().toString()+":00.0");
        expectedTime = Timestamp.valueOf(aucExpDate.getText().toString()+ " "+aucExpTime.getText().toString()+":00.0");

        if(endTime.before(currentTime) || expectedTime.before(currentTime)){
            Toast.makeText(this,"End Time and Expected Time should be sometime in future!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(endTime.after(maxEnTime)){
            Toast.makeText(this,"Max duration of Auction can be 3 days!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(expectedTime.before(endTime) || expectedTime.after(maxExTime)){
            Toast.makeText(this,"Expected time of Auction can be within 1 day of the end of Auction!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if((expectedTime.getTime()-endTime.getTime()) < 21600000){
            Toast.makeText(this,"Expected time of Auction should be atleast 6 hours after the end of Auction!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(minPrice.getText().toString().isEmpty()){
            Toast.makeText(this,"Please enter the minimum Bid Amount!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Integer.valueOf(minPrice.getText().toString())<10){
            Toast.makeText(this,"Minimum Bid Amount should be atleast ₹ 10 !",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(orderLimit.getText().toString().isEmpty()){
            Toast.makeText(this,"Please enter the Order Limit!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Integer.valueOf(orderLimit.getText().toString())<1){
            Toast.makeText(this,"Order Limit should be atleast 1 !",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(aucLocation.getText().toString().isEmpty()){
            Toast.makeText(this,"Please enter the Location!",Toast.LENGTH_SHORT).show();
            return false;
        }

        Log.d("ServerFormActivity",currentTime.toString()+" "+endTime.toString()+" "+expectedTime.toString());

        return true;
    }

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
        try {
            String tag = ((JSONObject)j.get(0)).getString("tag");
            if(tag.equals("createAuction")){
                boolean status = ((JSONObject)j.get(1)).getBoolean("status");
                if(status){
                    Toast.makeText(this, "Auction started successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this,AuctionActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(this, "Auction can't be started!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
