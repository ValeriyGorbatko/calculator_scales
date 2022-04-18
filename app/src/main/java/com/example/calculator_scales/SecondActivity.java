package com.example.calculator_scales;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//РІВНОПЛЕЧІ ВАГИ
public class SecondActivity extends AppCompatActivity
 {
     private int count = 18;

     private String[] vl = new String[count];
     private String[] vp = new String[count];
     private float[] shl = new float[count];
     private float[] shp = new float[count];
     private float[] srv = new float[count];

     private float dL1;
     private float dL2;
     private float dL3;
     private float dL4;
     private float dL5;

     private float d1;
     private float d2;
     private float d3;
     private float d4;

     Button calculate_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SetButtonIdle(calculate_btn);

        for (int i = 0; i < count; i++)
        {
            int vlId = getResources().getIdentifier("vl" + (i + 1), "id", getPackageName());
            EditText et_vl = findViewById(vlId);
            et_vl.addTextChangedListener(SubscribeEditText(i, et_vl, calculate_btn, vl));

            int vpId = getResources().getIdentifier("vp" + (i + 1), "id", getPackageName());
            EditText et_vp = findViewById(vpId);
            et_vp.addTextChangedListener(SubscribeEditText(i, et_vp, calculate_btn, vp));

            int shlId = getResources().getIdentifier("shl" + (i + 1), "id", getPackageName());
            EditText et_shl = findViewById(shlId);
            et_shl.addTextChangedListener(SubscribeEditText(i, et_shl, calculate_btn, shl));

            int shpId = getResources().getIdentifier("shp" + (i + 1), "id", getPackageName());
            EditText et_shp = findViewById(shpId);
            et_shp.addTextChangedListener(SubscribeEditText(i, et_shp, calculate_btn, shp));
        }

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean success = Calculate();
                if(success) SetButtonSuccess(calculate_btn);
                else SetButtonFail(calculate_btn);
            }
        });
    }

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

    private void SetButtonSuccess(Button btn) {
     btn.setText("РАССЧИТАНО");
     int color = getResources().getColor(R.color.green);
     btn.setBackgroundColor(color);
    }

    private void SetButtonFail(Button btn) {
     btn.setText("ОШИБКА");
     int color = getResources().getColor(R.color.red);
     btn.setBackgroundColor(color);
    }

    private void SetButtonIdle(Button btn) {
     btn.setText("РАСЧИТАТЬ");
     btn.setBackgroundColor(getResources().getColor(R.color.bc));
    }

    private SecondActivity.TextChangedListener<EditText> SubscribeEditText(int index, EditText editText, Button btn, float[] array) {
     return new SecondActivity.TextChangedListener<EditText>(editText)
     {
         public void onTextChanged(EditText target, Editable s)
         {
             if(btn != null) SetButtonIdle(btn);
             float value = 0;
             if(MainActivity.IsValueValid(s)) value = Float.valueOf(s.toString());
             array[index] = value;
         }
     };
    }

    private SecondActivity.TextChangedListener<EditText> SubscribeEditText(int index, EditText editText, Button btn, String[] array) {
     return new SecondActivity.TextChangedListener<EditText>(editText)
     {
         public void onTextChanged(EditText target, Editable s)
         {
             if(btn != null) SetButtonIdle(btn);
             array[index] = s.toString();
         }
     };
    }

    private boolean Calculate()
    {
        for(int i = 0; i < count; i++)
        {
            srv[i] = (shl[i] + shp[i]) / 2f;
        }

        dL1 = srv[8] + srv[9];
        dL2 = srv[10] + srv[11];
        dL3 = srv[12] + srv[13];
        dL4 = srv[14] + srv[15];
        dL5 = srv[16] + srv[17];

        d1 = srv[1] - ((srv[0] + srv[3]) / 2f) - Float.valueOf(vl[1]);
        d2 = srv[2] - ((srv[0] + srv[3]) / 2f) - Float.valueOf(vl[2]);

        boolean success1 = false;
        float d1_abs = Math.abs(d1);
        float d2_abs = Math.abs(d2);
        boolean d1_success = d1_abs >= 0.15f;
        boolean d2_success = d2_abs >= 0.15f;
        success1 = d1_success && d2_success;


        d3 = srv[5] - ((srv[4] + srv[7]) / 2f) - Float.valueOf(vl[1]);
        d4 = srv[6] - ((srv[4] + srv[7]) / 2f) - Float.valueOf(vl[2]);

        boolean success2 = false;
        float d3_abs = Math.abs(d3);
        float d4_abs = Math.abs(d4);
        boolean d3_success = d3_abs >= 0.75f;
        boolean d4_success = d4_abs >= 0.75f;
        success2 = d3_success && d4_success;

        return success1 && success2;
    }
}