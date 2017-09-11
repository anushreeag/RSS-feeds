package com.cosmic.nasarssfeeddisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class FeedDisplayActivity extends AppCompatActivity {

    public static final String KEPLER = "https://www.nasa.gov/rss/dyn/mission_pages/kepler/news/kepler-newsandfeatures-RSS.rss";
    public static final String CHANDRA = "https://www.nasa.gov/rss/dyn/chandra_images.rss";
    public static final String SOLAR = "https://www.nasa.gov/rss/dyn/solar_system.rss";
    public static final String HURRICANE = "https://www.nasa.gov/rss/dyn/hurricaneupdate.rss";
    public static final String FEED = "feed";

    TextView kepler;
    TextView chandra;
    TextView solar;
    TextView hurricane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_display);

        kepler = (TextView) findViewById(R.id.kepler);
        chandra = (TextView) findViewById(R.id.chandra);
        solar = (TextView) findViewById(R.id.solar);
        hurricane = (TextView) findViewById(R.id.hurricane);


        kepler.setMovementMethod(LinkMovementMethod.getInstance());

        kepler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FeedDisplayActivity.this,NASARssfeedDisplay.class);
                intent.putExtra(FEED,KEPLER);
                startActivity(intent);
            }
        });


        chandra.setMovementMethod(LinkMovementMethod.getInstance());

        chandra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FeedDisplayActivity.this,NASARssfeedDisplay.class);
                intent.putExtra(FEED,CHANDRA);
                startActivity(intent);
            }
        });


        solar.setMovementMethod(LinkMovementMethod.getInstance());

        solar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FeedDisplayActivity.this,NASARssfeedDisplay.class);
                intent.putExtra(FEED,SOLAR);
                startActivity(intent);
            }
        });


        hurricane.setMovementMethod(LinkMovementMethod.getInstance());

        hurricane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(FeedDisplayActivity.this,NASARssfeedDisplay.class);
                intent.putExtra(FEED,HURRICANE);
                startActivity(intent);
            }
        });




    }



}
