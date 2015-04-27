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
 * Created by mohit on 27/4/15.
 */
public class AuctionPlacedBidCard extends Card {

    String bidLocation,status;
    String rank,price;
    int index;

    public AuctionPlacedBidCard(Context context,String bidloc,String status,String ra,String pric,int i) {
        super(context, R.layout.auction_placed_bid_card);

            this.bidLocation = bidloc;
            this.index=i;
            this.price=pric;
        if (status.equals("P"))
        {
            this.status="Pending";
            this.rank="";
        }
        else if (status.equals("A"))
        {
            this.status="Accepted";
            this.rank=ra;
        }
        else if (status.equals("C"))
        {
            this.status="Confirmed";
            this.rank="";
        }
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView bidLocationView = (TextView) parent.findViewById(R.id.locationtext);
        TextView rankView = (TextView)parent.findViewById(R.id.participatingrank);
        TextView priceView = (TextView)parent.findViewById(R.id.pricetext);
        TextView statusView = (TextView) parent.findViewById(R.id.status);
//        TextView bidOrderView = (TextView) parent.findViewById(R.id.auc_bid_order_summary);

        bidLocationView.setText(bidLocation);
        rankView.setText(String.valueOf(rank));
        priceView.setText(String.valueOf(price));
        statusView.setText(status);

        //      bidOrderView.setText(bidOrder);
    }
}
