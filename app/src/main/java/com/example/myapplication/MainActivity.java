package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


//option + enter to fix import
public class MainActivity extends AppCompatActivity {
private int count = 0;

    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //wire the button
        Button btnMagic = findViewById(R.id.btnMagic);
        Button btnLessMagic = findViewById(R.id.btn2);
        final Button btnSecond = findViewById(R.id.actBtn);
        final TextView textView = findViewById(R.id.textMain);


        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity2();
            }
        });

        btnMagic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                //TextView textView = findViewById(R.id.textMain);
                textView.setText("It's " + count);
            }
        });

    btnLessMagic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            count--;
            btnSecond.setText("Yo");
            textView.setText("It's " + count);
        }
    });



    }
}
