package group2.netapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import group2.netapp.utilFragments.DatePickerFragment;
import group2.netapp.utilFragments.TimePickerFragment;


public class Broadcast extends FragmentActivity implements TimePickerFragment.OnTimeSetListener,DatePickerFragment.OnDateSetListener {

    //--------------new------------------
    TextView expectTime,expectDate;
    Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        //--------------new------------------
        setUpForm();

        Button broadcast_button =  (Button)findViewById(R.id.broadcast_button);

        broadcast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item= ((EditText)findViewById(R.id.itemfield)).getText().toString();
                String description= ((EditText)findViewById(R.id.descriptionfield)).getText().toString();
                String location= ((EditText)findViewById(R.id.locationfield)).getText().toString();

                //int time_hour=((TimePicker)findViewById(R.id.timePicker)).getCurrentHour();
                //int time_minute=((TimePicker)findViewById(R.id.timePicker)).getCurrentMinute();
                //String time = String.format("%02d:%02d",c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
                //expectTime = (TextView) findViewById(R.id.timePicker);

                String time = ((TextView)findViewById(R.id.timePicker)).getText().toString();
                //expectTime.setOnClickListener(showTimePickerDialog);

                //int time_hour = c.get(Calendar.HOUR_OF_DAY);
                //int time_minute = c.get(Calendar.MINUTE);

                //int date_year= ((DatePicker)findViewById(R.id.datePicker)).getYear();
                //int date_month= ((DatePicker)findViewById(R.id.datePicker)).getMonth();
                //int date_day= ((DatePicker)findViewById(R.id.datePicker)).getDayOfMonth();
                //String date = String.format("%04d-%02d-%02d",c.get(Calendar.YEAR),c.get(Calendar.MONTH) + 1,c.get(Calendar.DAY_OF_MONTH));
                expectDate = (TextView) findViewById(R.id.datePicker);
                String date = ((TextView)findViewById(R.id.datePicker)).getText().toString();
                //expectDate.setOnClickListener(showDatePickerDialog);

                /*int date_year = c.get(Calendar.YEAR);
                int date_month = c.get(Calendar.MONTH);
                int date_day = c.get(Calendar.DAY_OF_MONTH);*/

                if(item.length()<1 || location.length()< 1)
                {
                    if(item.length()<1)
                    {
                        Toast.makeText(getApplicationContext(), "Enter the item", Toast.LENGTH_SHORT).show();
                        ((EditText)findViewById(R.id.itemfield)).requestFocus();
                    }
                    else if(location.length()<1)
                    {
                        Toast.makeText(getApplicationContext(), "Enter the location", Toast.LENGTH_SHORT).show();
                        ((EditText)findViewById(R.id.locationfield)).requestFocus();
                    }
                }
                else {
                    Timestamp currentTime = new Timestamp(Calendar.getInstance().getTime().getTime());
                    Timestamp bTime = Timestamp.valueOf(date+" "+time+":00");
                    if(bTime.before(currentTime)){
                        Toast.makeText(getApplicationContext(), "Time must be before current time", Toast.LENGTH_SHORT).show();
                    }else {

                        //new add_broadcast(item,location,description,time_hour,time_minute,date_day,date_month,date_year,"http://netapp.byethost33.com/add_broadcast.php").execute(null,null,null);

                        new add_broadcast(item, location, description, time, date, "http://netapp.byethost33.com/add_broadcast.php").execute(null, null, null);
                        //finish();
                    }
                }
            }
        });
    }

    //--------------new-----------------------
    public void setUpForm(){
        String time = String.format("%02d:%02d",c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
        String date = String.format("%04d-%02d-%02d",c.get(Calendar.YEAR),c.get(Calendar.MONTH) + 1,c.get(Calendar.DAY_OF_MONTH));

        expectTime = (TextView) findViewById(R.id.timePicker);
        expectTime.setText(time);
        expectTime.setOnClickListener(showTimePickerDialog);

        expectDate = (TextView) findViewById(R.id.datePicker);
        expectDate.setText(date) ;
        expectDate.setOnClickListener(showDatePickerDialog);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_broadcast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    //-----------------------------------new--------------------------------
    View.OnClickListener showTimePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag;
            if(v.getId()== R.id.timePicker){
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

    //-----------------------------------new--------------------------------
    View.OnClickListener showDatePickerDialog = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String tag;
            if(v.getId()== R.id.datePicker){
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
    //-----------------------------------new--------------------------------
    @Override
    public void onTimePicked(int view,int hourOfDay, int minute) {
        String time = String.format("%02d:%02d",hourOfDay,minute);
        switch(view){
            case R.id.timePicker:expectTime.setText(time);break;
            //case R.id.datePicker:expectDate.setText(time);break;
        }

    }

    @Override
    public void onDatePicked(int view,int year, int monthOfYear, int dayOfMonth) {
        String date = String.format("%04d-%02d-%02d",year,monthOfYear+1,dayOfMonth);
        switch(view){
            case R.id.datePicker:expectDate.setText(date);break;
            //case R.id.auc_exp_date:aucExpDate.setText(date);break;
        }
    }

    class add_broadcast extends AsyncTask<String,String,String>
    {
        private String item,location,description,host;
        private String time_hour,time_minute,date_year,date_day,date_month,time,date;

        //public  add_broadcast(String a,String b,String d,int e,int f,int g,int h, int i,String j)
        public  add_broadcast(String a,String b,String d,String e,String f,String j)
        {
            item=a;
            location=b;
            description = d;
            /*time_hour=String.valueOf(e);
            time_minute=String.valueOf(f);
            date_day=String.valueOf(g);
            date_month=String.valueOf(h);
            date_year=String.valueOf(i);*/
            host=j;
            /*time=time_hour+":"+time_minute+":00";
            date=date_year+"-"+date_month+"-"+date_day;*/
            time = e;
            date = f;
        }


        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            String text;

            try
            {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String email = saved_values.getString("email",null);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("item", item));
                nameValuePairs.add(new BasicNameValuePair("location", location));
                nameValuePairs.add(new BasicNameValuePair("description", description));
                nameValuePairs.add(new BasicNameValuePair("time", time));
                nameValuePairs.add(new BasicNameValuePair("date", date));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                if(response != null)
                {
                    InputStream is = response.getEntity().getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    text = sb.toString();
                    System.out.println(text);
                }

            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }
    }
}
