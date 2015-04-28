package group2.netapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.matesnetwork.callverification.Cognalys;
import com.matesnetwork.interfaces.VerificationListner;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private CountDownTimer countDownTimer;
    ProgressDialog progress;
    EditText phone;

    EditText address;
    TextView emailText;
    TextView nameText;
    ImageView profileImage;
    private OnFragmentInteractionListener mListener;

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor

        this.setArguments(new Bundle());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int ishome = getArguments().getInt("ishome",-1);
        View infh = inflater.inflate(R.layout.fragment_profile, container, false);
        phone = (EditText)infh.findViewById(R.id.editText3);
        address = (EditText)infh.findViewById(R.id.editText4);
        emailText = (TextView)infh.findViewById(R.id.emailText);
        nameText = (TextView)infh.findViewById(R.id.nameText);
        ImageButton editPhone = (ImageButton)infh.findViewById(R.id.editPhone);
        ImageButton editAddress = (ImageButton)infh.findViewById(R.id.editAddress);
        profileImage=(ImageView)infh.findViewById(R.id.imageView2);
        if(ishome==0){
            String id = getArguments().getString("id",null);
            System.out.println("here "+id);

            editAddress.setVisibility(View.INVISIBLE);
            editPhone.setVisibility(View.INVISIBLE);
            new get_profile(id).execute(null,null,null);
        }
        else
        {

            SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            final String email = saved_values.getString("email",null);
            String picpath = saved_values.getString("picpath",null);
            String name = saved_values.getString("user_name",null);
            String phone_number = saved_values.getString("phone",null);
            String address_text = saved_values.getString("address",null);



            if(phone_number==null)
            {
                new get_phone(email).execute(null,null,null);
            }
            else
            {
                phone.setText(phone_number);
            }


            if(address_text==null)
            {
                new get_address(email).execute(null,null,null);
            }
            else
            {
                address.setText(address_text);
            }




            emailText.setText(email);


            nameText.setText(name);






            if(picpath != null) {
                Bitmap bitmap = null;
                File file = new File(picpath, "ProfilePic.jpg");
                FileInputStream streamIn = null;
                try {
                    streamIn = new FileInputStream(file);
                    bitmap = BitmapFactory.decodeStream(streamIn);
                    streamIn.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profileImage.setImageBitmap(bitmap);
            }


            editAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View addressDialog = inflater.inflate(R.layout.address_dialog,null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setView(addressDialog);

                    final EditText addressText = (EditText)addressDialog.findViewById(R.id.AddressText);
                    addressText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
                    dialog
                            .setCancelable(false)
                            .setPositiveButton("Save",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            String addr = addressText.getText().toString();
                                            new add_address(email, addr).execute(null,null,null);
                                            //address.setText(addr);
                                            int lines = addressText.getLineCount();
                                            address.setLines(lines<6 ? lines : 5);

                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,int id) {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
            });

            editPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View phoneDialog = inflater.inflate(R.layout.phone_dialog,null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setView(phoneDialog);

                    final EditText phoneText = (EditText)phoneDialog.findViewById(R.id.phoneText);
                    phoneText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                    dialog
                            .setCancelable(false)
                            .setPositiveButton("Verify",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            String enteredNumber = phoneText.getText().toString();
                                            if (enteredNumber.length() == 10) {
                                                progress = new ProgressDialog(getActivity());
                                                progress.setMessage("Verifying...please wait");
                                                progress.setCanceledOnTouchOutside(false);
                                                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                                progress.setIndeterminate(true);
                                                progress.show();
                                                final Thread t = new Thread(){
                                                    @Override
                                                    public void run(){

                                                        int jumpTime = 0;
                                                        while(jumpTime < 100){
                                                            try {
                                                                sleep(1200000);
                                                                jumpTime += 5;
                                                                progress.setProgress(jumpTime);
                                                            } catch (InterruptedException e) {
                                                                // TODO Auto-generated catch block
                                                                e.printStackTrace();
                                                            }

                                                        }

                                                    }
                                                };
                                                t.start();
                                                verify(enteredNumber, email);
                                            }

                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();

                }
            });
            // Inflate the layout for this fragment
        }
            return infh;
    }

    private void verify(final String number, final String email) {
        countDownTimer = new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //timertv.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                progress.dismiss();
            }

        };
        countDownTimer.start();

        Cognalys.verifyMobileNumber(getActivity(),
                "6ac9f915d36c3979e6491a47f0157c2d3aba9edb",
                "ce4e50816fed4e7e89cc176", number, new VerificationListner() {

                    @Override
                    public void onVerificationStarted() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onVerificationSuccess() {
                        countDownTimer.cancel();
                        Toast.makeText(getActivity(), "Number Verified Successfully", Toast.LENGTH_SHORT).show();
                        new add_phone(email,number).execute(null,null,null);
                        progress.dismiss();
                    }

                    @Override
                    public void onVerificationFailed(ArrayList<String> errorList) {
                        countDownTimer.cancel();
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Number Verification Failed", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        int ishome = getArguments().getInt("ishome",-1);
        if(ishome!=0)
        ((HomeActivity) activity).onSectionAttached(1);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    class get_phone extends AsyncTask<String,String,String> {
        private String email;
        private String received_num=null;
        private String host = "http://netapp.byethost33.com/get_phone.php";
        public  get_phone(String a)
        {
            email=a;
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            try
            {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                if(response != null)
                {
                    InputStream is = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    received_num = reader.readLine().replaceAll("\\s+","");

                }
            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String Result) {
            if(received_num!=null && received_num.length()>0)
            {
                phone.setText(received_num);
                try
                {
                    SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                    SharedPreferences.Editor editor=saved_values.edit();
                    editor.putString("phone",received_num);
                    editor.apply();
                }
                catch(Exception e)
                {
                    System.out.println("Phone not saved in shared preferences");
                }

            }
        }
    }

    class get_address extends AsyncTask<String,String,String>
    {
        private String email;
        private String received_addr=null;
        private String host = "http://netapp.byethost33.com/get_address.php";
        public  get_address(String a)
        {
            email=a;
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            try
            {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                if(response != null)
                {
                    InputStream is = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    received_addr = reader.readLine().replaceAll("\\s+","");

                }
            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String Result) {
            if(received_addr!=null && received_addr.length()>0)
            {
                address.setText(received_addr);
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor=saved_values.edit();
                editor.putString("address",received_addr);
                editor.apply();
            }
        }
    }

    class get_profile extends AsyncTask<String,String,String>
    {
        private String id;
        InputStream is;
        private String host = "http://netapp.byethost33.com/get_profile.php";
        public  get_profile(String a)
        {
            id=a;
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            System.out.println("here2 "+id);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            try
            {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("id", id));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                if(response != null)
                {
                    is = response.getEntity().getContent();
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
                    String text = sb.toString();
                    System.out.println(id + "ssdvs" + text);

                    return text;

                }
            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String Result) {
            Reader reader = new InputStreamReader(is);
            profile_details pi=null;
            try {
//                JsonParser parser = new JsonParser();
//                JsonObject data = parser.parse(Result).getAsJsonObject();
//                GsonBuilder gsonBuilder = new GsonBuilder();
//                Gson gson = gsonBuilder.create();
//
//
//              p = gson.fromJson(data.get("Informations"), profile_details.class);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Log.d("ProfileFragment",p.toString());
                JSONObject js = new JSONObject(new JSONTokener(Result));
                JSONArray ja = js.getJSONArray("Informations");
                JSONObject p = ja.getJSONObject(0);
                if(p!=null)
                {
                    address.setText(p.getString("address"));
                    phone.setText(p.getString("phone"));
                    emailText.setText(p.getString("email"));
                    nameText.setText(p.getString("name"));
                    new setProfileImage(profileImage).execute(p.getString("picurl"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private class setProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView downloadedImage;

        public setProfileImage(ImageView image) {
            this.downloadedImage = image;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap icon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                icon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return icon;
        }

        protected void onPostExecute(Bitmap result) {
            downloadedImage.setImageBitmap(result);
        }
    }

    class add_address extends AsyncTask<String,String,String>
    {
        private String email;
        private String addr;
        private String resp=null;
        private String host = "http://netapp.byethost33.com/add_address.php";
        public  add_address(String a, String b)
        {
            email=a;
            addr=b;
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            try
            {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("address", addr));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                if(response != null)
                {
                    InputStream is = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    resp = reader.readLine();
                    Toast.makeText(getActivity(), resp, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String Result) {
            if(resp!=null && resp.length()>0)
            {
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor=saved_values.edit();
                editor.putString("address",addr);
                editor.apply();
                //System.out.println("if ran");
                address.setText(addr);

            }
        }
    }

    class add_phone extends AsyncTask<String,String,String>
    {
        private String email;
        private String phn;
        String resp = null;
        private String host = "http://netapp.byethost33.com/add_phone.php";
        public  add_phone(String a, String b)
        {
            email=a;
            phn = b;
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            try
            {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("phone", phn));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                if(response != null)
                {
                    InputStream is = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    resp = reader.readLine();
                }
            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }

        protected void onPostExecute(String Result) {
            if(resp!=null && resp.length()>0)
            {
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor=saved_values.edit();
                editor.putString("phone",phn);
                editor.apply();
                phone.setText(phn);
                //System.out.println("if ran");
            }
        }
    }

    class profile_details
    {
        String name;
        String email;
        String phone;
        String address;
        String picurl;
    }

}
