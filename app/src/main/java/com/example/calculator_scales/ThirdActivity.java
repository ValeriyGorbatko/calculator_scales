package com.example.calculator_scales;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;

import java.io.Console;
import java.util.List;

public class ThirdActivity extends AppCompatActivity
{
    private int total = 11;

    private String tag_1 = "tag_1";
    private String tag_2 = "tag_2";
    private String tag_3 = "tag_3";

    private float l0;
    private float dL0;
    private float[] nn = new float[total];
    private float[] nr = new float[total];
    private float[] nnDiff = new float[total];
    private float[] nrDiff = new float[total];
    private float[] nominalValues = new float[total];
    private float[] nnnr_output = new float[total];
    private float E0_output;

    Button btn_tab1;
    Button btn_tab2;
    EditText et_vim_zn_gir;
    EditText et_zag_mass_dgir;

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

    private TabHost.OnTabChangeListener onTabChanged = new TabHost.OnTabChangeListener() {
        @Override
        public void onTabChanged(String s)
        {
            if(s.equals(tag_1)) InitContent1();
            else if (s.equals(tag_2)) InitContent2();
            else if (s.equals(tag_3)) InitContent3();
        }
    };

    public abstract class TextChangedListener<T> implements TextWatcher {
        private T target;

        public TextChangedListener(T target) {
            this.target = target;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            this.onTextChanged(target, s);
        }

        public abstract void onTextChanged(T target, Editable s);
    }

    private void InitContent1()
    {
        btn_tab1 = findViewById(R.id.check_tab1);
        btn_tab2 = findViewById(R.id.check_tab2);

        et_vim_zn_gir = findViewById(R.id.vim_zn_gir);
        et_zag_mass_dgir = findViewById(R.id.zag_mass_dgir);

        SetButtonIdle(btn_tab1);
        SetButtonIdle(btn_tab2);

        for (int i = 0; i < 11; i++)
        {
            int nnId = getResources().getIdentifier("nn" + (i + 1), "id", getPackageName());
            EditText et_nn = findViewById(nnId);
            et_nn.addTextChangedListener(SubscribeNN(i, et_nn));

            int nrId = getResources().getIdentifier("nr" + (i + 1), "id", getPackageName());
            EditText et_nr = findViewById(nrId);
            et_nr.addTextChangedListener(SubscribeNR(i, et_nr));
        }

        et_vim_zn_gir.addTextChangedListener(new TextChangedListener<EditText>(et_vim_zn_gir) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab1);
                if(!MainActivity.IsValueValid(et_vim_zn_gir.getText())) l0 = 0;
                else l0 = Float.valueOf(et_vim_zn_gir.getText().toString());
            }
        });
        et_zag_mass_dgir.addTextChangedListener(new TextChangedListener<EditText>(et_zag_mass_dgir) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab1);
                if(!MainActivity.IsValueValid(et_zag_mass_dgir.getText())) dL0 = 0;
                else dL0 = Float.valueOf(et_zag_mass_dgir.getText().toString());
            }
        });

        btn_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean success = CheckTab1();
                if(success) SetButtonSuccess(btn_tab1);
                else SetButtonFail(btn_tab1);
            }
        });

        btn_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean success = CheckTab2();
                if(success) SetButtonSuccess(btn_tab2);
                else SetButtonFail(btn_tab2);
            }
        });

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

            //всё ровно
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

    private float CheckValueByAccuracy(float value)
    {
        MainActivity.AccuracyData accuracyData = MainActivity.accuracyData[MainActivity.GetAccuracyIndex()];
        for (int a = 0; a < accuracyData.Range.length; a++)
        {
            MainActivity.AccuracyRange range = accuracyData.Range[a];
            if (value >= range.Min && value <= range.Max)
            {
                return range.Factor;
            }
        }
        return -1;
    }

    private boolean CheckTab1()
    {
        float e = MainActivity.GetScaleDivisionValue();
        float L0 = (e * 10f) * 1000f;
        float E0 = (l0 - L0 + (e * 0.5f) - dL0) / 1000f;

        float factor = CheckValueByAccuracy(E0);
        if(factor != -1)
        {
            E0_output = (E0 * 1000f) * factor;
            return true;
        }

        return false;
    }

    private boolean CheckTab2()
    {
        for (int i = 0; i < total; i++)
        {
            nnDiff[i] = nn[i] - nominalValues[i];
            nrDiff[i] = nr[i] - nominalValues[i];
        }

        int successCount = 0;
        MainActivity.AccuracyData accuracyData = MainActivity.accuracyData[MainActivity.GetAccuracyIndex()];
        for (int i = 0; i < total; i++)
        {
            float value = nnDiff[i];
            if(value <= 0) value = nrDiff[i];

            float factor = CheckValueByAccuracy(value);
            if(factor != -1)
            {
                nnnr_output[i] = MainActivity.GetScaleDivisionValue() * factor;
                successCount++;
            }
        }

        return successCount == total;
    }

    private void SetButtonSuccess(Button btn)
    {
        btn.setText("ГОДЕН");
        int color = getResources().getColor(R.color.green);
        btn.setBackgroundColor(color);
    }

    private void SetButtonFail(Button btn)
    {
        btn.setText("НЕ ГОДЕН");
        int color = getResources().getColor(R.color.red);
        btn.setBackgroundColor(color);
    }

    private void SetButtonIdle(Button btn)
    {
        btn.setText("ПРОВЕРИТЬ");
        btn.setBackgroundColor(getResources().getColor(R.color.bc));
    }

    private TextChangedListener<EditText> SubscribeNN(int index, EditText editText)
    {
        return new TextChangedListener<EditText>(editText)
        {
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab2);
                float value = 0;
                if(MainActivity.IsValueValid(s)) value = Float.valueOf(s.toString());
                SetNN(index, value);
            }
        };
    }

    private void SetNN(int index, float value)
    {
        for (int i = 0; i < nn.length; i++)
        {
            if(i == index)
            {
                nn[i] = value;
                return;
            }
        }
    }

    private TextChangedListener<EditText> SubscribeNR(int index, EditText editText)
    {
        return new TextChangedListener<EditText>(editText)
        {
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab2);
                float value = 0;
                if(MainActivity.IsValueValid(s)) value = Float.valueOf(s.toString());
                SetNR(index, value);
            }
        };
    }

    private void SetNR(int index, float value)
    {
        for (int i = 0; i < nr.length; i++)
        {
            if(i == index)
            {
                nr[i] = value;
                return;
            }
        }
    }



    private void InitContent2()
    {

    }

    private void InitContent3()
    {

    }
}