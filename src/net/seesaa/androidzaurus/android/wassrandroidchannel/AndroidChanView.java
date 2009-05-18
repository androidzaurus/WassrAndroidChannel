package net.seesaa.androidzaurus.android.wassrandroidchannel;

import net.seesaa.androidzaurus.android.wassrandroidchannel.WassrRss.WassrItems;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.LiveFolders;
import android.widget.SimpleCursorAdapter;

public class AndroidChanView extends ListActivity {
	private static final String[] PROJECTION = new String [] {
		LiveFolders._ID,
		LiveFolders.DESCRIPTION,
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent intent = getIntent();
		final String action = intent.getAction();

		if (LiveFolders.ACTION_CREATE_LIVE_FOLDER.equals(action)) {
			setResult(RESULT_OK, createLiveFolder(this, WassrItems.LIVE_FOLDER_URI,
					"Wassr Android Channel", R.drawable.wassricon));
			finish();
		} else {
			if (intent.getData() == null) {
				intent.setData(WassrItems.CONTENT_URI);
			}
			Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null, null, WassrItems.DEFAULT_SORT_ORDER);
			SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.main, cursor, 
					new String[] {LiveFolders.DESCRIPTION}, new int[] {R.id.TextView01});
			setListAdapter(adapter);
		}
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