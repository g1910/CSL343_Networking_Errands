package group2.netapp;

import group2.netapp.R;
import java.io.InputStream;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {

	private static final int RC_SIGN_IN = 0;

	// Google client to communicate with Google
	private static GoogleApiClient mGoogleApiClient;

	private boolean mIntentInProgress;
	private boolean signedInUser;
	private ConnectionResult mConnectionResult;
	private SignInButton signinButton;
	private ImageView image;
	private TextView username, emailLabel;
	private static LinearLayout profileFrame, signinFrame;
    private int logged=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        logged = getIntent().getIntExtra("loggedout",0);
		signinButton = (SignInButton) findViewById(R.id.signin);
		signinButton.setOnClickListener(this);

		image = (ImageView) findViewById(R.id.image);
		username = (TextView) findViewById(R.id.username);
		emailLabel = (TextView) findViewById(R.id.email);

		profileFrame = (LinearLayout) findViewById(R.id.profileFrame);
		signinFrame = (LinearLayout) findViewById(R.id.signinFrame);

		mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();
	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
			return;
		}

		if (!mIntentInProgress) {
			// store mConnectionResult
			mConnectionResult = result;

			if (signedInUser) {
				resolveSignInError();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		switch (requestCode) {
		case RC_SIGN_IN:
			if (responseCode == RESULT_OK) {
				signedInUser = false;

			}
			mIntentInProgress = false;
			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
			break;
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		signedInUser = false;
        if(logged==1)
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        else
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		getProfileInformation();
	}

	private void updateProfile(boolean isSignedIn,String pname,String picurl,String email) {
		if (isSignedIn) {

            if(logged==0)
            {
                signinFrame.setVisibility(View.GONE);
                //profileFrame.setVisibility(View.VISIBLE);
                Intent ihome = new Intent(getApplicationContext(),HomeActivity.class);
                ihome.putExtra("pname",pname);
                ihome.putExtra("picurl",picurl);
                ihome.putExtra("email",email);
                startActivity(ihome);
                finish();}
            else
            {
                /*signinFrame.setVisibility(View.GONE);
                profileFrame.setVisibility(View.VISIBLE);
                logged=0;*/
                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();}
                signinFrame.setVisibility(View.VISIBLE);
                //profileFrame.setVisibility(View.GONE);
                isSignedIn=false;
                logged=0;
            }

		} else {
            if (mGoogleApiClient.isConnected()) {
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();}
			signinFrame.setVisibility(View.VISIBLE);
			profileFrame.setVisibility(View.GONE);
		}
	}

	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				username.setText(personName);
				emailLabel.setText(email);

				new LoadProfileImage(image).execute(personPhotoUrl);

				// update profile frame with new info about Google Account
				// profile
				updateProfile(true,personName,personPhotoUrl,email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
		updateProfile(false,"null","null","null");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signin:
			googlePlusLogin();
			break;
		}
	}

	public void signIn(View v) {
		googlePlusLogin();
	}

	public void logout(View v) {
		googlePlusLogout();
	}

	private void googlePlusLogin() {
		if (!mGoogleApiClient.isConnecting()) {
			signedInUser = true;
			resolveSignInError();
		}
	}

	private  void googlePlusLogout() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
			updateProfile(false,"null","null","null");
		}
	}

	// download Google Account profile image, to complete profile
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