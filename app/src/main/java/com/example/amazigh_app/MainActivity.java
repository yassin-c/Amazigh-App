package com.example.amazigh_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button over_ons = (Button) findViewById(R.id.button4);
        over_ons.setOnClickListener(this); // calling onClick() method
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button4:
                startActivity(new Intent(MainActivity.this, Over_ons.class));
                break;
            default:
                break;
        }
    }
}