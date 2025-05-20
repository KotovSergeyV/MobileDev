package ru.mirea.kotovsv.mireaproject;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudRecViewHolder> {
    private final List<File> audRecords;
    private final OnRecordingPause pauseListener;
    private final OnRecordingPlay playListener;
    private String fileName = " ";

    public interface OnRecordingPause {
        void onPauseClick(File file);
    }
    public interface OnRecordingPlay {
        void onPlayClick(File file);
    }

    public AudioAdapter(List<File> notes, OnRecordingPlay playListener, OnRecordingPause pauseListener) {
        this.audRecords = notes;
        this.playListener = playListener;
        this.pauseListener = pauseListener;
    }

    public void SetCurrentName(String name){
        fileName = name;
    }

    @NonNull
    @Override
    public AudRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_for_recycler,
                parent, false);  // Must match your XML file
        return new AudRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudRecViewHolder holder, int position) {
        File file = audRecords.get(position);

        holder.recording.setText(fileName);

        holder.recordingPlay.setOnClickListener(v -> playListener.onPlayClick(file));
        holder.recordingPause.setOnClickListener(v -> pauseListener.onPauseClick(file));
    }

    @Override
    public int getItemCount() {
        return audRecords.size();
    }

    static class AudRecViewHolder extends RecyclerView.ViewHolder {
        TextView recording;
        ImageButton  recordingPlay;
        ImageButton recordingPause;

        public AudRecViewHolder(@NonNull View itemView) {
            super(itemView);
            recording = itemView.findViewById(R.id.audioRecord);
            recordingPlay = itemView.findViewById(R.id.audRecPlay);
            recordingPause = itemView.findViewById(R.id.audRecPause);
        }
    }
}