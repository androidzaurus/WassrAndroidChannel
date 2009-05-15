package net.seesaa.androidzaurus.android.wassrandroidchannel;

import android.net.Uri;
import android.provider.BaseColumns;

public final class WassrRss {
	public static final String AUTHORITY = "net.seesaa.androidzaurus.rss";
	
	private WassrRss() {}
	
	public static final class WassrItems implements BaseColumns {
		private WassrItems() {}
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");
		
		public static final Uri LIVE_FOLDER_URI = Uri.parse("content://" + AUTHORITY + "/live");
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.wassr.rss";
		
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.wassr.rss";
		
		public static final String DEFAULT_SORT_ORDER = "modified DESC";
		
		public static final String DESCRIPTION = "description";
		
		public static final String URL = "url";
		
		public static final String ID = "id";
	}
}
