package com.example.calculator_scales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rivnoplechiv:
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                break;
            case R.id.platphormv:
                Intent intentsec = new Intent(this, ThirdActivity.class);
                startActivity(intentsec);
                break;
            default:
                break;
        }
    }

//    @Override
//    public void onClickPla(View view) {
//        switch (view.getId()){
//            case R.id.platphormv:
//                Intent intent = new Intent(this, ThirdActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//    }
}