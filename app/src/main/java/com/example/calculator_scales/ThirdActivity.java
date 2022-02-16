package com.example.calculator_scales;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class ThirdActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TabHost tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();


        LinearLayout ll1 = findViewById(R.id.linearLayout1);
        View tabs1 = inflater.inflate(R.layout.tabs01, null);
        ll1.addView(tabs1);

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("1");
        tabHost.addTab(tabSpec);


        LinearLayout ll2 = findViewById(R.id.linearLayout2);
        View tabs2 = inflater.inflate(R.layout.tabs02, null);
        ll2.addView(tabs2);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("2");
        tabHost.addTab(tabSpec);


        LinearLayout ll3 = findViewById(R.id.linearLayout3);
        View tabs3 = inflater.inflate(R.layout.tabs03, null);
        ll3.addView(tabs3);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.linearLayout3);
        tabSpec.setIndicator("3");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }
}