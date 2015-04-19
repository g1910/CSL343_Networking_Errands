package group2.netapp.auction;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import group2.netapp.R;
import group2.netapp.utilFragments.DatePickerFragment;
import group2.netapp.utilFragments.ServerConnect;
import group2.netapp.utilFragments.TimePickerFragment;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ServerFormFragment extends Fragment implements TimePickerFragment.OnTimeSetListener, DatePickerFragment.OnDateSetListener, ServerConnect.OnResponseListener{

    TextView aucEndTime,aucEndDate,aucExpTime,aucExpDate;
    EditText aucDesc, aucLocation;
    Button aucStartBtn;
    Activity a = getActivity();
    Calendar c = Calendar.getInstance();

    public ServerFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_server_form, container, false);
        setUpForm(v);
        return v;
    }

    public void setUpForm(View v){
        aucDesc = (EditText) v.findViewById(R.id.auction_desc);
        aucLocation = (EditText) v.findViewById(R.id.auction_location);

        String time = String.format("%02d:%02d",c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE));
        String date = String.format("%04d-%02d-%02d",c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
        aucEndTime = (TextView) v.findViewById(R.id.auc_end_time);
        aucEndTime.setText(time);
        aucEndTime.setOnClickListener(showTimePickerDialog);

        aucEndDate = (TextView) v.findViewById(R.id.auc_end_date);
        aucEndDate.setText(date) ;
        aucEndDate.setOnClickListener(showDatePickerDialog);

        aucExpTime = (TextView) v.findViewById(R.id.auc_exp_time);
        aucExpTime.setText(time);
        aucExpTime.setOnClickListener(showTimePickerDialog);

        aucExpDate = (TextView) v.findViewById(R.id.auc_exp_date);
        aucExpDate.setText(date) ;
        aucExpDate.setOnClickListener(showDatePickerDialog);

        aucStartBtn = (Button) v.findViewById(R.id.auc_start_btn);
        aucStartBtn.setOnClickListener(startNewAuction);
    }

    View.OnClickListener showTimePickerDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag;
            if(v.getId()== R.id.auc_end_time){
                tag = "endtimepicker";
            }else{
                tag = "exptimepicker";
            }
            TimePickerFragment time = (TimePickerFragment) getChildFragmentManager().findFragmentByTag(tag);
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
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

    View.OnClickListener showDatePickerDialog = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String tag;
            if(v.getId()== R.id.auc_end_time){
                tag = "enddatepicker";
            }else{
                tag = "expdatepicker";
            }
            DatePickerFragment date = (DatePickerFragment) getChildFragmentManager().findFragmentByTag(tag);
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
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

    View.OnClickListener startNewAuction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("location", aucLocation.getText().toString()));
            Log.e("OrderIt", aucEndDate.getText().toString() + " " + aucEndTime.getText().toString() + ":00");
            nameValuePairs.add(new BasicNameValuePair("endtime",aucEndDate.getText().toString() + " " + aucEndTime.getText().toString()+":00"));
            nameValuePairs.add(new BasicNameValuePair("expected",aucExpDate.getText().toString() + " " + aucExpTime.getText().toString()+":00"));
            nameValuePairs.add(new BasicNameValuePair("description",aucDesc.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("id_user","1"));
            ServerConnect myServer=new ServerConnect(a);

            myServer.execute("http://"+getString(R.string.IP)+"/Networks/CSL343_Networking_Errands/Server/auction.php",nameValuePairs,this);
        }
    };

    @Override
    public void onTimePicked(int view,int hourOfDay, int minute) {
        String time = String.format("%02d:%02d",hourOfDay,minute);
        switch(view){
            case R.id.auc_end_time:aucEndTime.setText(time);break;
            case R.id.auc_exp_time:aucExpTime.setText(time);break;
        }

    }

    @Override
    public void onDatePicked(int view,int year, int monthOfYear, int dayOfMonth) {
        String date = String.format("%04d-%02d-%02d",year,monthOfYear+1,dayOfMonth);
        switch(view){
            case R.id.auc_end_date:aucEndDate.setText(date);break;
            case R.id.auc_exp_date:aucExpDate.setText(date);break;
        }
    }

    @Override
    public void onResponse(JSONArray j) {
        Log.d("ResponseListener","onResponseListened");
        Toast toast = Toast.makeText(getActivity(), "Auction Started Successfully", Toast.LENGTH_SHORT);
        toast.show();
    }


}
