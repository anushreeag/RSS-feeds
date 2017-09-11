package com.cosmic.nasarssfeeddisplay;


import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class NASARssfeedDisplay extends AppCompatActivity {


    MyPullParser_1 parser;
    ListView lv;
    RssFeedAdp rssAdp;
    public static final String FEED = "feed";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url  = getIntent().getExtras().get(FEED).toString();
        setContentView(R.layout.activity_nasa_chandra);
        parser = new MyPullParser_1();
        lv = (ListView) findViewById(R.id.list);
        ParserTask task = new ParserTask();

        setTitle(R.string.feed_title);
        task.execute(url);
        int[] colors = {0, 0xFFFF3FFF, 0}; // red for the example
        lv.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        lv.setDividerHeight(10);
        lv.setHeaderDividersEnabled(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rssAdp.clear();
    }


    class ParserTask extends AsyncTask<String,Void,ArrayList<RssFeedBean>>{

        @Override
        protected ArrayList<RssFeedBean> doInBackground(String... strings) {

            ArrayList<RssFeedBean> feedlist = new ArrayList<>();
            try {


                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser myparse = factory.newPullParser();

                        URL url1 = new URL(strings[0]);
                        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();

                        conn.setReadTimeout(10000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream stream = conn.getInputStream();


                        myparse.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        myparse.setInput(stream, null);

                        feedlist.addAll(parser.parseXml(myparse));

                        stream.close();
                    } catch (Exception e) {
                    }

            Log.i("Nasa", "Size = "+feedlist.size());
            return feedlist;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<RssFeedBean> rssFeedBean) {
            super.onPostExecute(rssFeedBean);
            Log.i("Nasa", "Size = "+rssFeedBean.size());
            rssAdp = new RssFeedAdp(NASARssfeedDisplay.this,rssFeedBean);
            rssAdp.notifyDataSetChanged();
            lv.setAdapter(rssAdp);
            rssAdp.notifyDataSetChanged();
        }
    }




    class ViewHolder{
        TextView title;
        TextView link;
        TextView description;
        ImageView icon;
    }

    class RssFeedAdp extends ArrayAdapter<RssFeedBean>{
        ArrayList<RssFeedBean> list;

        Context ctx;
        LayoutInflater li;

        View myView;

        public RssFeedAdp(@NonNull Context context, ArrayList<RssFeedBean> mylist) {
            super(context, R.layout.rss_item,mylist);
            ctx = context;
            list = mylist;
            li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Nullable
        @Override
        public RssFeedBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            final ViewHolder holder;
            if(convertView==null){
                holder = new ViewHolder();
                convertView  = li.inflate(R.layout.rss_item,null);
                convertView.setTag(holder);
                holder.title = (TextView) convertView.findViewById(R.id.txtTitle);
                holder.link = (TextView) convertView.findViewById(R.id.textdate);
                holder.description = (TextView) convertView.findViewById(R.id.txtDesc);
                holder.icon = (ImageView) convertView.findViewById(R.id.imageView);

            }
            else{
                holder = (ViewHolder) convertView.getTag();
            }
            final RssFeedBean bean = list.get(position);
            holder.title.setText(bean.getTitle());
            holder.link.setText(bean.getLink());
            holder.link.setMovementMethod(LinkMovementMethod.getInstance());
            holder.description.setText(bean.getDescription());
            Glide.with(ctx).load(bean.getImageURL()).into(holder.icon);
            holder.icon.setClickable(true);
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Glide.with(NASARssfeedDisplay.this).asBitmap().load(bean.getImageURL()).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            WallpaperManager wm = WallpaperManager.getInstance(NASARssfeedDisplay.this);
                            Bitmap bitmap;
                            DisplayMetrics metrics = new DisplayMetrics();
                            getWindowManager().getDefaultDisplay().getMetrics(metrics);

                            int height = metrics.heightPixels;
                            int width = metrics.widthPixels;

                            bitmap = Bitmap
                                    .createScaledBitmap(resource, width, height, true);
                            try {

                                wm.setBitmap(bitmap);

                                Toast.makeText(ctx,"Wallpaper Set",Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(ctx,"Wallpaper Set Failed",Toast.LENGTH_LONG).show();
                            }
                            finally {
                                bitmap.recycle();


                            }
                        }



                    });
                }
            });

            return convertView;
        }
    }

}
