package com.mspifarre.marc.scanmarket;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech tts;
    private final int CHECK_TTS_AVAILABLE = 100;
    private String textToSpeech = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, CHECK_TTS_AVAILABLE);

        startReading();
    }

    public void startReading(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    private TextToSpeech.OnInitListener listener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int i) {
            if(i == TextToSpeech.SUCCESS){
                int result = tts.setLanguage(new Locale("spa", "ESP"));
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "This Language is not supported");
                }else {

                    if(textToSpeech != null){
                        tts.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null);
                        textToSpeech = null;
                    }
                }

            }
            else if(i == TextToSpeech.ERROR){
                Log.e("ERROR", "error en el text to speech");
            }
        }
    };


    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanResult != null){
            if(scanResult.getContents() != null) {
                textToSpeech = scanResult.getContents();
            }
        }
        if(requestCode == CHECK_TTS_AVAILABLE){
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                tts = new TextToSpeech(this, listener);
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }
}
