package com.example.docapp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.r0adkll.slidr.Slidr;

import java.util.Calendar;

public class activity_add extends AppCompatActivity {

    private static final String[] medicine = new String[]{"ap(try)","ap(phen)","ap(ace)","cpm",
            "lcet(W)","cet","b19","d19","d10","ylw","met200","met400","dmr","rant150","cef200",
            "cef100","az250","mox625","h.shifa","mom","b3","ak","g10","nil","mvt","sol500",
            "p500","react100","relief","dyspcombo","bro","dom","bru","eczyme","ncp","spmoxcv",
            "splcet","spbru","sp125","sp250","spnflox","spcodo","spcoca","spambrols","spdeed",
            "p650","dicgel", "nivabion","rab-d", "combo19", "IMR", "AMR", "PAN-D", "NO COLD","ORS",
            "spkoldtime","spnil","spfem","h.mufa","O2","spasplus","ace(lab)","acefen","ace(abt)",
            "mlcet","lcet(T)","lopa(C)","lopa(T)","vomistop","h.raal","h.rst","h.selan","vert16",
            "amlo-at","amlip5","asp","spasmonil", "tramp", "oindmx", "oinburnol", "pantodex", "dsr",
            "clav625", "spmlcet", "ap(gal)", "ap(ran)", "ap(lab)", "vov", "vovplus", "rantd", "dip",
            "spambrolsjr", "spkedexjr", "spalbum", "spb3", "h.ayarij", "omee", "omeeD", "prsdine",
            "raligesic", "raligesic-s", "macni", "onmi", "tphb", "telh", "tel40", "losah", "elpressAT",
            "glimpM1", "glimpM2", "glimpMP2", "glimMV2", "vidam", "atozac", "multirich", "leviz", "mox375",
            "doo625"
    };

    private static final String[] complaints = new String[]{ "headache", "fever", "cold", "knee pain",
            "back pain","abscess","throat pain","stomach pain","indigestion","diarrhea","shoulder pain",
            "giddiness","body pain","dysmenorrhea","amenorrhea","ear pain","tooth pain","diarrhea","insomnia",
            "chest pain","leucorrhoea","sinusitis","vomiting","nausea","dyspnea","constipation","dub","itching"

    };

    private static final String[] kco = new String[]{
            "DM", "HT", "BA", "epilepsy", "heart condition"
    };

    DatabaseHelper myDB;

    DatePickerDialog.OnDateSetListener mDateSetListener;


    EditText ed1,ed2,ed3,ed6,ed7;
    Button b1;
    private MultiAutoCompleteTextView ed4,ed5,ed8;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myDB = new DatabaseHelper(this);
        ed1 = (EditText) findViewById(R.id.nedit);
        ed2 = (EditText) findViewById(R.id.aedit);
        ed3 = (EditText) findViewById(R.id.gedit);
        ed6 = (EditText) findViewById(R.id.sugaredit);
        ed7 = (EditText) findViewById(R.id.tempedit);
        ed4 = (MultiAutoCompleteTextView) findViewById(R.id.cedit);
        ed5 = (MultiAutoCompleteTextView) findViewById(R.id.medit);
        tv1 = (TextView) findViewById(R.id.dateedittext);
        ed8 = (MultiAutoCompleteTextView) findViewById(R.id.kcoedit);
        Slidr.attach(this); //slider

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //date only
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        activity_add.this,
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

        //autocomplete for medicine
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,medicine);
        ed5.setAdapter(adapter);
        ed5.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //autocomplete for complaint
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,complaints);
        ed4.setAdapter(adapter1);
        ed4.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        //autocomplete for k/c/o
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kco);
        ed8.setAdapter(adapter2);
        ed8.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        b1 = (Button) findViewById(R.id.savebutton);

        AddData();
    }
    public void AddData(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( ed1.getText().toString().trim().length() == 0 || ed2.getText().toString().trim().length() == 0 || ed4.getText().toString().trim().length() == 0 ||
                        ed5.getText().toString().trim().length() == 0)
                        {
                            Snackbar.make(v,"Please Enter All Details",Snackbar.LENGTH_LONG).show();
                            //Toast.makeText(activity_add.this,"Please enter all details", Toast.LENGTH_LONG).show();
                        }

                else {
                   boolean isInserted =  myDB.insertData(tv1.getText().toString(),
                            ed1.getText().toString(),
                            ed2.getText().toString(),
                            ed3.getText().toString(),
                            ed6.getText().toString(),
                            ed7.getText().toString(),
                            ed8.getText().toString(),
                            ed4.getText().toString(),
                            ed5.getText().toString());

                    ed1.getText().clear();
                    ed2.getText().clear();
                    ed3.getText().clear();
                    ed4.getText().clear();
                    ed5.getText().clear();
                    ed6.getText().clear();
                    ed7.getText().clear();
                    ed8.getText().clear();
                    if(isInserted){
                        //Toast.makeText(activity_add.this, "Record Added", Toast.LENGTH_LONG).show();
                        Snackbar.make(v,"Patient Record Added",Snackbar.LENGTH_LONG).show();
                    }

                    else{
                        Snackbar.make(v,"Patient Record NOT Added",Snackbar.LENGTH_LONG).show();
                        //Toast.makeText(activity_add.this, "Record not Added", Toast.LENGTH_LONG).show();
                    }

                }
            }

        });
    }




}
