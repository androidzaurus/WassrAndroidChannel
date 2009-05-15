package net.seesaa.androidzaurus.android.wassrandroidchannel;

import net.seesaa.androidzaurus.android.wassrandroidchannel.WassrRss.WassrItems;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.LiveFolders;

public class AndroidChanView extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = getIntent();
		final String action = intent.getAction();

		if (LiveFolders.ACTION_CREATE_LIVE_FOLDER.equals(action)) {
			setResult(RESULT_OK, createLiveFolder(this, WassrItems.LIVE_FOLDER_URI,
					"Wassr Android Channel", R.drawable.wassricon));
		} else {
			setResult(RESULT_CANCELED);
		}

		finish();
	}

	private Intent createLiveFolder(AndroidChanView androidChanView,
			Uri contentUri, String string, int wassricon) {
		final Intent intent = new Intent();

		intent.setData(contentUri);
		intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_NAME, string);
		intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_ICON,
				Intent.ShortcutIconResource.fromContext(androidChanView,
						wassricon));
		intent.putExtra(LiveFolders.EXTRA_LIVE_FOLDER_DISPLAY_MODE,
				LiveFolders.DISPLAY_MODE_LIST);
		return intent;
	}
}