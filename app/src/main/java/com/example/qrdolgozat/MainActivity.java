package com.example.qrdolgozat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private AppCompatTextView txtView_adatok;
    private AppCompatButton btn_scan,btn_kiir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.setPrompt("QR code olvasás");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.initiateScan();
            }
        });
        btn_kiir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bevitel = txtView_adatok.getText().toString();
                if (bevitel == ""){
                    Toast.makeText(MainActivity.this, "Nem lehet üres", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        Naplozas.kiir(bevitel);
                        Toast.makeText(MainActivity.this, "Sikeres fájlba írás", Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents()==null){
                Toast.makeText(this, "Kiléptél", Toast.LENGTH_SHORT).show();
            }else{
                txtView_adatok.setText(result.getContents());
                try {
                    Uri uri = Uri.parse(txtView_adatok.getText().toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }catch (Exception e){
                    Log.d("URL ERROR", e.toString());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void init(){
        txtView_adatok = findViewById(R.id.txtView_adatok);
        btn_scan = findViewById(R.id.btn_scan);
        btn_kiir = findViewById(R.id.btn_kiir);
    }
}