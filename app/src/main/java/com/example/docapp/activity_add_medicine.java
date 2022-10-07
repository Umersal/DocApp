package com.example.docapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.r0adkll.slidr.Slidr;

public class activity_add_medicine extends AppCompatActivity {
    DatabaseHelper mydb1;

    Button b1,b2,b3,b4;
    EditText ed1,ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        mydb1 = new DatabaseHelper(this);

        b1 = (Button) findViewById(R.id.addmedicinestock);
        b2 = (Button) findViewById(R.id.viewmedicine);
        b3 = (Button) findViewById(R.id.updatemedicine);
        b4 = (Button) findViewById(R.id.delbutton);

        ed1 = (EditText) findViewById(R.id.medicinenameedit);
        ed2 = (EditText) findViewById(R.id.qtyedit);
        Slidr.attach(this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().trim().length() == 0 || ed2.getText().toString().trim().length() == 0){
                    Snackbar.make(v,"Please enter all details",Snackbar.LENGTH_LONG).show();
                    //Toast.makeText(activity_add_medicine.this,"Please enter all details",Toast.LENGTH_LONG).show();
                }
                else{
                boolean isInserted =  mydb1.insertData2(ed1.getText().toString(),ed2.getText().toString());

                ed1.getText().clear();
                ed2.getText().clear();
                if(isInserted)
                    Snackbar.make(v,"Medicine Details Added",Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v,"Medicine Details NOT Added",Snackbar.LENGTH_LONG).show();
            }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
                Cursor c = simpledb.rawQuery("select * from medicine_table", null);
                if(c.getCount() == 0){
                    Snackbar.make(v,"No Record Found",Snackbar.LENGTH_LONG).show();
                    return ;
                }
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("Name: "+c.getString(0)+"\n");
                    buffer.append("QTY: "+c.getString(1)+"\n");
                    buffer.append("----------------------\n");
                }
                showMessage("MEDICINE DETAILS", buffer);
                ed1.getText().clear();
                ed2.getText().clear();
                Snackbar.make(v,"Record Found",Snackbar.LENGTH_LONG).show();
                }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().trim().length() == 0 || ed2.getText().toString().trim().length() == 0){
                    Toast.makeText(activity_add_medicine.this,"Please enter all details",Toast.LENGTH_LONG).show();
                }
                else{

                boolean isUpdated = mydb1.updateData(ed1.getText().toString(), ed2.getText().toString());
                ed1.getText().clear();
                ed2.getText().clear();
                if(isUpdated == true)
                    Snackbar.make(v,"Medicine Details Updated",Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v,"Medicine Details Not Updated",Snackbar.LENGTH_LONG).show();
            }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().trim().length() == 0){
                    Snackbar.make(v,"Please Enter Name To Delete",Snackbar.LENGTH_LONG).show();
                }
                else{
                String n = ed1.getText().toString();
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
                simpledb.execSQL("delete from medicine_table where name='"+n+"'");
                ed1.getText().clear();
                    Snackbar.make(v,"Deleted",Snackbar.LENGTH_LONG).show();
            }
            }
        });


    }

    public void showMessage(String title, StringBuffer message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        //builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.setIcon(R.drawable.cliniccropped);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }



}






