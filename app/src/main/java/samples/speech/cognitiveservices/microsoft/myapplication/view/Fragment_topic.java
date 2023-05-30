package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Topic_Adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_topic extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        Share_revise share_revise = ((MainActivity) requireActivity()).getData_revise();
        share_revise.getlistTopic().observe(getViewLifecycleOwner(), listTopic -> {
            if (!listTopic.isEmpty()) {
                RecyclerView recyclerView = view.findViewById(R.id.rycTopic);
                LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
                recyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(decoration);
                NavController navController = Navigation.findNavController(view);
                Topic_Adapter topic_adapter = new Topic_Adapter(listTopic,navController);
                recyclerView.setAdapter(topic_adapter);
            }
        });

        return view;
    }
}
