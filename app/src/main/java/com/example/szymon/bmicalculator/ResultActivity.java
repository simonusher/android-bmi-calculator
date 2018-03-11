package com.example.szymon.bmicalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView resultTextView = findViewById(R.id.resultText);
        String result = getIntent().getExtras().getString("result");
        if(result != null){
            resultTextView.setText(result);
        }
        int colorCode = getIntent().getExtras().getInt("colorCode");
        getWindow().getDecorView().setBackgroundColor(colorCode);
    }
}
