package com.example.matt.recipeminiapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mButton = findViewById(R.id.startButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                launchActivity();
            }
        });

    }

    private void launchActivity(){
        Intent intent = new Intent(mContext, SearchActivity.class);

        startActivityForResult(intent,1);
    }
}
