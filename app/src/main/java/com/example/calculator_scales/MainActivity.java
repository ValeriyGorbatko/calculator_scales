package com.example.calculator_scales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Range;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Dictionary;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static class AccuracyRange
    {
        public float Factor;
        public float Min;
        public float Max;

        public AccuracyRange(float _Factor, float _Min, float _Max)
        {
            Factor = _Factor;
            Min = _Min;
            Max = _Max;
        }
    }

    public static class AccuracyData
    {
        public AccuracyRange[] Range;

        public AccuracyData(AccuracyRange[] _Range)
        {
            Range = _Range;
        }
    }

    static AccuracyData[] accuracyData = new AccuracyData[]
        {
                new AccuracyData(new AccuracyRange[]
                        {
                                new AccuracyRange(1f,0f, 50f),
                                new AccuracyRange(2f,50f, 200f),
                                new AccuracyRange(3f,200f, Float.MAX_VALUE),
                        }),
                new AccuracyData(new AccuracyRange[]
                        {
                                new AccuracyRange(1f,0f, 5f),
                                new AccuracyRange(2f,5f, 20f),
                                new AccuracyRange(3f,20f, 100f),
                        }),
                new AccuracyData(new AccuracyRange[]
                        {
                                new AccuracyRange(1f,0f, 0.5f),
                                new AccuracyRange(2f,0.5f, 2f),
                                new AccuracyRange(3f,2f, 10f),
                        }),
                new AccuracyData(new AccuracyRange[]
                        {
                                new AccuracyRange(1f,0f, 0.05f),
                                new AccuracyRange(2f,0.05f, 0.2f),
                                new AccuracyRange(3f,0.2f, 1f),
                        }),
        };

    static String min_nav_key = "min_nav_key";
    static String max_nav_key = "max_nav_key";
    static String scale_division_key = "scale_division_key";
    static String divisions_valid_key = "divisions_valid_key";
    static String accuracy_key = "accuracy_key";

    EditText in_min_nav;
    EditText in_max_nav;
    EditText in_scale_division;
    EditText in_divisions_valid;
    Spinner in_accuracy;

    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        in_min_nav = findViewById(R.id.min_nav);
        in_max_nav = findViewById(R.id.max_nav);
        in_scale_division = findViewById(R.id.scale_division);
        in_divisions_valid = findViewById(R.id.divisions_valid);
        in_accuracy = findViewById(R.id.accuracy_class);

        InitFields();
    }

    @Override
    public void onClick(View view)
    {
        if(SaveFields())
        {
            Intent intent = null;
            switch (view.getId())
            {
                case R.id.rivnoplechiv:
                    intent = new Intent(this, SecondActivity.class);
                    break;
                case R.id.platphormv:
                    intent = new Intent(this, ThirdActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    private void InitFields()
    {
        in_accuracy.setSelection(sharedPreferences.getInt(accuracy_key, 0));

        in_min_nav.setText(String.valueOf(sharedPreferences.getFloat(min_nav_key, 0)));
        in_max_nav.setText(String.valueOf(sharedPreferences.getFloat(max_nav_key, 0)));

        in_scale_division.setText(String.valueOf(sharedPreferences.getFloat(scale_division_key, 0)));
        in_divisions_valid.setText(String.valueOf(sharedPreferences.getFloat(divisions_valid_key, 0)));
    }

    private boolean SaveFields()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(accuracy_key, in_accuracy.getSelectedItemPosition());

        if(IsValueValid(in_min_nav.getText()))
            editor.putFloat(min_nav_key, Float.valueOf(in_min_nav.getText().toString()));
        else return false;
        if(IsValueValid(in_max_nav.getText()))
            editor.putFloat(max_nav_key, Float.valueOf(in_max_nav.getText().toString()));
        else return false;
        if(IsValueValid(in_scale_division.getText()))
            editor.putFloat(scale_division_key, Float.valueOf(in_scale_division.getText().toString()));
        else return false;
        if(IsValueValid(in_divisions_valid.getText()))
            editor.putFloat(divisions_valid_key, Float.valueOf(in_divisions_valid.getText().toString()));
        else return false;

        editor.commit();
        return true;
    }

    public static boolean IsValueValid(Editable editable)
    {
        if(editable.toString().isEmpty() ||
                editable.toString().equals("-") ||
                editable.toString().equals("+") ||
                editable.toString().equals("=") ||
                editable.toString().equals(".") ||
                editable.toString().equals(",")) return false;
        return true;
    }

    public static float GetMinNavValue()
    {
        return sharedPreferences.getFloat(min_nav_key, 0);
    }

    public static float GetMaxNavValue()
    {
        return sharedPreferences.getFloat(max_nav_key, 0);
    }

    public static float GetScaleDivisionValue()
    {
        return sharedPreferences.getFloat(scale_division_key, 0);
    }

    public static float GetDivisionsValidValue()
    {
        return sharedPreferences.getFloat(divisions_valid_key, 0);
    }

    public static int GetAccuracyIndex()
    {
        return sharedPreferences.getInt(accuracy_key, 0);
    }
}