package net.seesaa.androidzaurus.android.wassrandroidchannel;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.database.MatrixCursor;
import android.provider.LiveFolders;
import android.util.Log;
import android.util.Xml;

public class RssCursor {

	private String rssUrl;
	private String columnames[] = { LiveFolders._ID, LiveFolders.NAME, LiveFolders.DESCRIPTION };
	private String anonymous = "anonymous";
	private MatrixCursor myCursor;

	public RssCursor(String url) {
		rssUrl = url;
	}

	public MatrixCursor update()
		throws MalformedURLException, IOException,	XmlPullParserException
	{
		int _id;
		String author = anonymous;
		boolean inItem, inContent, inAuthor;
		
		final XmlPullParser xpp = Xml.newPullParser();
		HttpURLConnection conn = (HttpURLConnection) (new URL(rssUrl).openConnection());
		xpp.setInput(new InputStreamReader(conn.getInputStream(), "utf-8"));
		
		myCursor = new MatrixCursor(columnames);
		
		inItem = inAuthor = inContent = false;
		_id = 1;
		while (xpp.next() != XmlPullParser.END_DOCUMENT)
		{
			if (inItem && inAuthor && xpp.getEventType() == XmlPullParser.TEXT)
			{
				author = new String(xpp.getText());
			}
			else if (inItem && inContent && xpp.getEventType() == XmlPullParser.TEXT)
			{
				MatrixCursor.RowBuilder row = myCursor.newRow();
				String desc = new String(xpp.getText());
				Log.v("RssCursor", _id + " " + author + " " + desc);
				row.add(_id);
				row.add(author);
				row.add(desc);
				_id = _id + 1;
				author = anonymous;
//				if (myCursor.getCount() == 10)
//					break;
			}
			else if (xpp.getEventType() == XmlPullParser.START_TAG)
			{
				if (xpp.getName().equals("item"))
					inItem = true;
				else if (xpp.getName().equals("description"))
					inContent = true;
				else if (xpp.getName().equals("author"))
					inAuthor = true;
			}
			else if (xpp.getEventType() == XmlPullParser.END_TAG)
			{
				if (xpp.getName().equals("item"))
					inItem = false;
				else if (xpp.getName().equals("description"))
					inContent = false;
				else if (xpp.getName().equals("author"))
					inAuthor = false;
			}
		}
		return myCursor;
	}
}
