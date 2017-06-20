package task.parser;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vyjen on 19.06.2017.
 */

public abstract class BaseFeedParser implements FeedParser {

    // Имена тегов XML
    static final  String LINK = "link";
    static final  String TITLE = "title";
    static final  String ITEM = "item";
    static final String CHANNEL = "channel";
    final String TAG = "mylog";
    final URL feedUrl;

    protected BaseFeedParser(String feedUrl){
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            Log.d(TAG,"oshibka v url");
            throw new RuntimeException(e);


        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            Log.d(TAG,"oshibka v getinputstream");
            throw new RuntimeException(e);
        }
    }
}
