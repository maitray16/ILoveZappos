package com.example.maitrayshah.ilovezappos.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maitrayshah.ilovezappos.R;

public class IntroActivity extends AppCompatActivity {

    TextView ilove;
    TextView zappos;
    TextView built;
    TextView maitray;
    Button button_explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ilove = (TextView) findViewById(R.id.ilove);
        zappos = (TextView) findViewById(R.id.zappos);
        built = (TextView) findViewById(R.id.built);
        maitray = (TextView) findViewById(R.id.maitray);
        button_explore = (Button) findViewById(R.id.button_explore);

        Typeface face_bold = Typeface.createFromAsset(getAssets(), "Raleway-Bold.ttf");
        Typeface face_semi = Typeface.createFromAsset(getAssets(), "Raleway-SemiBold.ttf");

        ilove.setTypeface(face_bold);
        zappos.setTypeface(face_bold);
        button_explore.setTypeface(face_semi);
        built.setTypeface(face_semi);
        maitray.setTypeface(face_semi);

        button_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
