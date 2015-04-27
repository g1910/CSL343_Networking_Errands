package group2.netapp.auction.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.R;
import group2.netapp.auction.bidsTabs.AcceptedBids;
import group2.netapp.auction.bidsTabs.PostAcceptedBids;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by gaurav on 12/4/15.
 */
public class PostBidCard extends Card implements CompoundButton.OnCheckedChangeListener {

    public interface PostBidCardListener{
        public void onItemChecked(int i,boolean b);
    }

    PostBidCardListener p;
    int index;

    TextView bidLocationView;
    TextView bidOrderView;
    TextView bidPrice;
    CheckBox bidCheck;

    JSONObject bid;
    public PostBidCard(Context context, JSONObject bid, PostAcceptedBids a, int index) {
        super(context, R.layout.post_auc_bid_card);

        this.bid = bid;
        this.p = a;
        this.index = index;

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        bidLocationView = (TextView) parent.findViewById(R.id.auc_bid_location);
        bidOrderView = (TextView) parent.findViewById(R.id.auc_bid_order_summary);
        bidPrice = (TextView) parent.findViewById(R.id.bid_price);
        bidCheck = (CheckBox) parent.findViewById(R.id.bid_checkbox);

        try {
            bidLocationView.setText(bid.getString("location"));
            bidOrderView.setText(bid.getJSONArray("orders").length() + " Items Ordered\n"+bid.getString("bid_description"));
            bidPrice.setText(bid.getString("Price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        bidCheck.setOnCheckedChangeListener(this);
    }

    public JSONObject getBid() {
        return bid;
    }

    public void setBid(JSONObject bid) {
        this.bid = bid;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        p.onItemChecked(index, isChecked);
    }

    public void setChecked(boolean b){
        bidCheck.setChecked(b);
    }
}
