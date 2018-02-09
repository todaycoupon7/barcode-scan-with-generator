package com.sample.todaycoupon7.barcodescanwithgenerator.support;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * Created by chaesooyang on 2018. 2. 9..
 */

public class ExtCaptureManager extends CaptureManager {

    public interface OnBarcodeScanResultListener {
        void onStartScan();
        void onResult(String barcode);
    }

    private int REFRESH_INTERVAL = 1000;
    private DecoratedBarcodeView mScannerView;
    private OnBarcodeScanResultListener mListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public ExtCaptureManager(Activity activity, DecoratedBarcodeView barcodeView) {
        super(activity, barcodeView);
        mScannerView = barcodeView;
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(mRefreshRunnable);
        super.onDestroy();
    }

    public void setOnBarcodeScanResultListener(OnBarcodeScanResultListener listener) {
        mListener = listener;
    }

    public void setRefreshInterval(int ms) {
        REFRESH_INTERVAL = ms;
    }

    @Override
    public void decode() {
        super.decode();
        if(mListener != null) {
            mListener.onStartScan();
        }
    }

    @Override
    protected void returnResult(BarcodeResult rawResult) {
        if(mListener != null) {
            mListener.onResult(rawResult.getText());
        }
        mHandler.postDelayed(mRefreshRunnable, REFRESH_INTERVAL);
    }

    private Runnable mRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            mScannerView.resume();
            decode();
        }
    };

}
