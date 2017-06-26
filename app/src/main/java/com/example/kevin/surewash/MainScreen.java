package com.example.kevin.surewash;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.kevin.surewash.R.styleable.FloatingActionButton;

//accessing \changed stuff
//

public class MainScreen extends AppCompatActivity {
    private int mCount;
    private final IntentIntegrator INTEGRATOR = new IntentIntegrator(this);

    FloatingActionButton logOutButton;
    FloatingActionButton qrButton;
    FloatingActionButton settingButton;
    Button learnedSomethingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //setting up the buttons
        qrButton = (FloatingActionButton) findViewById(R.id.qrButton);
        logOutButton = (FloatingActionButton) findViewById(R.id.logOutButton);
        learnedSomethingButton = (Button) findViewById(R.id.buttonLearned);
        settingButton = (FloatingActionButton) findViewById(R.id.settingButton);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonActivity(v);
            }
        });
        learnedSomethingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                buttonActivity(v);
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                buttonActivity(v);
            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                buttonActivity(v);
            }
        });

    }
    //@Override
    public void buttonActivity(View v) {
        if (v == logOutButton) {
            Intent intent = new Intent(MainScreen.this, LoginActivity.class);
            startActivity(intent);
            finish();  // This call is missing.
        } else if (v == settingButton) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }else if (v == learnedSomethingButton) {
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);
        }else if(v == qrButton){            /**
             * setDesiredBarcodeFormats: setting the scanner to scan qr codes, this can be
             * changed to scan other codes (bar codes) and may become useful if we want to
             * implement extended functionality beyond V1
             * setPrompt: a string shown to the user when scanning. May make this dynamic
             * to show how many qr codes have been scanned so far (probably V1)
             * setCameraId: use a specific camera of the device (front or back)
             * setBeepEnabled: when true, audible beep occurs when scan occurs
             * setBarcodeImageEnabled: Set to true to enable saving the barcode image and
             * sending its path in the result Intent.
             * initiateScan: open the scanner (after it has been configured)
             */
            INTEGRATOR.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            INTEGRATOR.setPrompt("Scan the qr code");
            INTEGRATOR.setCameraId(0);
            INTEGRATOR.setBeepEnabled(false);
            INTEGRATOR.setBarcodeImageEnabled(true);
            INTEGRATOR.initiateScan();
        }
    }


    /**
     * onActivityResult
     *
     * Defines what happens when a successful read of a qr code occurs. Right now (at base), when
     * a qr code is successfully scanned, the scanner is exited and the contents of the scan
     * are briefly shown on the device screen
     *
     * TODO the body of this function will need to be moved to DecodeUtils
     *
     * @param requestCode: Needed to pass to the intent integrator and super class
     * @param resultCode: Needed to pass to the intent integrator and super class
     * @param data: Needed to pass to the intent integrator and super class
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("Scan_Button", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("Scan_Button", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
