package com.example.calculator_scales;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//РІВНОПЛЕЧІ ВАГИ
public class SecondActivity extends AppCompatActivity
 {
     private int count = 18;

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

     private float dP0;
     private float dl;

     private float etal_ap;
     private float etal_al;

     Button calculate_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        calculate_btn = findViewById(R.id.calculate_btn);

        SetButtonIdle(calculate_btn);

        for (int i = 0; i < count; i++)
        {
            int shlId = getResources().getIdentifier("shl" + (i + 1), "id", getPackageName());
            EditText et_shl = findViewById(shlId);
            et_shl.addTextChangedListener(SubscribeEditText(i, et_shl, calculate_btn, shl));

            int shpId = getResources().getIdentifier("shp" + (i + 1), "id", getPackageName());
            EditText et_shp = findViewById(shpId);
            et_shp.addTextChangedListener(SubscribeEditText(i, et_shp, calculate_btn, shp));
        }

        EditText et_etal_ap = findViewById(R.id.etal_ap);
        et_etal_ap.addTextChangedListener(new SecondActivity.TextChangedListener<EditText>(et_etal_ap) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(calculate_btn);
                if(!MainActivity.IsValueValid(et_etal_ap.getText())) etal_ap = 0;
                else etal_ap = Math.abs(Float.valueOf(et_etal_ap.getText().toString()));
            }
        });

        EditText et_etal_al = findViewById(R.id.etal_al);
        et_etal_al.addTextChangedListener(new SecondActivity.TextChangedListener<EditText>(et_etal_al) {
            @Override
            public void onTextChanged(EditText target, Editable s)
            {
                SetButtonIdle(calculate_btn);
                if(!MainActivity.IsValueValid(et_etal_al.getText())) etal_al = 0;
                else etal_al = Math.abs(Float.valueOf(et_etal_al.getText().toString()));
            }
        });

        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean success = Calculate();
                if(success) SetButtonSuccess(calculate_btn);
                else SetButtonFail(calculate_btn);

                SaveToFile();
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
     btn.setText("ПРИДАТНИЙ");
     int color = getResources().getColor(R.color.green);
     btn.setBackgroundColor(color);
    }

    private void SetButtonFail(Button btn) {
     btn.setText("ПОМИЛКА");
     int color = getResources().getColor(R.color.red);
     btn.setBackgroundColor(color);
    }

    private void SetButtonIdle(Button btn) {
     btn.setText("ВИСНОВОК");
     btn.setBackgroundColor(getResources().getColor(R.color.bc));
    }

     private class Pair {
         public float Min;
         public float Max;
     }

     private SecondActivity.Pair GetMinMax(float[] array) {
         SecondActivity.Pair pair = new SecondActivity.Pair();
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

        d1 = srv[1] - ((srv[0] + srv[3]) / 2f) - Float.valueOf(shl[1]);
        d2 = srv[2] - ((srv[0] + srv[3]) / 2f) - Float.valueOf(shl[2]);

        boolean success1 = false;
        float d1_abs = Math.abs(d1);
        float d2_abs = Math.abs(d2);
        boolean d1_success = d1_abs >= 0.15f;
        boolean d2_success = d2_abs >= 0.15f;
        success1 = d1_success && d2_success;


        d3 = srv[5] - ((srv[4] + srv[7]) / 2f) - Float.valueOf(shl[1]);
        d4 = srv[6] - ((srv[4] + srv[7]) / 2f) - Float.valueOf(shl[2]);

        boolean success2 = false;
        float d3_abs = Math.abs(d3);
        float d4_abs = Math.abs(d4);
        boolean d3_success = d3_abs >= 0.75f;
        boolean d4_success = d4_abs >= 0.75f;
        success2 = d3_success && d4_success;

        float biggest_a = 0;
        if(etal_al > etal_ap) biggest_a = etal_al;
        else if(etal_ap > etal_al) biggest_a = etal_ap;

        dl = biggest_a > 0 ? Math.abs((biggest_a / 2) - (1/2) * ((srv[7] + srv[8]) - (srv[3] + srv[9]))) : 0;

        float[] tmp = new float[] { srv[9], srv[11], srv[13], srv[15], srv[17] };
        Pair pair = GetMinMax(tmp);
        dP0 = pair.Max - pair.Min;

        return success1 && success2;
    }

     private void SaveToFile() {
         String ten = "#,###"; //0.000
         DecimalFormat decimalFormatter = new DecimalFormat(ten);

         SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
         Date now = new Date(System.currentTimeMillis());

         //Берём текущий номер протокола
         int protocol_number = MainActivity.GetProtocolNumber2();

         //Объявляем название будущего файла с текущим номером протокола
         String new_protocol_filename = "Протокол №" + protocol_number + " " + dateFormatter.format(now) + ".xml";

         //Название папки с протоколами
         String protocol_folders_name = "Протоколы ваги рівноплечі";

         //Пытаемся считать нужный нам шаблон. (160х160, 250х250 и т.д.)
         try (InputStream input = getResources().openRawResource(getResources().getIdentifier("second","raw", getPackageName())))
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
             content = content.replace("keyVlasnik", MainActivity.GetOwner());
             content = content.replace("keyMax", String.valueOf(MainActivity.GetMaxNavValue()));
             content = content.replace("keyClass", getResources().getStringArray(R.array.accuracy)[MainActivity.GetAccuracyIndex()]);
             content = content.replace("keyMin", String.valueOf(MainActivity.GetMinNavValue()));

             key = "keyTypePovir";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, getResources().getStringArray(R.array.type_ver)[MainActivity.GetTypeVerificationIndex()]);
             content = content.replace(origin, newVal);

             content = content.replace("keyvikPovir", MainActivity.GetVikPovir());


            for (int i = 0; i < count; i++)
            {
                key = "keyLt" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(shl[i]));
                content = content.replace(origin, newVal);

                key = "keyRt" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(shp[i]));
                content = content.replace(origin, newVal);

                key = "keyRiv" + (i + 1);
                origin = "<w:t>" + key + "</w:t>";
                newVal = origin.replace(key, String.valueOf(srv[i]));
                content = content.replace(origin, newVal);
            }

             key = "keyDif0";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(dL1));
             content = content.replace(origin, newVal);

             key = "keyDif1";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(dL2));
             content = content.replace(origin, newVal);

             key = "keyDif2";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(dL3));
             content = content.replace(origin, newVal);

             key = "keyDif3";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(dL4));
             content = content.replace(origin, newVal);

             key = "keyDif4";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(dL5));
             content = content.replace(origin, newVal);

             key = "keyRo";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(shl[1]));
             content = content.replace(origin, newVal);

             key = "keyRtw";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(shl[2]));
             content = content.replace(origin, newVal);

             key = "keyBn1";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(d1));
             content = content.replace(origin, newVal);

             key = "keyBn2";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(d2));
             content = content.replace(origin, newVal);

             key = "keyZn1";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(d3));
             content = content.replace(origin, newVal);

             key = "keyZn2";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(d4));
             content = content.replace(origin, newVal);

             key = "keyRp";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(dl));
             content = content.replace(origin, newVal);


             String keyPLValue = "";
             if(etal_al > etal_ap) keyPLValue = "Ліва";
             else if(etal_ap > etal_al) keyPLValue = "Права";

             key = "keyPl";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, keyPLValue);
             content = content.replace(origin, newVal);

             key = "keyNen";
             origin = "<w:t>" + key + "</w:t>";
             newVal = origin.replace(key, String.valueOf(dP0));
             content = content.replace(origin, newVal);

             boolean success = true;
             content = content.replace("totalSuccess", success ? "Придатний" : "Не придатний");

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
         MainActivity.IncreaseProtocolNumber2();
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