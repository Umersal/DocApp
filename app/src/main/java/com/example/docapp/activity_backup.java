package com.example.docapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.r0adkll.slidr.Slidr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class activity_backup extends AppCompatActivity {

    Context context;

    Button b1,b2,b3,b4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        b1 = (Button) findViewById(R.id.backuppatient);
        b2 = (Button) findViewById(R.id.backupmedicine);
        b3 = (Button) findViewById(R.id.backupbalance);
        b4 = (Button) findViewById(R.id.backupunanimed);
        Slidr.attach(this);



        ActivityCompat.requestPermissions(this,new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPdfForPatientDetails();
                    Snackbar.make(v,"Backup of Patient Details COMPLETED",Snackbar.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Snackbar.make(v,"File Not Found",Snackbar.LENGTH_LONG).show();
                } catch (DocumentException e) {
                    e.printStackTrace();
                    Snackbar.make(v,"Document Problem",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPDFForMedicineStocks();
                    Snackbar.make(v,"Backup of Allopathy Medicine COMPLETED",Snackbar.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Snackbar.make(v,"File Not Found",Snackbar.LENGTH_LONG).show();
                } catch (DocumentException e) {
                    e.printStackTrace();
                    Snackbar.make(v,"Document Problem",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPDFForBalanceDetails();
                    Snackbar.make(v,"Backup of Balance History COMPLETED",Snackbar.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Snackbar.make(v,"File Not Found",Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (DocumentException e) {
                    Snackbar.make(v,"Document Problem",Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPDFForUnaniMedicineStocks();
                    Snackbar.make(v,"Backup of Unani Medicine Stock COMPLETED",Snackbar.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Snackbar.make(v,"File Not Found",Snackbar.LENGTH_LONG).show();
                } catch (DocumentException e) {
                    e.printStackTrace();
                    Snackbar.make(v,"Document Problem",Snackbar.LENGTH_LONG).show();
                }
            }
        });




    }

    //PDF table creation for patient history
    public void createPdfForPatientDetails() throws FileNotFoundException, DocumentException {
        File directory, filename;

        //if there is no SD card, create new directory objects to make directory on device
        if (Environment.getExternalStorageState() == null) {
            //create new file directory object
            directory = new File(Environment.getDataDirectory()
                    + "/smshifaclinic/");
            filename = new File(Environment.getDataDirectory() + "/smshifaclinic/PatientDetails.pdf");
            if (filename.exists()) {
                File[] dirFiles = filename.listFiles();
                if (dirFiles.length != 0) {
                    for (int ii = 0; ii <= dirFiles.length; ii++) {
                        dirFiles[ii].delete();
                    }
                }
            }
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, "PatientDetails.pdf");
            SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
            Cursor c = simpledb.rawQuery("select * from patient_table", null);
            Document document = new Document(); // create the document
            PdfWriter.getInstance(document, new FileOutputStream(file));
            PdfWriter writer =  PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            document.open();
            document.add(new Paragraph("Patient Details"));
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100f); //width of the table
            //spacing before and after the table
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);


            table.addCell("Date");
            table.addCell("Name");
            table.addCell("Age");
            table.addCell("BP");
            table.addCell("Sugar");
            table.addCell("Temp");
            table.addCell("Complaints");
            table.addCell("KCO");
            table.addCell("Medicine");

            while (c.moveToNext()) {
                String date = c.getString(1);
                String name = c.getString(2);
                String age = c.getString(3);
                String bp = c.getString(4);
                String sugar = c.getString(5);
                String temperature = c.getString(6);
                String kco = c.getString(7);
                String complaints = c.getString(8);
                String medicine = c.getString(9);

                table.addCell(date);
                table.addCell(name);
                table.addCell(age);
                table.addCell(bp);
                table.addCell(sugar);
                table.addCell(temperature);
                table.addCell(kco);
                table.addCell(complaints);
                table.addCell(medicine);

            }

            document.add(table);
            document.addCreationDate();
            c.close();
            document.close();
        } else if (Environment.getExternalStorageState() != null) {
            // search for directory on SD card
            directory = new File(Environment.getExternalStorageDirectory()
                    + "/smshifaclinic/");
            // if no directory exists, create new directory to store test
            // results
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, "PatientDetails.pdf");
            SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
            Cursor c = simpledb.rawQuery("select * from patient_table", null);
            Document document = new Document(); // create the document
            PdfWriter writer =  PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            document.open();
            document.add(new Paragraph("Patient Details"));
            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100f); //width of the table
            //spacing before and after the table
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);


            table.addCell("Date");
            table.addCell("Name");
            table.addCell("Age");
            table.addCell("BP");
            table.addCell("Sugar");
            table.addCell("Temp");
            table.addCell("Complaints");
            table.addCell("KCO");
            table.addCell("Medicine");

            while (c.moveToNext()) {
                String date = c.getString(1);
                String name = c.getString(2);
                String age = c.getString(3);
                String bp = c.getString(4);
                String sugar = c.getString(5);
                String temperature = c.getString(6);
                String kco = c.getString(7);
                String complaints = c.getString(8);
                String medicine = c.getString(9);

                table.addCell(date);
                table.addCell(name);
                table.addCell(age);
                table.addCell(bp);
                table.addCell(sugar);
                table.addCell(temperature);
                table.addCell(kco);
                table.addCell(complaints);
                table.addCell(medicine);

            }

            document.add(table);
            document.addCreationDate();
            c.close();
            document.close();
        }
    }


    //PDF table creation for Medicine Stocks
    public void createPDFForMedicineStocks() throws FileNotFoundException, DocumentException {
        File directory, filename;

        //if there is no SD card, create new directory objects to make directory on device
        if (Environment.getExternalStorageState() == null) {
            //create new file directory object
            directory = new File(Environment.getDataDirectory()
                    + "/smshifaclinic/");
            filename = new File(Environment.getDataDirectory() + "/smshifaclinic/AllopathyMedicineStocks.pdf");
            if (filename.exists()) {
                File[] dirFiles = filename.listFiles();
                if (dirFiles.length != 0) {
                    for (int ii = 0; ii <= dirFiles.length; ii++) {
                        dirFiles[ii].delete();
                    }
                }
            }
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, "AllopathyMedicineStocks.pdf");
            SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
            Cursor c = simpledb.rawQuery("select * from medicine_table", null);
            Document document = new Document(); // create the document
            PdfWriter writer =  PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            document.open();
            document.add(new Paragraph("Allopathy Medicine Stocks"));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100f); //width of the table
            //spacing before and after the table
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);

            table.addCell("Allopathy MEDICINE NAME");
            table.addCell("QUANTITY");

            while (c.moveToNext()) {
                String name = c.getString(0);
                String qty = c.getString(1);

                table.addCell(name);
                table.addCell(qty);
            }
            document.add(table);
            document.addCreationDate();
            c.close();
            document.close();
        } else if (Environment.getExternalStorageState() != null) {
            // search for directory on SD card
            directory = new File(Environment.getExternalStorageDirectory()
                    + "/smshifaclinic/");
            // if no directory exists, create new directory to store test
            // results
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, "AllopathyMedicineStocks.pdf");
            SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
            Cursor c = simpledb.rawQuery("select * from medicine_table", null);
            Document document = new Document(); // create the document
            PdfWriter writer =  PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            document.open();
            document.add(new Paragraph("Allopathy Medicine Stocks"));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100f); //width of the table
            //spacing before and after the table
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);

            table.addCell("ALLOPATHY MEDICINE NAME");
            table.addCell("QUANTITY");

            while (c.moveToNext()) {
                String name = c.getString(0);
                String qty = c.getString(1);

                table.addCell(name);
                table.addCell(qty);
            }
            document.add(table);
            document.addCreationDate();
            c.close();
            document.close();
        }
    }

    public void createPDFForBalanceDetails() throws FileNotFoundException, DocumentException{
        File directory, filename;

        //if there is no SD card, create new directory objects to make directory on device
        if (Environment.getExternalStorageState() == null) {
            //create new file directory object
            directory = new File(Environment.getDataDirectory()
                    + "/smshifaclinic/");
            filename = new File(Environment.getDataDirectory()+"/smshifaclinic/BalanceHistory.pdf");
            if (filename.exists()) {
                File[] dirFiles = filename.listFiles();
                if (dirFiles.length != 0) {
                    for (int ii = 0; ii <= dirFiles.length; ii++) {
                        dirFiles[ii].delete();
                    }
                }
            }
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, "BalanceHistory.pdf");
            SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
            Cursor c = simpledb.rawQuery("select * from balance_history", null);
            Document document = new Document(); // create the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            document.open();
            document.add(new Paragraph("Balance Details"));
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100f); //width of the table
            //spacing before and after the table
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);


            table.addCell("Date");
            table.addCell("Name");
            table.addCell("Age");
            table.addCell("Balance");

            while (c.moveToNext()) {
                String date = c.getString(0);
                String name = c.getString(1);
                String age = c.getString(3);
                String bal = c.getString(2);


                table.addCell(date);
                table.addCell(name);
                table.addCell(age);
                table.addCell(bal);
            }

            document.add(table);
            document.addCreationDate();
            c.close();
            document.close();

            // if phone DOES have sd card
        } else if (Environment.getExternalStorageState() != null) {
            // search for directory on SD card
            directory = new File(Environment.getExternalStorageDirectory()
                    + "/smshifaclinic/");
            // if no directory exists, create new directory to store test
            // results
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(directory, "BalanceHistory.pdf");
            SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
            Cursor c = simpledb.rawQuery("select * from balance_history", null);
            Document document = new Document(); // create the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            document.open();
            document.add(new Paragraph("Balance Details"));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100f); //width of the table
            //spacing before and after the table
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);


            table.addCell("Date");
            table.addCell("Name");
            table.addCell("Age");
            table.addCell("Balance");

            while (c.moveToNext()) {
                String date = c.getString(0);
                String name = c.getString(1);
                String age = c.getString(3);
                String bal = c.getString(2);


                table.addCell(date);
                table.addCell(name);
                table.addCell(age);
                table.addCell(bal);
            }

            document.add(table);
            document.addCreationDate();
            c.close();
            document.close();
        }// end of SD card checking
    }

    public void createPDFForUnaniMedicineStocks() throws FileNotFoundException, DocumentException {
        File directory, filename;

        //if there is no SD card, create new directory objects to make directory on device
        if (Environment.getExternalStorageState() == null) {
            //create new file directory object
            directory = new File(Environment.getDataDirectory()
                    + "/smshifaclinic/");
            filename = new File(Environment.getDataDirectory() + "/smshifaclinic/UnaniMedicineStocks.pdf");
            if (filename.exists()) {
                File[] dirFiles = filename.listFiles();
                if (dirFiles.length != 0) {
                    for (int ii = 0; ii <= dirFiles.length; ii++) {
                        dirFiles[ii].delete();
                    }
                }
            }
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, "UnaniMedicineStocks.pdf");
            SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
            Cursor c = simpledb.rawQuery("select * from unani_medicine", null);
            Document document = new Document(); // create the document
            PdfWriter writer =  PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            document.open();
            document.add(new Paragraph("Unani Medicine Stocks"));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100f); //width of the table
            //spacing before and after the table
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);

            table.addCell("UNANI MEDICINE NAME");
            table.addCell("QUANTITY");

            while (c.moveToNext()) {
                String name = c.getString(0);
                String qty = c.getString(1);

                table.addCell(name);
                table.addCell(qty);
            }
            document.add(table);
            document.addCreationDate();
            c.close();
            document.close();

        } else if (Environment.getExternalStorageState() != null) {
            // search for directory on SD card
            directory = new File(Environment.getExternalStorageDirectory()
                    + "/smshifaclinic/");
            // if no directory exists, create new directory to store test
            // results
            if (!directory.exists()) {
                directory.mkdir();
            }
            File file = new File(directory, "UnaniMedicineStocks.pdf");
            SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
            Cursor c = simpledb.rawQuery("select * from unani_medicine", null);
            Document document = new Document(); // create the document
            PdfWriter writer =  PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            document.open();
            document.add(new Paragraph("Unani Medicine Stocks"));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100f); //width of the table
            //spacing before and after the table
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);

            table.addCell("UNANI MEDICINE NAME");
            table.addCell("QUANTITY");

            while (c.moveToNext()) {
                String name = c.getString(0);
                String qty = c.getString(1);

                table.addCell(name);
                table.addCell(qty);
            }
            document.add(table);
            document.addCreationDate();
            c.close();
            document.close();
        }
    }



}




