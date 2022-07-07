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
import android.widget.TabWidget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private float E0;
    private float E0_output;

    private float uniq_e_tab1;
    private float uniq_e_tab2;

    Button btn_tab1;
    Button btn_tab2;

    EditText et_vim_zn_gir;
    EditText et_zag_mass_dgir;

    EditText et_uniq_e_tab1;
    EditText et_uniq_e_tab2;

    boolean successContent1Tab1 = false;
    boolean successContent1Tab2 = false;

    //Content 2
    private float [] mg = new float[3];
    private float [] vmg = new float[3];
    private float [] mgDiff = new float[3];
    private float [] mgvmg_output = new float[3];

    private float [] ves = new float[3];
    private float [] zch = new float[3];
    private float sensitivity;

    private float uniq_e_tab3;

    Button btn_tab3;
    Button btn_tab4;

    Spinner sp_form;
    View currentLayout;

    EditText et_uniq_e_tab3;

    boolean successContent2Tab3 = false;
    boolean successContent2Tab4 = false;

    //Content 3
    boolean isActiveContent3 = false;
    private float[] hmax = new float[10];
    private float[] max = new float[10];
    private float diff_hmax;
    private float diff_max;
    private float _1e1;
    private float _1e2;

    private float uniq_e_tab5;

    Button btn_tab5;
    EditText et_uniq_e_tab5;

    boolean successContent3Tab5 = false;

    //Content 4
    private int nzTotal = 5;
    private float zt1;
    private float zt2;
    private float[] nz1 = new float[nzTotal];
    private float[] nz2 = new float[nzTotal];
    private float[] nzn1 = new float[nzTotal];
    private float[] nzn2 = new float[nzTotal];
    private float[] nzr1 = new float[nzTotal];
    private float[] nzr2 = new float[nzTotal];
    private float[] nz1Diff1 = new float[nzTotal];
    private float[] nz1Diff2 = new float[nzTotal];
    private float[] nz2Diff1 = new float[nzTotal];
    private float[] nz2Diff2 = new float[nzTotal];
    private float[] nz1_output = new float[nzTotal];
    private float[] nz2_output = new float[nzTotal];

    private float uniq_e_tab6;

    Button btn_tab6;
    Button btn_protocol;

    EditText et_uniq_e_tab6;

    boolean successContent4Tab6 = false;



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


        isActiveContent3 = MainActivity.GetAccuracyIndex() == 0 || MainActivity.GetAccuracyIndex() == 1 ||
                (MainActivity.GetAccuracyIndex() == 2 && MainActivity.GetMaxNavValue() <= 50f);
        if(isActiveContent3)
        {
            LinearLayout ll3 = findViewById(R.id.linearLayout3);
            View tabs3 = inflater.inflate(R.layout.tabs03, null);
            ll3.addView(tabs3);

            tabSpec = tabHost.newTabSpec(tag_3);
            tabSpec.setContent(R.id.linearLayout3);
            tabSpec.setIndicator("3");
            tabHost.addTab(tabSpec);
        }

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
        btn.setText("придатний");
        int color = getResources().getColor(R.color.green);
        btn.setBackgroundColor(color);
    }

    private void SetButtonFail(Button btn) {
        btn.setText("не придатний");
        int color = getResources().getColor(R.color.red);
        btn.setBackgroundColor(color);
    }

    private void SetButtonIdle(Button btn) {
        btn.setText("Висновок");
        btn.setBackgroundColor(getResources().getColor(R.color.bc));
    }

    private class Pair {
        public float Min;
        public float Max;
    }

    private Pair GetMinMax(float[] array) {
        Pair pair = new Pair();
        int count = array.length;

        if(count == 1)
        {
            pair.Min = array[0];
            pair.Max = array[0];
            return pair;
        }

        if (array[0] > array[1])
        {
            pair.Max = array[0];
            pair.Min = array[1];
        }
        else
        {
            pair.Max = array[1];
            pair.Min = array[0];
        }

        int i;
        for(i = 2; i < count; i++)
        {
            if (array[i] > pair.Max)
                pair.Max = array[i];

            else if (array[i] < pair.Min)
                pair.Min = array[i];
        }

        return pair;
    }


    //Content 1
    private void InitContent1() {
        btn_tab1 = findViewById(R.id.check_tab1);
        btn_tab2 = findViewById(R.id.check_tab2);

        et_vim_zn_gir = findViewById(R.id.vim_zn_gir);
        et_zag_mass_dgir = findViewById(R.id.zag_mass_dgir);
        et_uniq_e_tab1 = findViewById(R.id.uniq_e_tab1);
        et_uniq_e_tab2 = findViewById(R.id.uniq_e_tab2);

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

        et_uniq_e_tab1.addTextChangedListener(new TextChangedListener<EditText>(et_uniq_e_tab1) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab1);
                if(!MainActivity.IsValueValid(et_uniq_e_tab1.getText())) uniq_e_tab1 = 0;
                else uniq_e_tab1 = Math.abs(Float.valueOf(et_uniq_e_tab1.getText().toString()));
            }
        });

        et_uniq_e_tab2.addTextChangedListener(new TextChangedListener<EditText>(et_uniq_e_tab2) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab2);
                if(!MainActivity.IsValueValid(et_uniq_e_tab2.getText())) uniq_e_tab2 = 0;
                else uniq_e_tab2 = Math.abs(Float.valueOf(et_uniq_e_tab2.getText().toString()));
            }
        });

        btn_tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                successContent1Tab1 = CheckContent1Tab1();
                if(successContent1Tab1) SetButtonSuccess(btn_tab1);
                else SetButtonFail(btn_tab1);
            }
        });

        btn_tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                successContent1Tab2 = CheckContent1Tab2();
                if(successContent1Tab2) SetButtonSuccess(btn_tab2);
                else SetButtonFail(btn_tab2);
            }
        });

        float minVal = MainActivity.GetMinNavValue();
        nominalValues[0] = minVal;
        float maxVal = MainActivity.GetMaxNavValue();
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
        E0 = (l0 - L0 + ((e * 0.5f) * 1000) - dL0);

        float factor = CheckValueByAccuracy(E0);
        boolean isUniqE = uniq_e_tab1 != 0;
        boolean inRange = isUniqE ? E0 >= -uniq_e_tab1 && E0 <= uniq_e_tab1 :factor != -1;
        E0_output = isUniqE ? uniq_e_tab1 : E0 * factor;
        if(inRange)
        {
            float abs = Math.abs(E0_output);
            if(E0 >= -abs && E0 <= abs) return true;
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
        for (int i = 0; i < total; i++)
        {
            float value = nnDiff[i];
            if(value <= 0) value = nrDiff[i];

            float factor = CheckValueByAccuracy(value);
            boolean isUniqE = uniq_e_tab2 != 0;
            boolean inRange = isUniqE ? value >= -uniq_e_tab2 && value <= uniq_e_tab2 : factor != -1;
            nnnr_output[i] = isUniqE ? uniq_e_tab2 : MainActivity.GetScaleDivisionValue() * factor;
            if(inRange)
            {
                float abs = Math.abs(nnnr_output[i]);
                if(value >= -abs && value <= abs)
                {
                    successCount++;
                }
            }
        }

        return successCount == total;
    }


    //Content 2
    private void InitContent2() {
        btn_tab3 = findViewById(R.id.check_tab3);
        btn_tab4 = findViewById(R.id.check_tab4);

        et_uniq_e_tab3 = findViewById(R.id.uniq_e_tab3);

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

        et_uniq_e_tab3.addTextChangedListener(new TextChangedListener<EditText>(et_uniq_e_tab3) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab3);
                if(!MainActivity.IsValueValid(et_uniq_e_tab3.getText())) uniq_e_tab3 = 0;
                else uniq_e_tab3 = Math.abs(Float.valueOf(et_uniq_e_tab3.getText().toString()));
            }
        });
    }

    private void InitContent2Form(int count) {
        float min = MainActivity.GetMinNavValue();
        float max = MainActivity.GetMaxNavValue();

        mg = new float[count];
        vmg = new float[count];
        for (int i = 0; i < count; i++)
        {
            int mgId = getResources().getIdentifier("mg" + (i + 1),"id", getPackageName());
            EditText et_mg = findViewById(mgId);
            mg[i] = Math.round(max / 3f);
            et_mg.setText(String.valueOf(mg[i]));

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
                successContent2Tab3 = CheckContent2Tab3(count);
                if(successContent2Tab3) SetButtonSuccess(btn_tab3);
                else SetButtonFail(btn_tab3);
            }
        });

        btn_tab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                successContent2Tab4 = CheckContent2Tab4(count);
                if(successContent2Tab4) SetButtonSuccess(btn_tab4);
                else SetButtonFail(btn_tab4);
            }
        });
    }

    private boolean CheckContent2Tab3(int count) {
        mgDiff = new float[count];
        mgvmg_output = new float[count];
        int successCount = 0;
        for (int i = 0; i < count; i++)
        {
            mgDiff[i] = vmg[i] - Math.round(MainActivity.GetMaxNavValue() / 3f);
            float factor = CheckValueByAccuracy(mgDiff[i]);
            boolean isUniqE = uniq_e_tab3 != 0;
            boolean inRange = isUniqE ? mgDiff[i] >= -uniq_e_tab3 && mgDiff[i] <= uniq_e_tab3 : factor != -1;
            mgvmg_output[i] = isUniqE ? uniq_e_tab3 : mgDiff[i] * factor;
            if(inRange)
            {
                float abs = Math.abs(mgvmg_output[i]);
                if(mgDiff[i] >= -abs && mgDiff[i] <= abs) successCount++;
            }
        }

        return successCount == count;
    }

    private boolean CheckContent2Tab4(int count) {
        sensitivity = 1.4f * MainActivity.GetDivisionsValidValue();

        int successCount = 0;
        for (int i = 0; i < 3; i++)
        {
            if(zch[i] <= sensitivity) successCount++;
        }

        return successCount == 3;
    }




    //Content 3
    private void InitContent3() {
        btn_tab5 = findViewById(R.id.check_tab5);
        SetButtonIdle(btn_tab5);

        et_uniq_e_tab5 = findViewById(R.id.uniq_e_tab5);

        _1e1 = MainActivity.GetScaleDivisionValue();
        EditText et_1e1 = findViewById(R.id._1e1);
        et_1e1.setText(String.valueOf(_1e1));

        _1e2 = MainActivity.GetScaleDivisionValue();
        EditText et_1e2 = findViewById(R.id._1e2);
        et_1e2.setText(String.valueOf(_1e2));

        for (int i = 0; i < 10; i++)
        {
            int number = i + 1;

            int hmaxId = getResources().getIdentifier("hmax" + number, "id", getPackageName());
            EditText et_hmax = findViewById(hmaxId);
            et_hmax.addTextChangedListener(SubscribeEditText(i, et_hmax, btn_tab5, hmax));

            int maxId = getResources().getIdentifier("max" + number, "id", getPackageName());
            EditText et_max = findViewById(maxId);
            et_max.addTextChangedListener(SubscribeEditText(i, et_max, btn_tab5, max));
        }

        btn_tab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                successContent3Tab5 = CheckContent3Tab5();
                if(successContent3Tab5) SetButtonSuccess(btn_tab5);
                else SetButtonFail(btn_tab5);
            }
        });

        et_uniq_e_tab5.addTextChangedListener(new TextChangedListener<EditText>(et_uniq_e_tab5) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab5);
                if(!MainActivity.IsValueValid(et_uniq_e_tab5.getText())) uniq_e_tab5 = 0;
                else uniq_e_tab5 = Math.abs(Float.valueOf(et_uniq_e_tab5.getText().toString()));
            }
        });
    }

    private boolean CheckContent3Tab5() {
        Pair pair1 = GetMinMax(hmax);
        float foundMin1 = pair1.Min;
        float foundMax1 = pair1.Max;
        diff_hmax = foundMax1 - foundMin1;
        EditText et_diff_hmax = findViewById(R.id.diff_hmax);
        et_diff_hmax.setText(String.valueOf(diff_hmax * 1000f));

        Pair pair2 = GetMinMax(max);
        float foundMin2 = pair2.Min;
        float foundMax2 = pair2.Max;
        diff_max = foundMax2 - foundMin2;
        EditText et_diff_max = findViewById(R.id.diff_max);
        et_diff_max.setText(String.valueOf(diff_max));

        //надо ли домножать...................................................................................
        float factor1 = CheckValueByAccuracy(diff_hmax);
        float factor2 = CheckValueByAccuracy(diff_max);

        boolean isUniqE = uniq_e_tab5 != 0;

        boolean success1 = isUniqE ? diff_hmax >= -uniq_e_tab5 && diff_hmax <= uniq_e_tab5 : factor1 != -1;
        boolean success2 = isUniqE ? diff_max >= -uniq_e_tab5 && diff_max <= uniq_e_tab5 : factor2 != -1;

        return success1 && success2;
    }





    private void InitContent4() {
        btn_tab6 = findViewById(R.id.check_tab6);
        SetButtonIdle(btn_tab6);

        et_uniq_e_tab6 = findViewById(R.id.uniq_e_tab6);

        btn_protocol = findViewById(R.id.protocol_button);
        btn_protocol.setText("Зберегти");
        int color = getResources().getColor(R.color.bc);
        btn_protocol.setBackgroundColor(color);

        zt1 = MainActivity.GetMaxNavValue() * 0.1f;
        zt1 -= zt1 % 5;

        EditText et_zt1 = findViewById(R.id.zt1);
        et_zt1.setText(String.valueOf(zt1));


        zt2 = MainActivity.GetMaxNavValue() * 0.7f;
        zt2 -= zt2 % 5;

        EditText et_zt2 = findViewById(R.id.zt2);
        et_zt2.setText(String.valueOf(zt2));

        for (int i = 0; i < nzTotal; i++)
        {
            int nzn1Id = getResources().getIdentifier("nzn" + (i + 1), "id", getPackageName());
            EditText et_nzn1 = findViewById(nzn1Id);
            et_nzn1.addTextChangedListener(SubscribeEditText(i, et_nzn1, btn_tab6, nzn1));

            int nzr1Id = getResources().getIdentifier("nzr" + (i + 1), "id", getPackageName());
            EditText et_nzr1 = findViewById(nzr1Id);
            et_nzr1.addTextChangedListener(SubscribeEditText(i, et_nzr1, btn_tab6, nzr1));

            int nzn2Id = getResources().getIdentifier("nzn" + (i + nzTotal + 1), "id", getPackageName());
            EditText et_nzn2 = findViewById(nzn2Id);
            et_nzn2.addTextChangedListener(SubscribeEditText(i, et_nzn2, btn_tab6, nzn2));

            int nzr2Id = getResources().getIdentifier("nzr" + (i + nzTotal + 1), "id", getPackageName());
            EditText et_nzr2 = findViewById(nzr2Id);
            et_nzr2.addTextChangedListener(SubscribeEditText(i, et_nzr2, btn_tab6, nzr2));
        }

        for (int i = 1; i <= nzTotal; i++)
        {
            float origin = MainActivity.GetMaxNavValue() - zt1;

            float valPerItem = origin / 5;
            float newValue = valPerItem * i;
            newValue = Math.round(newValue);
            int step = origin >= 25 ? 5 : 1;
            newValue -= newValue % step;
            nz1[i - 1] = newValue;

            int nzId = getResources().getIdentifier("nz" + i, "id", getPackageName());
            EditText et_nz = findViewById(nzId);
            et_nz.setText(String.valueOf(newValue));
        }

        for (int i = 1; i <= nzTotal; i++)
        {
            float origin = MainActivity.GetMaxNavValue() - zt2;

            float valPerItem = origin / 5;
            float newValue = valPerItem * i;
            newValue = Math.round(newValue);
            int step = origin >= 25 ? 5 : 1;
            newValue -= newValue % step;
            nz2[i - 1] = newValue;

            int nzId = getResources().getIdentifier("nz" + (i + nzTotal), "id", getPackageName());
            EditText et_nz = findViewById(nzId);
            et_nz.setText(String.valueOf(newValue));
        }

        btn_tab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                successContent4Tab6 = CheckContent4Tab6();
                if(successContent4Tab6) SetButtonSuccess(btn_tab6);
                else SetButtonFail(btn_tab6);

                btn_protocol.setText("Зберегти");
                int color = getResources().getColor(R.color.bc);
                btn_protocol.setBackgroundColor(color);
            }
        });

        et_uniq_e_tab6.addTextChangedListener(new TextChangedListener<EditText>(et_uniq_e_tab6) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(btn_tab6);
                if(!MainActivity.IsValueValid(et_uniq_e_tab6.getText())) uniq_e_tab6 = 0;
                else uniq_e_tab6 = Math.abs(Float.valueOf(et_uniq_e_tab6.getText().toString()));
            }
        });

        btn_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SaveToFile();
                btn_protocol.setText("Збережено!");
                int color = getResources().getColor(R.color.green);
                btn_protocol.setBackgroundColor(color);
            }
        });
    }

    private boolean CheckContent4Tab6() {
        for (int i = 0; i < nzTotal; i++)
        {
            nz1Diff1[i] = nzn1[i] - nz1[i];
            nz1Diff2[i] = nzr1[i] - nz1[i];

            nz2Diff1[i] = nzn2[i] - nz2[i];
            nz2Diff2[i] = nzr2[i] - nz2[i];
        }

        boolean isUniqE = uniq_e_tab6 != 0;

        int successCount = 0;
        for (int i = 0; i < nzTotal; i++)
        {
            float value1 = nz1Diff1[i];
            if(value1 <= 0) value1 = nz1Diff2[i];

            float factor1 = CheckValueByAccuracy(value1);

            boolean inRange1 = isUniqE ? value1 >= -uniq_e_tab6 && value1 <= uniq_e_tab6 : factor1 != -1;
            nz1_output[i] = isUniqE ? uniq_e_tab6 : MainActivity.GetScaleDivisionValue() * factor1;
            if(inRange1)
            {
                float abs = Math.abs(nz1_output[i]);
                if(value1 >= -abs && value1 <= abs) successCount++;
            }

            float value2 = nz2Diff1[i];
            if(value2 <= 0) value2 = nz2Diff2[i];

            float factor2 = CheckValueByAccuracy(value2);
            boolean inRange2 = isUniqE ? value2 >= -uniq_e_tab6 && value2 <= uniq_e_tab6 : factor2 != -1;
            nz2_output[i] = isUniqE ? uniq_e_tab6 : MainActivity.GetScaleDivisionValue() * factor2;
            if(inRange2)
            {
                float abs = Math.abs(nz2_output[i]);
                if(value2 >= -abs && value2 <= abs) successCount++;
            }
        }

        return successCount == nzTotal * 2;
    }

    private void SaveToFile() {
        String ten = "#,###"; //0,00
        DecimalFormat decimalFormatter = new DecimalFormat(ten);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        Date now = new Date(System.currentTimeMillis());

        //Берём текущий номер протокола
        int protocol_number = MainActivity.GetProtocolNumber();

        //Объявляем название будущего файла с текущим номером протокола
        String new_protocol_filename = "Протокол №" + protocol_number + " " + dateFormatter.format(now) + ".xml";

        //Название папки с протоколами
        String protocol_folders_name = "Протоколы ваги";

        //Пытаемся считать нужный нам шаблон. (160х160, 250х250 и т.д.)
        try (InputStream input = getResources().openRawResource(getResources().getIdentifier("first","raw", getPackageName())))
        {
            //Считываем байты с шаблона
            byte[] inputBytes = GetBytes(input);

            //Редактируем наш будущий файл, находя нужные нам поля по ключам.
            String content;

            //Титульный экран
            content = new String(inputBytes).replace("keyProtocolNumber", String.valueOf(protocol_number));

            String key = "keyType";
            String origin = "<w:t>" + key + "</w:t>";
            String newVal = origin.replace(key, MainActivity.GetZVTType());
            content = content.replace(origin, newVal);

            content = content.replace("keyPidrozdil", MainActivity.GetPidrozdil());
            content = content.replace("keyZavnum", MainActivity.GetFactoryNumber());
            content = content.replace("keyPovScale", String.valueOf(MainActivity.GetScaleDivisionValue()));
            content = content.replace("keyVlasnik", MainActivity.GetOwner());
            content = content.replace("keyMax", String.valueOf(MainActivity.GetMaxNavValue()));
            content = content.replace("keyClass", getResources().getStringArray(R.array.accuracy)[MainActivity.GetAccuracyIndex()]);
            content = content.replace("keyMin", String.valueOf(MainActivity.GetMinNavValue()));
            content = content.replace("keyTruScale", String.valueOf(MainActivity.GetDivisionsValidValue()));

            key = "keyTypePovir";
            origin = "<w:t>" + key + "</w:t>";
            newVal = origin.replace(key, getResources().getStringArray(R.array.type_ver)[MainActivity.GetTypeVerificationIndex()]);
            content = content.replace(origin, newVal);


            //Таблица №1

            float nomMass = MainActivity.GetScaleDivisionValue();
            float nomMass10 = nomMass * 10f;
            float nomMass1000 = nomMass10 * 1000f;
            content = content.replace("keyNomMass", decimalFormatter.format(nomMass1000));

            float vimZna = l0;
            content = content.replace("keyVimZna", String.valueOf(vimZna));

            content = content.replace("keyDodatkgir", String.valueOf(dL0));
            content = content.replace("keyZnabsolute", decimalFormatter.format(E0));

            key = "Gdoap1";
            origin = "<w:t>" + key + "</w:t>";
            newVal = origin.replace(key, decimalFormatter.format(E0_output));
            content = content.replace(origin, newVal);

            content = content.replace("keytable1", successContent1Tab1 ? "Придатний" : "Не придатний");


            //Таблица №2
            for (int i = 0; i < total; i++)
            {
                key = "kNM" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nominalValues[i]));
                content = content.replace(origin, newVal);

                key = "keyNavant" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nn[i]));
                content = content.replace(origin, newVal);

                key = "keyRozvant" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nr[i]));
                content = content.replace(origin, newVal);

                key = "keyAbsNavant" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nnDiff[i]));
                content = content.replace(origin, newVal);

                key = "keyAbsRozvant" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nrDiff[i]));
                content = content.replace(origin, newVal);

                key = "Gdoap" + (i + 2);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nnnr_output[i]));
                content = content.replace(origin, newVal);
            }
            content = content.replace("keytable2", successContent1Tab2 ? "Придатний" : "Не придатний");


            //Таблица №3
            content = content.replace("Keyformtype", getResources().getStringArray(R.array.form_vag)[sp_form.getSelectedItemPosition()]);

            for (int i = 0; i < 5; i++)
            {
                key = "kNM" + (i + 12);
                origin = "<w:t>" + key + "</w:t>";
                float value = 0;
                if(i < mg.length) value = mg[i];
                newVal = origin.replace(key, String.valueOf(value));
                content = content.replace(origin, newVal);

                key = "Vzmg" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                value = 0;
                if(i < vmg.length) value = vmg[i];
                newVal = origin.replace(key, String.valueOf(value));
                content = content.replace(origin, newVal);

                key = "Zap" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                value = 0;
                if(i < mgDiff.length) value = mgDiff[i];
                newVal = origin.replace(key, String.valueOf(value));
                content = content.replace(origin, newVal);
            }

            key = "Gdoap13";
            origin = "<w:t>" + key + "</w:t>";
            newVal = origin.replace(key, String.valueOf(mgvmg_output[0]));
            content = content.replace(origin, newVal);

            content = content.replace("keytable3", successContent2Tab3 ? "Придатний" : "Не придатний");


            //Таблица №4
            for (int i = 0; i < ves.length; i++)
            {
                key = "kNM" + (i + 17);
                origin = "<w:t>" + key + "</w:t>";
                float value = ves[i];
                newVal = origin.replace(key, String.valueOf(value));
                content = content.replace(origin, newVal);

                key = "Zch" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                value = zch[i];
                newVal = origin.replace(key, String.valueOf(value));
                content = content.replace(origin, newVal);
            }

            key = "Dzpch1";
            origin = "<w:t>" + key + "</w:t>";
            newVal = origin.replace(key, decimalFormatter.format(sensitivity));
            content = content.replace(origin, newVal);

            content = content.replace("keytable4", successContent2Tab4 ? "Придатний" : "Не придатний");


            //Таблица №5
            for (int i = 0; i < 10; i++)
            {
                key = "Pm" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                float value = isActiveContent3 ? hmax[i] : 0;
                newVal = origin.replace(key, String.valueOf(value));
                content = content.replace(origin, newVal);

                key = "M" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                value = isActiveContent3 ? max[i] : 0;
                newVal = origin.replace(key, String.valueOf(value));
                content = content.replace(origin, newVal);
            }

            key = "Pm11";
            origin = "<w:t>" + key + "</w:t>";
            newVal = origin.replace(key, String.valueOf(isActiveContent3 ? diff_hmax : 0));
            content = content.replace(origin, newVal);

            key = "M11";
            origin = "<w:t>" + key + "</w:t>";
            newVal = origin.replace(key, String.valueOf(isActiveContent3 ? diff_max : 0));
            content = content.replace(origin, newVal);

            key = "Pm12";
            origin = "<w:t>" + key + "</w:t>";
            newVal = origin.replace(key, String.valueOf(isActiveContent3 ? uniq_e_tab5 != 0 ? uniq_e_tab5 : _1e1 : 0));
            content = content.replace(origin, newVal);

            key = "M12";
            origin = "<w:t>" + key + "</w:t>";
            newVal = origin.replace(key, String.valueOf(isActiveContent3 ? uniq_e_tab5 != 0 ? uniq_e_tab5 : _1e2 : 0));
            content = content.replace(origin, newVal);

            content = content.replace("keytable5", isActiveContent3 ? successContent3Tab5 ? "Придатний" : "Не придатний" : "Не використовується");


            //Таблица №6
            content = content.replace("Zt1", String.valueOf(zt1));
            content = content.replace("Zt2", String.valueOf(zt2));

            for (int i = 1; i <= nzTotal; i++)
            {
                key = "Nzmg" + i;
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nz1[i - 1]));
                content = content.replace(origin, newVal);

                key = "Nzmg" + (i + nzTotal);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nz2[i - 1]));
                content = content.replace(origin, newVal);

                key = "vzPn" + i;
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nzn1[i - 1]));
                content = content.replace(origin, newVal);

                key = "vzPn" + (i + nzTotal);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nzn2[i - 1]));
                content = content.replace(origin, newVal);

                key = "vzPr" + i;
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nzr1[i - 1]));
                content = content.replace(origin, newVal);

                key = "vzPr" + (i + nzTotal);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nzr2[i - 1]));
                content = content.replace(origin, newVal);

                key = "Zppn" + i;
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nz1Diff1[i - 1]));
                content = content.replace(origin, newVal);

                key = "Zppn" + (i + nzTotal);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nz2Diff1[i - 1]));
                content = content.replace(origin, newVal);

                key = "Zppr" + i;
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nz1Diff2[i - 1]));
                content = content.replace(origin, newVal);

                key = "Zppr" + (i + nzTotal);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nz2Diff2[i - 1]));
                content = content.replace(origin, newVal);

                key = "Gdoap" + (i + 13);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nz1_output[i - 1]));
                content = content.replace(origin, newVal);

                key = "Gdoap" + (i + 13 + nzTotal);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(nz2_output[i - 1]));
                content = content.replace(origin, newVal);
            }
            content = content.replace("keytable6", successContent4Tab6 ? "Придатний" : "Не придатний");




            content = content.replace("keyvikPovir", MainActivity.GetVikPovir());

            boolean success =
                    successContent1Tab1 &&
                    successContent1Tab2 &&
                    successContent2Tab3 &&
                    successContent2Tab4 &&
                    successContent4Tab6;
            boolean totalSuccess = isActiveContent3 ? success && successContent3Tab5 : success;
            content = content.replace("totalSuccess", totalSuccess ? "Придатний" : "Не придатний");

            content = content.replace("date", dateFormatter.format(now));




            //Получаем или создаём папку, куда будет сохранён наш новый файл
            File folder = new File("/storage/emulated/0/", protocol_folders_name);
            if(!folder.mkdir()) folder.mkdir();

            //Записываем отредактируемую строку в файл
            File new_protocol = new File(folder, new_protocol_filename);
            try (OutputStream output = new FileOutputStream(new_protocol))
            {
                output.write(content.getBytes());
            }
            //Проверка на ошибки
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        //Проверка на ошибки
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Увеличиваем номер следующего протокола
        MainActivity.IncreaseProtocolNumber();
    }

    private byte[] GetBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1)
        {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }
}