package group2.netapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import group2.netapp.auction.AuctionActivity;
import group2.netapp.auction.DecideAuctionActivity;
import group2.netapp.bidding.CurrAuctionActivity;


public class HomeFragment extends Fragment implements View.OnClickListener{



    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button sp,cs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sp = (Button) v.findViewById(R.id.spBtn);
        cs = (Button) v.findViewById(R.id.csBtn);

        sp.setOnClickListener(this);
        cs.setOnClickListener(this);
        return v;
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
        ((HomeActivity) activity).onSectionAttached(0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId()){
            case R.id.spBtn:
                Context context = getActivity().getApplicationContext();
                i = new Intent(getActivity(), AuctionActivity.class);
                startActivity(i);
                break;
            case R.id.csBtn:
                i = new Intent(getActivity(), CurrAuctionActivity.class);
                startActivity(i);
                break;
        }
    }


}
