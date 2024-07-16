package com.example.slicecalculatorbmi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class CalculatorBMIActivity extends AppCompatActivity {
    private EditText tinggi;
    private EditText bobot;
    private EditText umur;
    private RadioGroup rgGender;
    private RadioButton rdGender, selectedRadioButton;

    int tampilBerat;
    int tampilTinggi;
    int tampilUmur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator_bmiactivity);

        tinggi= (EditText)findViewById(R.id.inputTinggi);
        bobot = (EditText)findViewById(R.id.inputBerat);
        umur = (EditText)findViewById(R.id.inputUmur);
        rgGender = (RadioGroup)findViewById(R.id.pilihGender);
        Integer pilihGender = rgGender.getCheckedRadioButtonId();
        rdGender=(RadioButton)findViewById(pilihGender);

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    selectedRadioButton = findViewById(checkedId);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void krgBerat (View view){
        tampilBerat = tampilBerat - 1;
        displayBerat(tampilBerat);
    }
    public void tmbBerat (View view){
        tampilBerat = tampilBerat + 1;
        displayBerat(tampilBerat);
    }

    public void krgTinggi (View view){
        tampilTinggi = tampilTinggi - 1;
        displayTinggi(tampilTinggi);
    }
    public void tmbTinggi (View view){
        tampilTinggi = tampilTinggi + 1;
        displayTinggi(tampilTinggi);
    }

    public void krgUmur(View view) {
        tampilUmur = tampilUmur - 1;
        displayUmur(tampilUmur);
    }

    public void tmbUmur(View view) {
        tampilUmur = tampilUmur + 1;
        displayUmur(tampilUmur);
    }

    public void displayBerat(int number) {
        EditText inputBerat = (EditText) findViewById(R.id.inputBerat);
        inputBerat.setText("" + number);
    }

    public void displayTinggi(int number) {
        EditText inputTinggi = (EditText) findViewById(R.id.inputTinggi);
        inputTinggi.setText("" + number);
    }

    public void displayUmur(int number) {
        EditText inputUmur = (EditText) findViewById(R.id.inputUmur);
        inputUmur.setText("" + number);
    }

    public void calculateBMI(View view) {
        String tinggiStr = tinggi.getText().toString();
        String bobotStr =  bobot.getText().toString();

        if (tinggiStr != null && !"".equals(tinggiStr) && bobotStr != null && !"".equals(bobotStr)) {
            float tinggiValue = Float.parseFloat(tinggiStr) / 100;
            float bobotValue = Float.parseFloat(bobotStr);

            float bmi = 0;
            if (tinggiValue != 0) {
                bmi = bobotValue / (tinggiValue * tinggiValue);
            }

            displayBMI(bmi);
        }
    }

    public void displayBMI(float bmi) {
        String bmiLabel = "";
        String infoUmur = umur.getText().toString();

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedBMI = df.format(bmi);

        String jenisKelamin = "";
        if (selectedRadioButton != null) {
            jenisKelamin = selectedRadioButton.getText().toString();
        } else {
            jenisKelamin = "Female";
        }

        if (Float.compare(bmi, 15f) <= 0) {
            bmiLabel = getString(R.string.terlalu_sangat_kurus);
        } else if (Float.compare(bmi, 15f) > 0 && Float.compare(bmi, 16f) <= 0) {
            bmiLabel = getString(R.string.sangat_kurus);
        } else if (Float.compare(bmi, 16f) > 0 && Float.compare(bmi, 18.5f) <= 0) {
            bmiLabel = getString(R.string.kurus);
        } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0) {
            bmiLabel = getString(R.string.normal);
        } else if (Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0) {
            bmiLabel = getString(R.string.gemuk);
        } else if (Float.compare(bmi, 30f) > 0 && Float.compare(bmi, 35f) <= 0) {
            bmiLabel = getString(R.string.cukup_gemuk);
        } else if (Float.compare(bmi, 35f) > 0 && Float.compare(bmi, 40f) <= 0) {
            bmiLabel = getString(R.string.sangat_gemuk);
        } else {
            bmiLabel = getString(R.string.terlalu_sangat_gemuk);
        }

        bmiLabel = "Jenis kelamin: " +  jenisKelamin  + "\n\n" + "Hasil Penghitungan BMI : " + formattedBMI + " --- " +  "Kategori: " + "(" + bmiLabel + ")" + "\n\n"     + "Umur : " + infoUmur;

        AlertDialog.Builder tampilBMI = new AlertDialog.Builder(this);

        tampilBMI.setTitle("Hasil Penghitunan BMI");

        tampilBMI.setMessage(bmiLabel).setNeutralButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = tampilBMI.create();
        alertDialog.show();
    }
}