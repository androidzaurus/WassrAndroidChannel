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
			int eventtype = xpp.getEventType();
			switch (eventtype)
			{
			case XmlPullParser.TEXT:
				if (inItem && inAuthor)
				{
					author = new String(xpp.getText());
				}
				else if (inItem && inContent)
				{
					MatrixCursor.RowBuilder row = myCursor.newRow();
					String desc = new String(xpp.getText());
					Log.v("RssCursor", _id + " " + author + " " + desc);
					row.add(_id);
					row.add(author);
					row.add(desc);
					_id = _id + 1;
					author = anonymous;
//					if (myCursor.getCount() == 10)
//						return myCursor;
				}
				break;
			case XmlPullParser.START_TAG:
				String name = xpp.getName();
				if (name.equals("item"))
					inItem = true;
				else if (name.equals("description"))
					inContent = true;
				else if (name.equals("author"))
					inAuthor = true;
				break;
			case XmlPullParser.END_TAG:
				name = xpp.getName();
				if (name.equals("item"))
					inItem = false;
				else if (name.equals("description"))
					inContent = false;
				else if (name.equals("author"))
					inAuthor = false;
				break;
			default:
				break;
			};
		}
		return myCursor;
	}
}
