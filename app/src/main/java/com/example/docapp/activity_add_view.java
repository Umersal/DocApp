package com.example.docapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.r0adkll.slidr.Slidr;

public class activity_add_view extends AppCompatActivity {
    CardView c1,c2,c3,c4,c5;
    DatabaseHelper db;
    Context context;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_dot,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        switch (item.getItemId()){
            case R.id.one:
                paymentImage();
                break;

            case R.id.two:
                exportPDF();
                break;

                default:
                    break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void paymentImage(){
        Intent newintent = new Intent(getApplicationContext(),activity_payment.class);
        startActivity(newintent);
    }

    public void exportPDF(){
        Intent new_intent = new Intent(getApplicationContext(),activity_backup.class);
        startActivity(new_intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view);

        this.context = context;

        c1 = (CardView) findViewById(R.id.add_patient);
        c2 = (CardView) findViewById(R.id.view);
        c3 = (CardView) findViewById(R.id.med_stock);
        c4 = (CardView) findViewById(R.id.balancehistory);
        c5 = (CardView) findViewById(R.id.game);
        db = new DatabaseHelper(this);
        Slidr.attach(this);




        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), activity_add.class);
                startActivity(addIntent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent(getApplicationContext(), activity_view.class);
                startActivity(viewIntent);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicinestockint = new Intent(getApplicationContext(), activity_un_al.class);
                startActivity(medicinestockint);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent balintent = new Intent(getApplicationContext(), activity_balance.class);
                startActivity(balintent);
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(getApplicationContext(), activity_medicine_search.class);
                startActivity(gameIntent);
            }
        });


    }


}
