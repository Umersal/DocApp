package com.example.docapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.r0adkll.slidr.Slidr;

public class activity_un_al extends AppCompatActivity {

    CardView c1,c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_al);

        c1 = (CardView) findViewById(R.id.unani_stocks);
        c2 = (CardView) findViewById(R.id.allomedicine);
        Slidr.attach(this);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent unani_intent = new Intent(getApplicationContext(),activity_unani.class);
                startActivity(unani_intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent allo_intent = new Intent(getApplicationContext(), activity_add_medicine.class);
                startActivity(allo_intent);
            }
        });

    }


}
