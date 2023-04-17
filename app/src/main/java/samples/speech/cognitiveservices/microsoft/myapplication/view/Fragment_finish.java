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

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Finish_Adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value_revise;
import samples.speech.cognitiveservices.microsoft.myapplication.R;

public class Fragment_finish extends Fragment {
    Finish_Adapter finish_adapter;
    RecyclerView recyclerView;
    List<Value_revise> value_reviseList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish,container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.rcv_finish);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        finish_adapter = new Finish_Adapter(value_reviseList);
        recyclerView.setAdapter(finish_adapter);
        return view;
    }
}
