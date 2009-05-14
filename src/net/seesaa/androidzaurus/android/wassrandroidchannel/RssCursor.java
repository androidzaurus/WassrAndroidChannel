package net.seesaa.androidzaurus.android.wassrandroidchannel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.database.MatrixCursor;
import android.util.Log;
import android.util.Xml;

public class RssCursor {

	private String rssUrl;
	private String columnames[] = { "title", "url", "description" };

	public RssCursor(String url) {
		rssUrl = url;
	}

	public MatrixCursor update() throws MalformedURLException, IOException,
			XmlPullParserException {
		boolean inItem, inContent;
		MatrixCursor myCursor = new MatrixCursor(columnames);
		final XmlPullParser xmlPullParser = Xml.newPullParser();
		HttpURLConnection conn = (HttpURLConnection) (new URL(rssUrl)
				.openConnection());
		xmlPullParser.setInput(new InputStreamReader(conn.getInputStream(),
				"utf-8"));
		inItem = false;
		inContent = false;
		while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
			if (inItem && inContent
					&& xmlPullParser.getEventType() == XmlPullParser.TEXT) {
				String r[] = {null, null, xmlPullParser.getText()};
				Log.v("xml", r[2]);
				myCursor.addRow(r);
				if (myCursor.getCount() == 10)
					break;
			} else if (xmlPullParser.getEventType() == XmlPullParser.START_TAG) {
				if (xmlPullParser.getName().matches("item"))
					inItem = true;
				else if (xmlPullParser.getName().matches("description"))
					inContent = true;
			} else if (xmlPullParser.getEventType() == XmlPullParser.END_TAG) {
				if (xmlPullParser.getName().matches("item"))
					inItem = false;
				else if (xmlPullParser.getName().matches("description"))
					inContent = false;
			}
		}
		return myCursor;
	}
}
