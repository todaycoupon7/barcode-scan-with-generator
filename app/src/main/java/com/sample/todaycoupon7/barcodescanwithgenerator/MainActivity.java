package com.sample.todaycoupon7.barcodescanwithgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClicked(View v) {
        switch (v.getId()) {
            case R.id.btnGenerator:
                startActivity(new Intent(this, GeneratorActivity.class));
                break;
            case R.id.btnScanner:
                startActivity(new Intent(this, ScannerActivity.class));
                break;
        }
    }

}
