package com.example.popuprecordapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/*
YOU MAY USE THIS IN values/style AS THIS's ACTIVITY STYLE:
 */
/*
 <style name="AppTheme.PopTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowBackground">@android:color/transparent</item> //no background to this page
        <item name="android:windowContentOverlay">@null</item>
        <item name="android:windowCloseOnTouchOutside">true</item>              //activity closes when user touch outside the frame
        <item name="android:windowNoTitle">true</item>
        <item name="android:windowIsFloating">true</item>
        <item name="android:backgroundDimEnabled">false</item>
        <item name="android:layout_gravity">center_horizontal</item>            //buttons will be centered to screen both horizontally and vertically
    </style>
 */

public class PopupRecWindow extends AppCompatActivity {
    DisplayMetrics dm;
    int width, height;
    Button butStartRec;
    Button butPlayRec;
    Button butPauseRec;
    MediaRecorder myAudioRecorder;
    String outputFileLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_rec_window);
        setDisplay();
        /*
        recording process
         */
        butStartRec = findViewById(R.id.startRecBut);
        butPauseRec = findViewById(R.id.pauseRecBut);
        butPlayRec = findViewById(R.id.playRecBut);
        butPauseRec.setEnabled(false);
        butPlayRec.setEnabled(false);
        outputFileLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";        //this is the location the recording will be saved
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFileLocation);
        recProcess();
    }

    /*
    set background size
     */
    protected void setDisplay() {
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.4));
    }

    protected void recProcess() {
        butStartRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                } catch (IllegalStateException ise) {
                    // make something ...
                } catch (IOException ioe) {
                    // make something
                }
                butStartRec.setEnabled(false);
                butPauseRec.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        butPauseRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                butStartRec.setEnabled(true);
                butPauseRec.setEnabled(false);
                butPlayRec.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_SHORT).show();
            }
        });

        butPlayRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(outputFileLocation);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // make something
                }
            }
        });
    }

}
