package com.cosmic.nasarssfeeddisplay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by anushree on 8/29/2017.
 */

public class MyPullParser_1 {

   // private String url = "http://www.tutorialspoint.com/android/sampleXML.xml";
   private String url = "https://www.nasa.gov/rss/dyn/mission_pages/kepler/news/kepler-newsandfeatures-RSS.rss";

    //private String url = "http://www.nasa.gov/rss/dyn/chandra_images.rss";
    private String title = "title";
    private String description = "description";
    private String link = "link";
    private String image = "image";
    public volatile boolean parsingComplete = true;



    public String getTitle() {
        //Log.i("Parser1", title);
        return title;
    }

    public String getLink() {
        //Log.i("Parser1", link);
        return link;
    }

    public String getDescription() {
        //Log.i("Parser1", description);

        return description;
    }

    public String getImage() {
        return image;
    }


    public ArrayList<RssFeedBean> parseXml(XmlPullParser myparse) {

        int event;
        String text = null;
        RssFeedBean bean = null;
        ArrayList<RssFeedBean> mylist = new ArrayList<>();

        try {
            event = myparse.getEventType();
            int i=0;
            boolean insideItem = false;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myparse.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        Log.i("Parser1", name);
                        if (name.equals("item")) {
                            insideItem = true;

                        }



                        break;

                    case XmlPullParser.TEXT:


                        text = myparse.getText();
                        Log.i("Parser1text", text);

                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("item")) {
                            insideItem = false;
                            bean = new RssFeedBean(getTitle(),getLink(),getDescription(),getImage());
                            Log.i("Nasa", "Title = "+bean.getTitle());
                            mylist.add(bean);

                        } else if (name.equals("title")) {
                            if (insideItem) {
                                Log.i("Parser1", text);
                                title = text;
                            }
                        } else if (name.equals("link")) {
                            if (insideItem) {
                                Log.i("Parser1", text);
                                link = text;
                            }
                        } else if (name.equals("description")) {
                            if (insideItem) {
                                Log.i("Parser1", text);
                                description = text;
                            }
                        }
                        else if (name.equals("enclosure")) {
                            image = myparse.getAttributeValue(0);
                            Log.i("Parser1", image);
                        }
                        break;
                }

                i++;
                //Log.i("Parser1", ""+i);
                event = myparse.next();
            }
            parsingComplete = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mylist;

    }




   /* private void getBitmap(String myurl) {

                try {
                    Log.i("ParserT", "Inside getBitmap, URL " + url);
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).getContent();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    image = BitmapFactory.decodeStream(input);
                    input.close();

                } catch (Exception ioe) {
                    Log.e("ParserT", "Exception");

                }

    } */




  /*  public ArrayList<RssFeedBean> fetchXML() {

        ArrayList<RssFeedBean> feedlist = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {


                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser myparse = factory.newPullParser();

                    URL url1 = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) url1.openConnection();

                  //  conn.setReadTimeout(10000 /* milliseconds );
                /*    conn.setConnectTimeout(15000 /* milliseconds );
                 /*   conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();


                    myparse.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparse.setInput(stream, null);

                    feedlist.add(parseXml(myparse));
                    Log.i("Nasa", "Size = "+feedlist.size());
                    stream.close();
                } catch (Exception e) {
                }
            }
        });
        thread.start();

        return feedlist;
    }*/

}