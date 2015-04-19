package group2.netapp.auction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.R;
import group2.netapp.utilFragments.ServerConnect;


public class DecideAuctionActivity extends Activity implements ServerConnect.OnResponseListener{


    private boolean isRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide_auction);
        ProgressBar bar=(ProgressBar) findViewById(R.id.decideBar);
        ServerConnect myServer=new ServerConnect(this);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id_user","1"));
        myServer.execute("http://10.20.9.85/Networks/CSL343_Networking_Errands/Server/getAuction.php",nameValuePairs);

        Log.e("OrderIt","Hi");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_decide_auction, menu);
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

    @Override
    public void onResponse(JSONArray j) {
        try {
            isRunning=Boolean.valueOf (((JSONObject)j.get(0)).get("isRunning").toString());
            if (isRunning)
            {
                Intent i = new Intent(this, AuctionActivity.class);
                startActivity(i);
            }
            else {
                Intent i = new Intent(this, ServerFormActivity.class);
                startActivity(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}