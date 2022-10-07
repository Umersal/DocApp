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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.r0adkll.slidr.Slidr;

import java.util.Calendar;

public class activity_balance extends AppCompatActivity{

    DatabaseHelper mydb2;

    Button b1,b2,b3;
    EditText e1,e2,e3;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        mydb2 = new DatabaseHelper(this);

        b1 = (Button) findViewById(R.id.addbal);
        b2 = (Button) findViewById(R.id.delbyname);
        b3 = (Button) findViewById(R.id.viewbal);

        tv1 = (TextView) findViewById(R.id.etxt_fromdate);

        e1 = (EditText) findViewById(R.id.balnameedit);
        e2 = (EditText) findViewById(R.id.balanceedit);
        e3 = (EditText) findViewById(R.id.ageedit);
        Slidr.attach(this);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        activity_balance.this,
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

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().trim().length() == 0 || e2.getText().toString().trim().length() == 0 || e3.getText().toString().trim().length() == 0){
                    Snackbar.make(v,"Please Enter All Details",Snackbar.LENGTH_LONG).show();
                }
                else{
                boolean isInserted = mydb2.insertBalance(tv1.getText().toString(),e1.getText().toString(),e2.getText().toString(),e3.getText().toString());
                e1.getText().clear();
                e2.getText().clear();
                e3.getText().clear();
                if(isInserted)
                    Snackbar.make(v,"Record Added",Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(v,"Record Not Added",Snackbar.LENGTH_LONG).show();
            }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().trim().length() == 0 || e3.getText().toString().trim().length() == 0){
                    Toast.makeText(activity_balance.this, "Please enter name and age to delete", Toast.LENGTH_LONG).show();
                }else{
                String n1 = e1.getText().toString();
                String n2 = e3.getText().toString();
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
                simpledb.execSQL("delete from balance_history where name='"+n1+"' and age ='"+n2+"'");
                e1.getText().clear();
                e3.getText().clear();
                    Snackbar.make(v,"Record Deleted",Snackbar.LENGTH_LONG).show();
            }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase simpledb = getApplicationContext().openOrCreateDatabase("medicalrecord.db", Context.MODE_PRIVATE, null);
                Cursor c = simpledb.rawQuery("select * from balance_history", null);
                if(c.getCount() == 0){
                    Snackbar.make(v,"No Record Found",Snackbar.LENGTH_LONG).show();
                    return ;
                }
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("Date: "+c.getString(0)+"\n");
                    buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("Age: "+c.getString(3)+"\n");
                    buffer.append("BAL: "+c.getString(2)+"\n");
                    buffer.append("----------------------\n");
                }
                showMessage("BALANCE DETAILS", buffer);
                e1.getText().clear();
                e2.getText().clear();
                e3.getText().clear();
                Snackbar.make(v,"Record Found",Snackbar.LENGTH_LONG).show();
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
