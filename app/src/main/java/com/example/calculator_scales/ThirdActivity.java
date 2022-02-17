package com.example.calculator_scales;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;

import java.io.Console;
import java.util.List;

public class ThirdActivity extends AppCompatActivity
{
    private String tag_1 = "tag_1";
    private String tag_2 = "tag_2";
    private String tag_3 = "tag_3";

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

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag_1);
        tabSpec.setContent(R.id.linearLayout1);
        tabSpec.setIndicator("1");
        tabHost.addTab(tabSpec);


        LinearLayout ll2 = findViewById(R.id.linearLayout2);
        View tabs2 = inflater.inflate(R.layout.tabs02, null);
        ll2.addView(tabs2);

        tabSpec = tabHost.newTabSpec(tag_2);
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("2");
        tabHost.addTab(tabSpec);


        LinearLayout ll3 = findViewById(R.id.linearLayout3);
        View tabs3 = inflater.inflate(R.layout.tabs03, null);
        ll3.addView(tabs3);

        tabSpec = tabHost.newTabSpec(tag_3);
        tabSpec.setContent(R.id.linearLayout3);
        tabSpec.setIndicator("3");
        tabHost.addTab(tabSpec);

        tabHost.setOnTabChangedListener(onTabChanged);
        tabHost.setCurrentTab(0);
        InitContent1();
    }

    private TabHost.OnTabChangeListener onTabChanged = new TabHost.OnTabChangeListener()
    {
        @Override
        public void onTabChanged(String s)
        {
            if(s.equals(tag_1)) InitContent1();
            else if (s.equals(tag_2)) InitContent2();
            else if (s.equals(tag_3)) InitContent3();
        }
    };

    private void InitContent1()
    {
        int total = 11;

        float[] nominalValues = new float[total];
        float minVal = MainActivity.GetMinNavValue();
        nominalValues[0] = minVal;
        float maxVal = nominalValues[total - 1] = MainActivity.GetMaxNavValue();
        nominalValues[total - 1] = maxVal;

        EditText[] nominalInputs = new EditText[total];
        nominalInputs[0] = findViewById(R.id.n1);
        nominalInputs[1] = findViewById(R.id.n2);
        nominalInputs[2] = findViewById(R.id.n3);
        nominalInputs[3] = findViewById(R.id.n4);
        nominalInputs[4] = findViewById(R.id.n5);
        nominalInputs[5] = findViewById(R.id.n6);
        nominalInputs[6] = findViewById(R.id.n7);
        nominalInputs[7] = findViewById(R.id.n8);
        nominalInputs[8] = findViewById(R.id.n9);
        nominalInputs[9] = findViewById(R.id.n10);
        nominalInputs[10] = findViewById(R.id.n11);

        for (int i = 0; i < nominalValues.length; i++)
        {
            if(i == 0 || i == nominalValues.length - 1) continue;

            //do somethings...
            float valPerItem = 1f * (maxVal - minVal) / (total - 1);
            float newValue = (valPerItem * i) + minVal;
            newValue = Math.round(newValue);
            int step = maxVal >= 50 ? 5 : 1;
            newValue -= newValue % step;
            nominalValues[i] = newValue;

        }

        for (int i = 0; i < nominalInputs.length; i++)
        {
            nominalInputs[i].setText(String.valueOf(nominalValues[i]));
        }
    }

    private void InitContent2()
    {

    }

    private void InitContent3()
    {

    }
}