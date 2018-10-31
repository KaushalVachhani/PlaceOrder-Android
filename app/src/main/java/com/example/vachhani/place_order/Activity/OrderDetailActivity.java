package com.example.vachhani.place_order.Activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vachhani.place_order.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class OrderDetailActivity extends AppCompatActivity {

    ImageView imgQrCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        imgQrCode = findViewById(R.id.imgQrCode);
        String message = getIntent().getStringExtra("msg");
        String orderId = getIntent().getStringExtra("oderId");
        generateQr(orderId);
    }
    public void generateQr(String qr)
    {
        Resources r = getResources();
        int dp = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 240,r.getDisplayMetrics()));

        Log.e("size", String.valueOf(dp));

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qr, BarcodeFormat.QR_CODE,dp,dp);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
