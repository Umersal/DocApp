package com.example.docapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.r0adkll.slidr.Slidr;

import java.util.Calendar;

public class activity_view extends AppCompatActivity {
    EditText ed1,ed2;
    TextView tv1;
    Button b1,b3,b2;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        b1 = (Button) findViewById(R.id.searchbtn);
        b2 = (Button) findViewById(R.id.srchbydate);
        b3 = (Button) findViewById(R.id.delbtn);

        ed1 = (EditText) findViewById(R.id.nameedittext);
        ed2 = (EditText) findViewById(R.id.ageedittext);
        tv1 = (TextView) findViewById(R.id.daterecordtext);
        Slidr.attach(this);

        //date
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        activity_view.this,
                        android.R.style.Theme_Black,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                tv1.setText(date);
            }
        };





        //delete button
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(ed1.getText().toString().trim().length() == 0 || ed2.getText().toString().trim().length() == 0){
                Snackbar.make(v,"Please Enter Name and Age to Delete",Snackbar.LENGTH_LONG).show();
            }
            else{
                String n1 = ed1.getText().toString();
                String n2 = ed2.getText().toString();
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
                simpledb.execSQL("delete from patient_table where name='"+n1+"' and age='"+n2+"'");
                ed1.getText().clear();
                ed2.getText().clear();
                Snackbar.make(v,"Record Deleted",Snackbar.LENGTH_LONG).show();
            }
            }
        });


        //search button
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().trim().length() == 0){
                    Snackbar.make(v,"Please Enter Name",Snackbar.LENGTH_LONG).show();
                }
                else{
                String n = ed1.getText().toString();
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
                Cursor c = simpledb.rawQuery("select * from patient_table where name='"+n+"'", null);
                if(c.getCount() == 0){
                    Snackbar.make(v,"No Record Found",Snackbar.LENGTH_LONG).show();
                    return ;
                }
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("Date: "+c.getString(1)+"\n");  //need to change col id since 0 is for id in my db
                    buffer.append("Name: "+c.getString(2)+"\n");
                    buffer.append("Age: "+c.getString(3)+"\n");
                    buffer.append("BP: "+c.getString(4)+" mmHg"+"\n");
                    buffer.append("Sugar: "+c.getString(5)+" mg/dl"+"\n");
                    buffer.append("Temp: "+c.getString(6)+" °F"+"\n");
                    buffer.append("KCO: "+c.getString(7)+"\n");
                    buffer.append("Complaints: "+c.getString(8)+"\n");
                    buffer.append("Medicine: "+c.getString(9)+"\n");
                    buffer.append("*********\n");

                }
                showMessage("PATIENT DETAILS", buffer);
                ed1.getText().clear();
                ed2.getText().clear();
                Snackbar.make(v,"Record Found",Snackbar.LENGTH_LONG).show();
            }
            }
        });

        //search by date button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv1.getText().toString().trim().length() == 0)
                    Snackbar.make(v,"Please Enter Date",Snackbar.LENGTH_LONG).show();
                else{
                    String n = tv1.getText().toString();
                    SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
                    Cursor c = simpledb.rawQuery("select * from patient_table where date='"+n+"'", null);
                    if(c.getCount() == 0){
                        Snackbar.make(v,"No Record Found",Snackbar.LENGTH_LONG).show();
                        return ;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while(c.moveToNext()){
                        buffer.append("Date: "+c.getString(1)+"\n");  //need to change col id since 0 is for id in my db
                        buffer.append("Name: "+c.getString(2)+"\n");
                        buffer.append("Age: "+c.getString(3)+"\n");
                        buffer.append("BP: "+c.getString(4)+" mmHg"+"\n");
                        buffer.append("Sugar: "+c.getString(5)+" mg/dl"+"\n");
                        buffer.append("Temp: "+c.getString(6)+" °F"+"\n");
                        buffer.append("KCO: "+c.getString(7)+"\n");
                        buffer.append("Complaints: "+c.getString(8)+"\n");
                        buffer.append("Medicine: "+c.getString(9)+"\n");
                        buffer.append("----------------------\n");

                    }
                    showMessage("PATIENT DETAILS", buffer);
                    Snackbar.make(v,"Record Found",Snackbar.LENGTH_LONG).show();

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
