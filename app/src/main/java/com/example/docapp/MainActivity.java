package com.example.docapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private long backpressTime;
    private Toast backToast;



    ActionBar actionBar;

    Button b1;
    EditText ed1,ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0c62ed")));

        b1 = (Button)findViewById(R.id.loginid);
        ed1 = (EditText)findViewById(R.id.docname);
        ed2 = (EditText)findViewById(R.id.password);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().equals("Zumar") &&
                        ed2.getText().toString().equals("zumasufi")) {
                    Snackbar.make(v,"Signing in....",Snackbar.LENGTH_SHORT).show();

                    ed1.getText().clear();
                    ed2.getText().clear();
                    Intent activity2Intent = new Intent(getApplicationContext(), activity_add_view.class);
                    startActivity(activity2Intent);

                }else if(ed1.getText().toString().trim().length() == 0 || ed2.getText().toString().trim().length() == 0 ||
                (ed1.getText().toString().trim().length() > 0) || (ed2.getText().toString().trim().length() > 0)){
                    Snackbar.make(v,"Wrong Credentials!!",Snackbar.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        if (backpressTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpressTime = System.currentTimeMillis();
    }
}
