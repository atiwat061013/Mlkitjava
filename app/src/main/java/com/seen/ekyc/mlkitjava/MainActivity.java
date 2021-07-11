package com.seen.ekyc.mlkitjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCapture;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureResult;

public class MainActivity extends AppCompatActivity {

    Button btn_custom;
    private static TextView mTextResult;
    private static ImageView mImageResult;


    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA
    };

    private static final int RC_CAMERA_AND_EXTERNAL_STORAGE_CUSTOM = 0x01 << 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextResult = findViewById(R.id.text_detect_result);
        mImageResult = findViewById(R.id.img_detect_result);

        btn_custom = findViewById(R.id.custom_btn);
        btn_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startCustomActivity();
                    return;
                }
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, RC_CAMERA_AND_EXTERNAL_STORAGE_CUSTOM);
            }
        });


    }

    private void startCustomActivity() {
        Intent intent = new Intent(this, LivenessCustomActivity.class);
        this.startActivity(intent);
    }

    public static final MLLivenessCapture.Callback customCallback = new MLLivenessCapture.Callback() {
        @Override
        public void onSuccess(MLLivenessCaptureResult result) {
            mTextResult.setText(result.toString());
            mTextResult.setBackgroundResource(result.isLive() ? R.drawable.bg_blue : R.drawable.bg_red);
            ///isLive True = blue False = red
            mImageResult.setImageBitmap(result.getBitmap());
        }

        @Override
        public void onFailure(int errorCode) {
            mTextResult.setText("errorCode:" + errorCode);
        }
    };

}

