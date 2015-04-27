package group2.netapp.bidding.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by mohit on 12/4/15.
 */
public class GeneralBidCard extends Card {

    String bidLocation, bidOrder;
    int bidId,index;

    public GeneralBidCard(Context context, JSONObject j,int i) {
        super(context, R.layout.general_bid_card);

        try {
            this.bidLocation = j.getString("location");
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
//        TextView bidOrderView = (TextView) parent.findViewById(R.id.auc_bid_order_summary);

        bidLocationView.setText(bidLocation);
        //      bidOrderView.setText(bidOrder);
    }

    public String getBidLocation() {
        return bidLocation;
    }

    public void setBidLocation(String bidLocation) {
        this.bidLocation = bidLocation;
    }

    public String getBidOrder() {
        return bidOrder;
    }

    public void setBidOrder(String bidOrder) {
        this.bidOrder = bidOrder;
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
