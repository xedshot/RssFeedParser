package task.parser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    TextView tv;
    List feedlist;
    List<String> titles;
    List<URL> links;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tv = (TextView) findViewById(R.id.tv);

        GetXML getxml = new GetXML();
        getxml.execute();
        lv = (ListView) findViewById(R.id.lv);





    }

    class GetXML extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            XmlPullFeedParser xmlpull = new XmlPullFeedParser("https://news.tut.by/rss/geonews/minsk.rss");
            feedlist = xmlpull.parse();
            titles = xmlpull.getTitlesFromMessages(feedlist);
            if(titles.size()>0){
                Log.d("doingbg","titles done");
            }
            links = xmlpull.getURLFromMessages(feedlist);
            if(links.size()>0){
                Log.d("doinbg","links done");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.list_item,titles);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(links.get((int)id))));
                    startActivity(browserIntent);
                }
            });

        }

    }





}
