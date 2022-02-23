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

import java.lang.reflect.Array;

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

    //Content 3
    private float[] hmax = new float[10];
    private float[] max = new float[10];
    private float diff_hmax;
    private float diff_max;
    private float _1e1;
    private float _1e2;

    Button btn_tab5;

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

    Button btn_tab6;



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


        if(MainActivity.GetAccuracyIndex() == 0 || MainActivity.GetAccuracyIndex() == 1 ||
                (MainActivity.GetAccuracyIndex() == 2 && MainActivity.GetMaxNavValue() <= 50f))
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




    //Content 3
    private void InitContent3() {
        btn_tab5 = findViewById(R.id.check_tab5);
        SetButtonIdle(btn_tab5);

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
                boolean success = CheckContent3Tab5();
                if(success) SetButtonSuccess(btn_tab5);
                else SetButtonFail(btn_tab5);
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

        boolean success1 = factor1 != -1;
        boolean success2 = factor2 != -1;

        return success1 && success2;
    }





    private void InitContent4()
    {
        btn_tab6 = findViewById(R.id.check_tab6);
        SetButtonIdle(btn_tab6);

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
                boolean success = CheckContent4Tab6();
                if(success) SetButtonSuccess(btn_tab6);
                else SetButtonFail(btn_tab6);
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

        int successCount = 0;
        for (int i = 0; i < nzTotal; i++)
        {
            float value1 = nz1Diff1[i];
            if(value1 <= 0) value1 = nz1Diff2[i];

            float factor1 = CheckValueByAccuracy(value1);
            if(factor1 != -1)
            {
                nz1_output[i] = MainActivity.GetScaleDivisionValue() * factor1;
                successCount++;
            }

            float value2 = nz2Diff1[i];
            if(value2 <= 0) value2 = nz2Diff2[i];

            float factor2 = CheckValueByAccuracy(value2);
            if(factor2 != -1)
            {
                nz2_output[i] = MainActivity.GetScaleDivisionValue() * factor2;
                successCount++;
            }
        }

        return successCount == nzTotal * 2;
    }
}