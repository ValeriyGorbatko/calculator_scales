package com.example.calculator_scales;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;

public class ThirdActivity extends AppCompatActivity
{
    private String tag_1 = "tag_1";
    private String tag_2 = "tag_2";
    private String tag_3 = "tag_3";
    private String tag_4 = "tag_4";

    //Content 1
    private int total = 11;
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

    //Content 2
    private float [] vmg = new float[3];
    private float [] mgDiff = new float[3];
    private float [] mgvmg_output = new float[3];

    private float [] ves = new float[3];
    private float [] zch = new float[3];
    private float sensitivity;

    Button btn_tab3;
    Button btn_tab4;
    Spinner sp_form;
    View currentLayout;

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

        LinearLayout ll4 = findViewById(R.id.linearLayout4);
        View tabs4 = inflater.inflate(R.layout.tabs04, null);
        ll4.addView(tabs4);

        tabSpec = tabHost.newTabSpec(tag_4);
        tabSpec.setContent(R.id.linearLayout4);
        tabSpec.setIndicator("4");
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
            else if (s.equals(tag_4)) InitContent4();
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

    private TextChangedListener<EditText> SubscribeEditText(int index, EditText editText, Button btn, float[] array) {
        return new TextChangedListener<EditText>(editText)
        {
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn);
                float value = 0;
                if(MainActivity.IsValueValid(s)) value = Float.valueOf(s.toString());
                array[index] = value;
            }
        };
    }

    private float CheckValueByAccuracy(float value) {
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

    private void SetButtonSuccess(Button btn) {
        btn.setText("ГОДЕН");
        int color = getResources().getColor(R.color.green);
        btn.setBackgroundColor(color);
    }

    private void SetButtonFail(Button btn) {
        btn.setText("НЕ ГОДЕН");
        int color = getResources().getColor(R.color.red);
        btn.setBackgroundColor(color);
    }

    private void SetButtonIdle(Button btn) {
        btn.setText("ПРОВЕРИТЬ");
        btn.setBackgroundColor(getResources().getColor(R.color.bc));
    }


    //Content 1
    private void InitContent1() {
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
            et_nn.addTextChangedListener(SubscribeEditText(i, et_nn, btn_tab1, nn));

            int nrId = getResources().getIdentifier("nr" + (i + 1), "id", getPackageName());
            EditText et_nr = findViewById(nrId);
            et_nr.addTextChangedListener(SubscribeEditText(i, et_nr, btn_tab2, nr));
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
                boolean success = CheckContent1Tab1();
                if(success) SetButtonSuccess(btn_tab1);
                else SetButtonFail(btn_tab1);
            }
        });

        btn_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean success = CheckContent1Tab2();
                if(success) SetButtonSuccess(btn_tab2);
                else SetButtonFail(btn_tab2);
            }
        });

        float minVal = MainActivity.GetMinNavValue();
        nominalValues[0] = minVal;
        float maxVal = nominalValues[total - 1] = MainActivity.GetMaxNavValue();
        nominalValues[total - 1] = maxVal;

        EditText[] nominalInputs = new EditText[total];
        for(int i = 0; i < nominalInputs.length; i++)
        {
            int id = getResources().getIdentifier("n" + (i + 1), "id", getPackageName());
            nominalInputs[i] = findViewById(id);
        }

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

    private boolean CheckContent1Tab1() {
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

    private boolean CheckContent1Tab2() {
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


    //Content 2
    private void InitContent2() {
        btn_tab3 = findViewById(R.id.check_tab3);
        btn_tab4 = findViewById(R.id.check_tab4);

        SetButtonIdle(btn_tab3);
        SetButtonIdle(btn_tab4);

        LinearLayout container = findViewById(R.id.forms);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sp_form = findViewById(R.id.spinner);
        sp_form.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(currentLayout != null) container.removeView(currentLayout);

                switch (i) {
                    case 0: //прямокутна
                        View layout3 = inflater.inflate(R.layout.forms_3, null);
                        container.addView(layout3);
                        currentLayout = layout3;
                        InitContent2Form(3);
                        break;
                    case 1: //трикутна
                        View layout4 = inflater.inflate(R.layout.forms_4, null);
                        container.addView(layout4);
                        currentLayout = layout4;
                        InitContent2Form(4);
                        break;
                    case 2: //кругла
                    case 3: //квадратна
                        View layout5 = inflater.inflate(R.layout.forms_5, null);
                        container.addView(layout5);
                        currentLayout = layout5;
                        InitContent2Form(5);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        sp_form.setSelection(0);
    }

    private void InitContent2Form(int count) {
        float min = MainActivity.GetMinNavValue();
        float max = MainActivity.GetMaxNavValue();

        vmg = new float[count];
        for (int i = 0; i < count; i++)
        {
            int mgId = getResources().getIdentifier("mg" + (i + 1),"id", getPackageName());
            EditText et_mg = findViewById(mgId);
            et_mg.setText(Math.round(max / 3f) + "");

            int vmgId = getResources().getIdentifier("vmg" + (i + 1),"id", getPackageName());
            EditText et_vmg = findViewById(vmgId);
            et_vmg.addTextChangedListener(SubscribeEditText(i, et_vmg, btn_tab3, vmg));
        }

        int veszchCount = 3;
        ves = new float[veszchCount];
        ves[0] = min;
        ves[1] = Math.round((max - min) / 2f);
        ves[2] = max;

        for (int i = 0; i < veszchCount; i++)
        {
            int vesId = getResources().getIdentifier("ves" + (i + 1),"id", getPackageName());
            EditText et_ves = findViewById(vesId);
            et_ves.setText(ves[i] + "");

            int zchId = getResources().getIdentifier("zch" + (i + 1),"id", getPackageName());
            EditText et_zch = findViewById(zchId);
            et_zch.addTextChangedListener(SubscribeEditText(i, et_zch, btn_tab4, zch));
        }

        btn_tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean success = CheckContent2Tab3(count);
                if(success) SetButtonSuccess(btn_tab3);
                else SetButtonFail(btn_tab3);
            }
        });

        btn_tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean success = CheckContent2Tab4(count);
                if(success) SetButtonSuccess(btn_tab4);
                else SetButtonFail(btn_tab4);
            }
        });
    }

    private boolean CheckContent2Tab3(int count) {
        mgDiff = new float[count];
        mgvmg_output = new float[count];
        int successCount = 0;
        MainActivity.AccuracyData accuracyData = MainActivity.accuracyData[MainActivity.GetAccuracyIndex()];
        for (int i = 0; i < count; i++)
        {
            mgDiff[i] = vmg[i] - Math.round(MainActivity.GetMaxNavValue() / 3f);
            float factor = CheckValueByAccuracy(mgDiff[i]);
            if(factor != -1)
            {
                mgvmg_output[i] = mgDiff[i] * factor;
                successCount++;
            }
        }

        return successCount == count;
    }

    private boolean CheckContent2Tab4(int count) {
        sensitivity = 1.4f * MainActivity.GetDivisionsValidValue();

        int successCount = 0;
        for (int i = 0; i < count; i++)
        {
            if(zch[i] <= sensitivity) successCount++;
        }

        return successCount == count;
    }





    private void InitContent3()
    {

    }

    private void InitContent4()
    {

    }
}