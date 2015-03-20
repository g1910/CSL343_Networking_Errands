package group2.netapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

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
import java.util.ArrayList;
import java.util.List;


public class Broadcast extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        Button broadcast_button =  (Button)findViewById(R.id.broadcast_button);

        broadcast_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item= ((EditText)findViewById(R.id.itemfield)).getText().toString();
                String quantity= ((EditText)findViewById(R.id.quantityfield)).getText().toString();
                String location= ((EditText)findViewById(R.id.locationfield)).getText().toString();
                String price= ((EditText)findViewById(R.id.pricefield)).getText().toString();
                int time_hour=((TimePicker)findViewById(R.id.timePicker)).getCurrentHour();
                int time_minute=((TimePicker)findViewById(R.id.timePicker)).getCurrentMinute();
                int date_year= ((DatePicker)findViewById(R.id.datePicker)).getYear();
                int date_month= ((DatePicker)findViewById(R.id.datePicker)).getMonth();
                int date_day= ((DatePicker)findViewById(R.id.datePicker)).getDayOfMonth();

                new add_broadcast(item,location,quantity,price,time_hour,time_minute,date_day,date_month,date_year,"http://netapp.byethost33.com/add_broadcast.php").execute(null,null,null);
                //finish();
            }
        });
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




    class add_broadcast extends AsyncTask<String,String,String>
    {
        private String item,location,quantity,price,host;
        private String time_hour,time_minute,date_year,date_day,date_month,time,date;

        public  add_broadcast(String a,String b,String c,String d,int e,int f,int g,int h, int i,String j)
        {
            item=a;
            location=b;
            quantity=c;
            price=d;
            time_hour=String.valueOf(e);
            time_minute=String.valueOf(f);
            date_day=String.valueOf(g);
            date_month=String.valueOf(h);
            date_year=String.valueOf(i);
            host=j;
            time=time_hour+":"+time_minute+":00";
            date=date_year+"-"+date_month+"-"+date_day;
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
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String email = saved_values.getString("email",null);
                System.out.println(email);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("item", item));
                nameValuePairs.add(new BasicNameValuePair("location", location));
                nameValuePairs.add(new BasicNameValuePair("quantity", quantity));
                nameValuePairs.add(new BasicNameValuePair("price", price));
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
