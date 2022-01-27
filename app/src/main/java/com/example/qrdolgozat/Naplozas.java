package com.example.qrdolgozat;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Naplozas {

    public static void kiir(String szoveg) throws IOException {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formazottDatum = dateFormat.format(date);

        String sor= String.format("%s %s", szoveg, formazottDatum);

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory(), "scannedCodes.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(sor);
            writer.append(System.lineSeparator());
            writer.close();
        }
    }
}
