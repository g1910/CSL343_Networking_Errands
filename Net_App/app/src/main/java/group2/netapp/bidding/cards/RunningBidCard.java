package group2.netapp.bidding.cards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.FeedbackActivity;
import group2.netapp.ProfileActivity;
import group2.netapp.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by mohit on 12/4/15.
 */
public class RunningBidCard extends Card {

    String bidLocation,description;
    String status,rank;
    int price;
    String IDS;
    int bidId,index;
    public RunningBidCard(Context context, JSONObject j,int i,String ranks,int prices,String statuses,String IDS) {
        super(context, R.layout.running_bid_card);

        try {
            this.bidLocation = j.getString("location");
            this.description=j.getString("bid_description");
            this.price = prices;
            this.IDS = IDS;
            Log.d("HERER", statuses);
            Log.d("HERER",statuses.equals("P")+" ");
            if (statuses.equals("P"))
            {
                this.status="Pending";
                this.rank="";
            }
            else if (statuses.equals("A"))
            {
                this.status="Accepted";
                this.rank=ranks;
            }
            else if (statuses.equals("C"))
            {
                this.status="Confirmed";
                this.rank="";
            }

            this.index=i;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //      this.bidOrder = bidOrder;
    //    this.bidId = bidId;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView bidLocationView = (TextView) parent.findViewById(R.id.locationtext);
        TextView desView = (TextView)parent.findViewById(R.id.auc_bid_order_summary);
        TextView rankView=(TextView) parent.findViewById(R.id.participatingrank);
        TextView statusView=(TextView) parent.findViewById(R.id.status);
        TextView priceView=(TextView) parent.findViewById(R.id.pricetext);
        if (status.equals("Confirmed"))
        {
            Button b=(Button) parent.findViewById(R.id.contact_details_button);
            b.setVisibility(View.VISIBLE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(getContext(), ProfileActivity.class);
                    Bundle mBundle = new Bundle();
                    Log.d("IDS",IDS);
                    mBundle.putString("id", IDS);
                    mBundle.putInt("tag",0);
                    mIntent.putExtras(mBundle);
                    getContext().startActivity(mIntent);
                }
            });
        }
//        TextView bidOrderView = (TextView) parent.findViewById(R.id.auc_bid_order_summary);

        bidLocationView.setText(bidLocation);
        desView.setText(description);
        rankView.setText(rank);
        statusView.setText(status);
        priceView.setText(price+"");
  //      bidOrderView.setText(bidOrder);
    }

    public String getBidLocation() {
        return bidLocation;
    }

    public void setBidLocation(String bidLocation) {
        this.bidLocation = bidLocation;
    }


    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
