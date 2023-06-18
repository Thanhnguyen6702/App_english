package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Topic_Adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topic;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_topic extends Fragment implements Topic_Adapter.OnclickItemadapter {
    View view;
    ShareViewModel shareViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_topic, container, false);
        Share_revise share_revise = ((MainActivity) requireActivity()).getData_revise();
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        RecyclerView recyclerView = view.findViewById(R.id.rycTopic);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
        if (shareViewModel.getNav().getValue() == R.id.action_fragment_topic_to_fragment_home) {
            share_revise.getlistTopic().observe(getViewLifecycleOwner(), listTopic -> {
                if (!listTopic.isEmpty()) {
                    Topic_Adapter topic_adapter = new Topic_Adapter(listTopic, this,view);
                    recyclerView.setAdapter(topic_adapter);
                }
            });
        } else {
            shareViewModel.getListtopicphatam().observe(getViewLifecycleOwner(), listTopic -> {
                if (!listTopic.isEmpty()) {

                    List<Topic> listtopic1 = new ArrayList<>(listTopic);
                    Topic_Adapter topic_adapter = new Topic_Adapter(listtopic1, this,view);
                    recyclerView.setAdapter(topic_adapter);
                }
            });
        }

        return view;
    }

    @Override
    public int Onclick() {
        return shareViewModel.getNav().getValue();
    }
}
