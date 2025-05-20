package ru.mirea.kotovsv.mireaproject;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ru.mirea.kotovsv.mireaproject.databinding.FragmentAudioRecordBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AudioRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AudioRecordFragment extends Fragment {

    private FragmentAudioRecordBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 200;
    private final String TAG = AudioRecordFragment.class.getSimpleName();

    private String fileName = null;
    private Button recordButton = null;
    private MediaRecorder recorder = null;
    boolean isStartRecord = true;
    private final MutableLiveData<List<File>> recordings = new MutableLiveData<>(new ArrayList<>());

    private AudioAdapter adapter;

    MediaPlayer mediaPlayer ;
    // You already know the path since you set it
    String currentPath;
    

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AudioRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudioRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AudioRecordFragment newInstance(String param1, String param2) {
        AudioRecordFragment fragment = new AudioRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(CameraFragment);

        binding = FragmentAudioRecordBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        LinearLayout root = binding.getRoot();
        root.setBackgroundColor(Color.argb(75, 50, 255, 170));

        recordButton = binding.recordButton;


        adapter = new AudioAdapter(recordings.getValue(), this::playAudio, this::pauseAudio);
        binding.audioList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.audioList.setAdapter(adapter);

        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(root.getContext(), android.Manifest.permission.RECORD_AUDIO);
        if(!(audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED)){

            ActivityCompat.requestPermissions(requireActivity(), new String[] {android.Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION);
        }

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStartRecord){
                    recordButton.setText("Остановить");
//                    playButton.setEnabled(false);
                    startRecord();
                } else{
                    recordButton.setText("Записать");
//                    playButton.setEnabled(true);
                    stopRecord();
                }
                isStartRecord = !isStartRecord;

            }
        });

        recordings.observe(getViewLifecycleOwner(), files -> adapter.notifyDataSetChanged());

        return root;
    }


//    @Override
//    public	void	onRequestPermissionsResult(int	requestCode,	@NonNull	String[]	permissions,	@NonNull	int[]
//            grantResults)	{
//        //	производится	проверка	полученного	результата	от	пользователя	на	запрос	разрешения	Camera
//        super.onRequestPermissionsResult(requestCode,	permissions,	grantResults);
//        switch	(requestCode){
//            case	REQUEST_CODE_PERMISSION:
//                isWork		=	grantResults[0]	==	PackageManager.PERMISSION_GRANTED;
//                break;
//        }
//        if	(!isWork	)
//            finish();
//    }




    private void startRecord(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        fileName = requireContext().getExternalFilesDir(null).getAbsolutePath() + "/audiorecord_"+timeStamp+"_.3gp";
//        Log.d("FindFile", requireContext().getExternalFilesDir(null).getAbsolutePath() + "/audiorecord.3gp");
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
        try {
            if (recorder != null) {
                Log.d(TAG, "Attempting to stop recorder");

                // Proper stopping sequence
                try {
                    recorder.stop();
                } catch (IllegalStateException e) {
                    Log.e(TAG, "Failed to stop recorder (was it started properly?)", e);
                }

                recorder.reset();  // Reset before release
                recorder.release();
                recorder = null;
                isStartRecord = false;

                adapter.SetCurrentName( fileName);
                List<File> current = recordings.getValue();
                if (current != null) {
                    current.add(new File(fileName));
                    recordings.setValue(current);
                }

                Log.d(TAG, "Recorder successfully stopped and released");
            } else {
                Log.w(TAG, "stopRecord called but recorder is null");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error stopping recorder", e);
            // Ensure recorder is null even if error occurs
            recorder = null;
        }
    }


    private void playAudio(File file) {


        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer =  new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            currentPath = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void pauseAudio(File file) {
        Log.d("audio", currentPath);
        Log.d("audio", file.getAbsolutePath());
        if (Objects.equals(currentPath, file.getAbsolutePath()) && mediaPlayer != null
                && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}