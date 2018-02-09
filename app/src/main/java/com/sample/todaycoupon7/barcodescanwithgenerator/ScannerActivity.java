package com.sample.todaycoupon7.barcodescanwithgenerator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.sample.todaycoupon7.barcodescanwithgenerator.support.ExtCaptureManager;

/**
 * Created by chaesooyang on 2018. 2. 9..
 */

public class ScannerActivity extends AppCompatActivity implements ExtCaptureManager.OnBarcodeScanResultListener {

    private static final String TAG = "ScannerActivity";

    private DecoratedBarcodeView mScannerView;
    private TextView mTvScanResult;

    private ExtCaptureManager mCaptureManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        setTitle("Barcode Scanner");

        mTvScanResult = findViewById(R.id.tvScanResult);
        mScannerView = findViewById(R.id.zxing_barcode_scanner);
        mCaptureManager = new ExtCaptureManager(this, mScannerView);
        mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);
        mCaptureManager.setOnBarcodeScanResultListener(this);
        mCaptureManager.setRefreshInterval(1000);
        mCaptureManager.decode();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaptureManager.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCaptureManager.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCaptureManager.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mCaptureManager.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mCaptureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /****************************************
     * ExtCaptureManager.OnBarcodeScanResultListener
     ****************************************/
    @Override
    public void onStartScan() {
        mTvScanResult.setText("Scanning...");
    }

    @Override
    public void onResult(String barcode) {
        mTvScanResult.setText(barcode);
    }
}
