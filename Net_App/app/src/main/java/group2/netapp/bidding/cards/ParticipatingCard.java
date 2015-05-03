package group2.netapp.bidding.cards;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.FeedbackActivity;
import group2.netapp.FeedbackFragment;
import group2.netapp.HomeActivity;
import group2.netapp.R;
import it.gmariotti.cardslib.library.internal.Card;

public class ParticipatingCard extends Card {

    String auctionLocation,desc,idUser,start_time,end_time,expected_time,order_limit;
    String ratings,numRated;
    int index,idAuction,price,rank;


    public ParticipatingCard(Context context, JSONObject j,int i) {
        super(context, R.layout.participating_card);
        try {
            this.index=i;
            this.auctionLocation =j.getString("location");
            this.desc = j.getString("description");
            this.idUser = j.getString("idUser");
            String temp = j.getString("start_time");
            this.start_time = temp.substring(0,temp.length()-3);
            temp = j.getString("end_time");
            this.end_time = temp.substring(0,temp.length()-3);
            this.idAuction = j.getInt("idAuction");
            temp = j.getString("expctd_time");
            this.expected_time = temp.substring(0,temp.length()-3);
            this.price = j.getInt("minPrice");
            this.order_limit = j.getString("orderLimit");
            this.ratings=j.getString("rating");
            this.numRated=j.getString("numRated");
            this.rank=j.getInt("rank");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView auctionLocView = (TextView) parent.findViewById(R.id.participatinglocation);
        TextView priceView = (TextView) parent.findViewById(R.id.participatingprice);
        TextView descView = (TextView)parent.findViewById(R.id.participatingdesc);
        RatingBar ratingsView = (RatingBar)parent.findViewById(R.id.participatingratings);
        TextView numRatedView = (TextView)parent.findViewById(R.id.participatingnumRated);
        TextView end_timeView = (TextView)parent.findViewById(R.id.participatingend_time);
        TextView expected_timeView = (TextView)parent.findViewById(R.id.participatingexpected_time);
        TextView rankView=(TextView)parent.findViewById(R.id.participatingrank);
        TextView orderLimit = (TextView)parent.findViewById(R.id.participatingorderlimit);

        auctionLocView.setText(auctionLocation);
        priceView.setText("₹" + price);
        descView.setText(desc);
        orderLimit.setText(order_limit);
        if(ratings == null ||  ratings.equals("null"))
        {
            ratings="0";
            numRated="0";
            ratingsView.setRating(Float.parseFloat(ratings));
            numRatedView.setPaintFlags(numRatedView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            numRatedView.setText("rated by : " + numRated + " users");
        }

        numRatedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getContext(), FeedbackActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("id", idUser);
                mBundle.putInt("tag",0);
                mIntent.putExtras(mBundle);
                getContext().startActivity(mIntent);
            }
        });
        end_timeView.setText(end_time);
        expected_timeView.setText(expected_time);
        rankView.setText(rank+" ");

    }

    public String getAuctionLocation() {
        return auctionLocation;
    }

    public void setAuctionLocation(String auctionLocation) {
        this.auctionLocation = auctionLocation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(int idAuction) {
        this.idAuction = idAuction;
    }

    public String getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(String expected_time) {
        this.expected_time = expected_time;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getNumRated() {
        return numRated;
    }

    public void setNumRated(String numRated) {
        this.numRated = numRated;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
