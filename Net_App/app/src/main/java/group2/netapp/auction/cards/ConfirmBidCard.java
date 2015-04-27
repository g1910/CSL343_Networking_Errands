package group2.netapp.auction.cards;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.R;
import group2.netapp.auction.bidsTabs.PostAcceptedBids;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by gaurav on 12/4/15.
 */
public class ConfirmBidCard extends Card {

    TextView bidLocationView;
    TextView bidOrderView;
    TextView bidPrice;


    JSONObject bid;
    public ConfirmBidCard(Context context, JSONObject bid) {
        super(context, R.layout.confirm_auc_bid_card);

        this.bid = bid;

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        bidLocationView = (TextView) parent.findViewById(R.id.auc_bid_location);
        bidOrderView = (TextView) parent.findViewById(R.id.auc_bid_order_summary);
        bidPrice = (TextView) parent.findViewById(R.id.bid_price);


        try {
            bidLocationView.setText(bid.getString("location"));
            bidOrderView.setText(bid.getJSONArray("orders").length() + " Items Ordered\n"+bid.getString("bid_description"));
            bidPrice.setText(bid.getString("Price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public JSONObject getBid() {
        return bid;
    }

    public void setBid(JSONObject bid) {
        this.bid = bid;
    }


}
