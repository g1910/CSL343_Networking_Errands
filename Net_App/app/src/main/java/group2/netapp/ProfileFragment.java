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

import com.matesnetwork.callverification.Cognalys;
import com.matesnetwork.interfaces.VerificationListner;

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

        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String email = saved_values.getString("email",null);
        String picurl = saved_values.getString("picurl",null);
        String name = saved_values.getString("user_name",null);
        String phone_number = saved_values.getString("phone",null);
        String address_text = saved_values.getString("address",null);

        View infh = inflater.inflate(R.layout.fragment_profile, container, false);

        phone = (EditText)infh.findViewById(R.id.editText3);
        if(phone_number==null)
        {
            new get_phone(email).execute(null,null,null);
        }
        else
        {
           phone.setText(phone_number);
        }
        address = (EditText)infh.findViewById(R.id.editText4);

        if(address_text==null)
        {
            new get_address(email).execute(null,null,null);
        }
        else
        {
            address.setText(address_text);
        }



        TextView emailText = (TextView)infh.findViewById(R.id.emailText);
        emailText.setText(email);

        TextView nameText = (TextView)infh.findViewById(R.id.nameText);
        nameText.setText(name);



        ImageButton editPhone = (ImageButton)infh.findViewById(R.id.editPhone);
        ImageButton editAddress = (ImageButton)infh.findViewById(R.id.editAddress);

        ImageView profileImage=(ImageView)infh.findViewById(R.id.imageView2);
        picurl+="0";
        new LoadProfileImage(profileImage).execute(picurl);


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
                                        address.setText(addr);
                                        int lines = addressText.getLineCount();
                                        address.setLines(lines<6 ? lines : 5);
                                        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                        SharedPreferences.Editor editor=saved_values.edit();
                                        editor.putString("address",addr);
                                        editor.apply();
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
                                            progress.setMessage("Verifying");
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
                                            verify(enteredNumber);
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
        return infh;
    }

    private void verify(final String number) {
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
                        progress.dismiss();
                        phone.setText(number);
                        Toast.makeText(getActivity(), "Number Verified Successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                        SharedPreferences.Editor editor=saved_values.edit();
                        editor.putString("phone",number);
                        editor.apply();
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
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView downloadedImage;

        public LoadProfileImage(ImageView image) {
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

    class get_phone extends AsyncTask<String,String,String>
    {
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
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor=saved_values.edit();
                editor.putString("phone",received_num);
                editor.apply();
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
                System.out.println("hiii");
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                SharedPreferences.Editor editor=saved_values.edit();
                editor.putString("address",received_addr);
                editor.apply();
            }
        }
    }

}
