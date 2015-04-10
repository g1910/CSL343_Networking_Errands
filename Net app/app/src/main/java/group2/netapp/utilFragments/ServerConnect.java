package group2.netapp.utilFragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohit on 15/3/15.
 */
public class ServerConnect extends AsyncTask<Object,Void,JSONArray> {
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> value;

    OnResponseListener listener = null;

    public ServerConnect(Activity a){
        listener = (OnResponseListener) a;
    }



    public interface OnResponseListener {
        public void onResponse(JSONArray j);
    };

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("ServerConnect","Starting Task...");
    }


    @Override
    protected JSONArray doInBackground(Object... params) {
        try {

            httpclient=new DefaultHttpClient();
            //ServerFormActivity a = (ServerFormActivity) params[0];
            //listener = (OnResponseListener) a;

            value=(ArrayList<NameValuePair>)params[1];

            httppost= new HttpPost((String)params[0]);
            httppost.setEntity(new UrlEncodedFormEntity(value));

            HttpResponse response = httpclient.execute(httppost);
            Log.d("ServerConnect",response.toString());
            //Log.d("ServerConnect",Integer.toString(response.getEntity().getContent().read()));

            return handleResponse(response);


        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(JSONArray j) {
        super.onPostExecute(j);
        listener.onResponse(j);
        Log.d("ServerConnect","Task Completed!");
    }

    private JSONArray handleResponse(HttpResponse response){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String json = reader.readLine();
            JSONTokener tokener = new JSONTokener(json);
            JSONArray j = new JSONArray(tokener);

            return j;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }

}
