package group2.netapp.bidding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.R;
import group2.netapp.utilFragments.ProgressFragment;
import group2.netapp.utilFragments.ServerConnect;

public class CurrAuctionActivity extends FragmentActivity implements ServerConnect.OnResponseListener {

    JSONArray Participating;
    JSONArray NotParticipating;
    JSONArray Bids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr_auction);

        loadData();

    }

    private void loadData() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment aDashFrag = new ProgressFragment();

        ft.add(R.id.curr_auction_frame,aDashFrag,"ProgressAuction");
//        ft.addToBackStack(null);
        ft.commit();

        ServerConnect myServer=new ServerConnect(this);

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id_user","1"));
        myServer.execute(getString(R.string.IP)+"getAllAuctions.php",nameValuePairs);
        Log.d("CurrAucActivity",getString(R.string.IP)+"getAllAuctions.php");
        Log.e("AuctionActivity","Hi");

        Log.d("AuctionActivity", "ProgressAuctionOpened");

    }

    private void showCurrAuctions() {



        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment aDashFrag = new CurrAuctionFragment();

        ft.replace(R.id.curr_auction_frame, aDashFrag, "CurrentAuction");
//        ft.addToBackStack(null);
        ft.commit();
        Log.d("AuctionActivity", "CurrAuctionOpened");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_curr_auction, menu);
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
            Log.e("AuctionActivity",(j.length()+" hi "  ));
            Log.e("AuctionActivity", ((JSONObject) j.get(0)).length() + " First " + ((JSONObject) j.get(0)).toString());
            Log.e("AuctionActivity",((JSONObject)j.get(1)).length()+" Second "+((JSONObject)j.get(1)).toString());
            Log.e("AuctionActivity",((JSONObject)j.get(2)).length()+" Third "+((JSONObject)j.get(2)).toString());

            Participating =(JSONArray) ((JSONObject)j.get(0)).get("Participating");
            NotParticipating =(JSONArray) ((JSONObject)j.get(1)).get("Not_Participating");
            Bids =(JSONArray) ((JSONObject)j.get(2)).get("Bids");


            Log.e("AuctionActivity",(j.length()+" hi "  ));
            Log.e("AuctionActivity", ((JSONObject) j.get(0)).length() + " First " + ((JSONObject) j.get(0)).toString());
            Log.e("AuctionActivity",((JSONObject)j.get(1)).length()+" Second "+((JSONObject)j.get(1)).toString());
            Log.e("AuctionActivity",((JSONObject)j.get(2)).length()+" Third "+((JSONObject)j.get(2)).toString());

            showCurrAuctions();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getParticipating() {
        return Participating;
    }

    public void setParticipating(JSONArray participating) {
        Participating = participating;
    }

    public JSONArray getNotParticipating() {
        return NotParticipating;
    }

    public void setNotParticipating(JSONArray notParticipating) {
        NotParticipating = notParticipating;
    }

    public JSONArray getBids() {
        return Bids;
    }

    public void setBids(JSONArray bids) {
        Bids = bids;
    }
}
