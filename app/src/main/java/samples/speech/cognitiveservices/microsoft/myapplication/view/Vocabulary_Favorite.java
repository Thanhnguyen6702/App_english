package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Fractice2_adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.FavoriteVoca;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.VocabDatabase;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Sound;

public class Vocabulary_Favorite extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_favorite,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_favorite);
        List<FavoriteVoca> vocaList = VocabDatabase.getInstance(requireContext()).daoVocab().getListVocabFavorite();
        Fractice2_adapter fractice2Adapter = new Fractice2_adapter(vocaList, Sound.getInstance(requireContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fractice2Adapter);
        return view;
    }
}
