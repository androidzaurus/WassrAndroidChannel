package net.seesaa.androidzaurus.android.wassrandroidchannel;

import java.io.IOException;
import java.net.MalformedURLException;

import org.xmlpull.v1.XmlPullParserException;

import net.seesaa.androidzaurus.android.wassrandroidchannel.WassrRss.WassrItems;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class RssProvider extends ContentProvider {
	private static final String WassrRssUrl = "http://api.wassr.jp/channel_message/list.rss?name_en=android";
	private static final int ITEMS = 1;
	private static final int ITEM_ID = 2;
	private static final int LIVE = 3;
	private String RssUrl;
	
	private static final UriMatcher sUriMatcher;
	
	public RssProvider() {
		RssUrl = WassrRssUrl;
	}

	public RssProvider(String url) {
		RssUrl = url;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case ITEMS:
			return WassrItems.CONTENT_TYPE;
		case ITEM_ID:
			return WassrItems.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri); 
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		RssCursor rc = new RssCursor(RssUrl);
		Cursor c;
		try {
			c = rc.update();
			c.setNotificationUri(getContext().getContentResolver(), uri);
			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(WassrRss.AUTHORITY, "items", ITEMS);
		sUriMatcher.addURI(WassrRss.AUTHORITY, "items/#", ITEM_ID);
		sUriMatcher.addURI(WassrRss.AUTHORITY, "live", LIVE);
	}
}
