package com.csl343.group2.orderit.utilFragments;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohit on 15/3/15.
 */
public class ServerConnect extends AsyncTask<Object,Void,Void> {
    HttpPost httppost;
    HttpClient httpclient;
    List<NameValuePair> value;
    @Override
    protected Void doInBackground(Object... params) {
        try {
            Log.e("OrderIt", (String) params[0]);
      //      URL url = new URL((String)params[0]);
       //     URLConnection conexion = url.openConnection();
            Log.e("OrderIt","hi");
      //      conexion.connect();
//            Log.e("OrderIt",conexion.getURL());
            httpclient=new DefaultHttpClient();
            value=(ArrayList<NameValuePair>)params[1];

//            Log.e("OrderIt",value.toString());
            httppost= new HttpPost((String)params[0]);
            httppost.setEntity(new UrlEncodedFormEntity(value));
            Log.e("OrderIt",httppost.toString());
            HttpResponse response =httpclient.execute(httppost);

            Log.e("OrderIt",response.getStatusLine().toString());
            Log.e("OrderIt",response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
