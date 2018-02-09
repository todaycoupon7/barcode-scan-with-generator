package com.sample.todaycoupon7.barcodescanwithgenerator;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by chaesooyang on 2018. 2. 9..
 */

public class GeneratorActivity extends AppCompatActivity {

    private EditText mEtInput;
    private ImageView mIvBarcode;

    private Bitmap mBarcodeBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        setTitle("Barcode Generator");

        mEtInput = findViewById(R.id.etInput);
        mIvBarcode = findViewById(R.id.ivBarcode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recycledBarcodeBitmap();
    }

    private void recycledBarcodeBitmap() {
        if(mBarcodeBitmap != null) {
            if(!mBarcodeBitmap.isRecycled()) {
                mBarcodeBitmap.recycle();
            }
            mBarcodeBitmap = null;
        }
    }

    public void onClicked(View v) {
        new BarcodeGeneratorTask(
                mIvBarcode.getWidth(),
                mIvBarcode.getHeight(),
                BarcodeFormat.CODE_128).execute(mEtInput.getText().toString());
    }

    private class BarcodeGeneratorTask extends AsyncTask<String, String, Bitmap> {

        private int width, height;
        private BarcodeFormat barcodeFormat;

        public BarcodeGeneratorTask(int width, int height, BarcodeFormat barcodeFormat) {
            this.width = width;
            this.height = height;
            this.barcodeFormat = barcodeFormat;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                final String barcodeNumber = strings[0];
                MultiFormatWriter writer = new MultiFormatWriter();
                BitMatrix bitMatrix = writer.encode(barcodeNumber, barcodeFormat, width, height);
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                for(int x=0; x<bitMatrix.getWidth(); x++) {
                    for(int y=0; y<bitMatrix.getHeight(); y++) {
                        bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                return bitmap;
            } catch (WriterException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            recycledBarcodeBitmap();
            mBarcodeBitmap = bitmap;
            mIvBarcode.setImageBitmap(mBarcodeBitmap);
        }
    }

}
