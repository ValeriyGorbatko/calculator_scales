package com.example.calculator_scales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    String min_nav_key = "min_nav_key";
    String max_nav_key = "max_nav_key";
    String scale_division_key = "scale_division_key";
    String divisions_valid_key = "divisions_valid_key";

    EditText in_min_nav;
    EditText in_max_nav;
    EditText in_scale_division;
    EditText in_divisions_valid;

    SharedPreferences sharedPreferences;

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
        in_min_nav.setText(String.valueOf(sharedPreferences.getFloat(min_nav_key, 0)));
        in_max_nav.setText(String.valueOf(sharedPreferences.getFloat(max_nav_key, 0)));

        in_scale_division.setText(String.valueOf(sharedPreferences.getFloat(scale_division_key, 0)));
        in_divisions_valid.setText(String.valueOf(sharedPreferences.getFloat(divisions_valid_key, 0)));
    }

    private boolean SaveFields()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();

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

    private boolean IsValueValid(Editable editable)
    {
        if(editable.toString().isEmpty() ||
                editable.toString().equals("-") ||
                editable.toString().equals("+") ||
                editable.toString().equals("=") ||
                editable.toString().equals(".") ||
                editable.toString().equals(",")) return false;
        return true;
    }

}