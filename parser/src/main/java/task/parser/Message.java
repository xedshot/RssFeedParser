package task.parser;

import java.net.MalformedURLException;
import java.net.URL;




public class Message {

    private  String title;
    private  URL link;

    public void setTitle(String title){
        this.title = title;
    }
    public  String getTitle(){
        return title;
    }

    public void setLink(String link) {
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    public URL getURL(){
        return link;
    }

    public void setList(int i){
    }


}
