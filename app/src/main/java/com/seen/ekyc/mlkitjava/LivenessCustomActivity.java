package com.seen.ekyc.mlkitjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.huawei.hms.mlsdk.livenessdetection.MLLivenessCaptureResult;
import com.huawei.hms.mlsdk.livenessdetection.MLLivenessDetectView;
import com.huawei.hms.mlsdk.livenessdetection.OnMLLivenessDetectCallback;

import java.util.Arrays;

import static com.huawei.hms.mlsdk.livenessdetection.MLLivenessDetectView.DETECT_MASK;

public class LivenessCustomActivity extends AppCompatActivity {

    private MLLivenessDetectView mlLivenessDetectView;
    private FrameLayout mPreviewContainer;
    private ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveness_custom);

        mPreviewContainer = findViewById(R.id.surface_layout);

        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;

        mlLivenessDetectView = new MLLivenessDetectView.Builder()
                .setContext(this)
                .setOptions(DETECT_MASK)
                // set Rect of face frame relative to surface in layout
                .setFaceFrameRect(new Rect(0, 0, widthPixels, dip2px(this,480)))
                .setDetectCallback(new OnMLLivenessDetectCallback() {
                    @Override
                    public void onCompleted(MLLivenessCaptureResult result) {
                        MainActivity.customCallback.onSuccess(result);

                        Log.d("LivenessCustom", "arr: " + result);
                        finish();
                    }

                    @Override
                    public void onError(int i) {
                        MainActivity.customCallback.onFailure(i);
                        finish();

                    }

                    @Override
                    public void onStateChange(int i, Bundle bundle) {

                    }

                    @Override
                    public void onInfo(int i, Bundle bundle) {

                    }
                }).build();

        mPreviewContainer.addView(mlLivenessDetectView);
        mlLivenessDetectView.onCreate(savedInstanceState);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlLivenessDetectView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mlLivenessDetectView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mlLivenessDetectView.onResume();
    }
}