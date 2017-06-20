package task.parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vyjen on 19.06.2017.
 */

public class XmlPullFeedParser extends BaseFeedParser {
    public XmlPullFeedParser(String feedUrl) {
        super(feedUrl);
    }

    public List<Message> parse() {
        List<Message> messages = null;
        XmlPullParser parser = Xml.newPullParser();
        try {

            parser.setInput(this.getInputStream(), null);
            int eventType = parser.getEventType();
            Message currentMessage = null;
            boolean done = false;
            while (eventType != XmlPullParser.END_DOCUMENT && !done){
                String name = null;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        messages = new ArrayList<Message>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM)){
                            currentMessage = new Message();
                        } else if (currentMessage != null){
                            if (name.equalsIgnoreCase(LINK)){
                                currentMessage.setLink(parser.nextText());
                            } else if (name.equalsIgnoreCase(TITLE)){
                                currentMessage.setTitle(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ITEM) &&
                                currentMessage != null){
                            messages.add(currentMessage);
                        } else if (name.equalsIgnoreCase(CHANNEL)){
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messages;
    }
    public List<String> getTitlesFromMessages(List<Message> list){
        List<String> titles = new ArrayList<String>();
        for(int i = 0;i<list.size();i++){
            titles.add(i,list.get(i).getTitle());
        }
        return titles;
    }
    public List<URL> getURLFromMessages(List<Message> list){
        List<URL> links = new ArrayList<URL>();
        for(int i = 0; i<list.size();i++){
            links.add(i,list.get(i).getURL());

        }
        return links;
    }
}
