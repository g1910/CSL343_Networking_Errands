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

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private CountDownTimer countDownTimer;
    ProgressDialog progress;
    EditText phone;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        View infh = inflater.inflate(R.layout.fragment_profile, container, false);



        TextView emailText = (TextView)infh.findViewById(R.id.emailText);
        emailText.setText(email);

        TextView nameText = (TextView)infh.findViewById(R.id.nameText);
        nameText.setText(name);

        phone = (EditText)infh.findViewById(R.id.editText3);
        final EditText address = (EditText)infh.findViewById(R.id.editText4);
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
                                        address.setText(addressText.getText().toString());
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
                                                            sleep(200);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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



}
