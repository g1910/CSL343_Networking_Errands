package com.csl343.group2.orderit.utilFragments;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

            httpclient=new DefaultHttpClient();
            value=(ArrayList<NameValuePair>)params[1];

            httppost= new HttpPost((String)params[0]);
            httppost.setEntity(new UrlEncodedFormEntity(value));

            HttpResponse response =httpclient.execute(httppost);

        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

}
