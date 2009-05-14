package net.seesaa.androidzaurus.android.wassrandroidchannel;

import java.io.IOException;
import java.net.MalformedURLException;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.LiveFolders;

public class AndroidChanView extends Activity {
	public static final Uri CONTENT_URI = Uri
			.parse("http://api.wassr.jp/channel_message/list.rss?name_en=android");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = getIntent();
		final String action = intent.getAction();

		if (LiveFolders.ACTION_CREATE_LIVE_FOLDER.equals(action)) {
			setResult(RESULT_OK, createLiveFolder(this, CONTENT_URI,
					"Wassr Android Channel", R.drawable.wassricon));
		} else {
			setResult(RESULT_CANCELED);
		}

		/* only for testing */
		RssCursor rc = new RssCursor("http://api.wassr.jp/channel_message/list.rss?name_en=android");
		try {
			rc.update();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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