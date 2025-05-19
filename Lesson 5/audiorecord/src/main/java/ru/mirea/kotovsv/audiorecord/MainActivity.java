package ru.mirea.kotovsv.audiorecord;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

import ru.mirea.kotovsv.audiorecord.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 200;
    private final String TAG = MainActivity.class.getSimpleName();

    private boolean isWork;
    private String fileName = null;
    private Button recordButton = null;
    private Button playButton = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    boolean isStartRecord = true;
    boolean isStartPlay = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getRoot().setBackgroundColor(Color.argb(75, 50, 255, 170));

        recordButton = binding.rec;
        playButton = binding.play;
        playButton.setEnabled(false);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStartRecord){
                    recordButton.setText("Stop recording, 9, БСБО-07-22");
                    playButton.setEnabled(false);
                    startRecord();
                } else{
                    recordButton.setText("Start recording, 9, БСБО-07-22");
                    playButton.setEnabled(true);
                    stopRecord();
                }
                isStartRecord = !isStartRecord;

            }
        });

        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isStartPlay){
                    playButton.setText("Stop playing");
                    recordButton.setEnabled(false);
                    startPlay();
                } else {
                    playButton.setText("Start playing");
                    recordButton.setEnabled(true);
                    stopPlay();
                }
                isStartPlay = !isStartPlay;
            }
        });


        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO);
        if(audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED){
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION);
        }
    }


    @Override
    public	void	onRequestPermissionsResult(int	requestCode,	@NonNull	String[]	permissions,	@NonNull	int[]
            grantResults)	{
        //	производится	проверка	полученного	результата	от	пользователя	на	запрос	разрешения	Camera
        super.onRequestPermissionsResult(requestCode,	permissions,	grantResults);
        switch	(requestCode){
            case	REQUEST_CODE_PERMISSION:
                isWork		=	grantResults[0]	==	PackageManager.PERMISSION_GRANTED;
                break;
        }
        if	(!isWork	)	finish();
    }




    private void startRecord(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        fileName = getExternalFilesDir(null).getAbsolutePath() + "/audiorecord.3gp";
        Log.d("FindFile", getExternalFilesDir(null).getAbsolutePath() + "/audiorecord.3gp");
        recorder.setOutputFile(fileName);

        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try{
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,	"prepare()	failed");
        }
        File audioFile = new File(fileName);
        Log.d(TAG, "Recording started. File path: " + fileName);
        Log.d(TAG, "File exists: " + audioFile.exists());
    }

    private void stopRecord(){
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlay() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,	"prepare()	failed");
        }
    }

    private void stopPlay() {
        player.release();
        player = null;
    }

}