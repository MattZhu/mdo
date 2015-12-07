package com.mdo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class FacebookBaseActivity extends BaseActivity implements StatusCallback {

	private UiLifecycleHelper uiHelper;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity","error :",error);
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
						Toast.makeText(FacebookBaseActivity.this,
								String.format("Post Error: %s", error.toString()),
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!"+FacebookDialog.getNativeDialogCompletionGesture(data)+", post id:"+FacebookDialog.getNativeDialogPostId(data)+" data:"+data.keySet());
					}
				});
	}

	private String itemName="";
	
	
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void shareToFB(String link) {
//		itemName=link;
		if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					this).setLink("http://itunes.apple.com/us/app/id457674552?ls=1&mt=8")
					.setDescription("The best mobile resource for essential oils... Get the app!")
					.setCaption("There's an oil for that¡­")
					.setName("Check out "+itemName+" in the md¨­ Certified Pure Therapeutic Grade Oils App!")
					.setPicture("http://www.theloungellc.com/appIcon@2x.png")
					.build();
			uiHelper.trackPendingDialogCall(shareDialog.present());

		} else {

			Session.openActiveSession(this, true, this);
			
		}
	}

	private void publishFeedDialog(String link) {
		Bundle params = new Bundle();
		params.putString("name", "Check out "+link+" in the md¨­ Certified Pure Therapeutic Grade Oils App!");
		params.putString("caption",
				"There's an oil for that¡­");
		params.putString(
				"description",
				"The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		params.putString("link", "http://itunes.apple.com/us/app/id457674552?ls=1&mt=8");
		params.putString("picture",
				"http://www.theloungellc.com/appIcon@2x.png");
	
		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(this,
				Session.getActiveSession(), params))
				.setOnCompleteListener(new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(FacebookBaseActivity.this,
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();
							} else {
								// User clicked the Cancel button
								Toast.makeText(
										FacebookBaseActivity.this
												.getApplicationContext(),
										"Publish cancelled", Toast.LENGTH_SHORT)
										.show();
							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Toast.makeText(
									FacebookBaseActivity.this
											.getApplicationContext(),
									"Publish cancelled", Toast.LENGTH_SHORT)
									.show();
						} else {
							// Generic, ex: network error
							Toast.makeText(
									FacebookBaseActivity.this
											.getApplicationContext(),
									"Error posting story", Toast.LENGTH_SHORT)
									.show();
						}
					}

				}).build();
		feedDialog.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void call(Session session, SessionState state,
			Exception exception) {
		Log.d("FacebookActivity","session state:"+state.toString());
		if(state==SessionState.OPENED){
			publishFeedDialog(itemName);
		}
	}

}
