package ru.mirea.kotovsv.mireaproject;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.mirea.kotovsv.mireaproject.databinding.FragmentWorkerFragmentBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment worker_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkerFragment newInstance(String param1, String param2) {
        WorkerFragment fragment = new WorkerFragment();
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

        // Inflate the layout for this fragment
        @NonNull FragmentWorkerFragmentBinding binding	= FragmentWorkerFragmentBinding.inflate(inflater,	container,	false);
        View root = binding.getRoot();

        binding.buttonTaskStart.setOnClickListener(view -> {
            Log.d("WorkerFragment", "CLICK!");

            Data inputData = new Data.Builder()
                    .putString("TASK_DESCRIPTION", "ASYNC task with network required, SK")
                    .build();

            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();

            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(inputData)
                    .setConstraints(constraints)
                    .build();

            WorkManager.getInstance(requireContext()).enqueue(workRequest);

            WorkManager.getInstance(requireContext())
                    .getWorkInfoByIdLiveData(workRequest.getId())
                    .observe(getViewLifecycleOwner(), workInfo -> {
                        if (workInfo != null) {
                            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {

                                String result = workInfo.getOutputData().getString("RESULT");
                                binding.resultView.setText(result);
                            } else if (workInfo.getState() == WorkInfo.State.FAILED) {

                                binding.resultView.setText("Error accured");
                            }
                        }
                    });
        });

        return binding.getRoot();
    }

}