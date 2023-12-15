package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Fractice_adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Value_Adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.FavoriteTopic;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.TopicDatabase;
import samples.speech.cognitiveservices.microsoft.myapplication.R;

public class Topic_Favorite extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_favorite,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_favorite);
        List<FavoriteTopic> listTopic = TopicDatabase.getInstance(requireContext()).daoTopic().getListTopicFavorite();
        Fractice_adapter fracticeAdapter = new Fractice_adapter(view, new Fractice_adapter.ClickDetail() {
            @Override
            public void moveToFractice2() {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_fragment_favorite_to_fragment_pratice2);
            }
        });
        fracticeAdapter.setData(listTopic);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(fracticeAdapter);
        return view;
    }
}
