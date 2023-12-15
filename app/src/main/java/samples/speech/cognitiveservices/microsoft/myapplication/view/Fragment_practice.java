package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.stream.Collectors;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Fractice_adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topic;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.FavoriteTopic;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_practice extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        ImageView back, back_item, next_item;
        TextView currentTopic,title;
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_bottom);
        bottomNavigationView.setVisibility(View.GONE);
        view = inflater.inflate(R.layout.fragment_practice, container, false);
        Share_revise data = ((MainActivity) requireActivity()).getData_revise();
        Topic topic = data.getTopicPractice().getValue();
        RecyclerView recyclerView = view.findViewById(R.id.rycPractice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        back = view.findViewById(R.id.back_practice);
        back_item = view.findViewById(R.id.back_item_practice);
        next_item = view.findViewById(R.id.next_item_practice);
        currentTopic = view.findViewById(R.id.text_practice);
        title = view.findViewById(R.id.title_practice);
        title.setText(topic.getTopic());
        int size = topic.getChildtopic().size();
        currentTopic.setText("1/"+size);
        back.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_practice_to_fragment_list);
            bottomNavigationView.setVisibility(View.VISIBLE);
        });
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()){
            @Override
            protected int getHorizontalSnapPreference() {
                return SNAP_TO_END;
            }
        };
        back_item.setOnClickListener(view1 -> {
            int currentItem = layoutManager.findFirstCompletelyVisibleItemPosition();
            if(0<currentItem) {
                currentItem--;
                smoothScroller.setTargetPosition(currentItem);
                layoutManager.startSmoothScroll(smoothScroller);
                currentTopic.setText(currentItem + 1 + "/" + size);
            }
        });
        next_item.setOnClickListener(view1 -> {
            int currentItem = layoutManager.findFirstCompletelyVisibleItemPosition();
            if(currentItem<size-1) {
                currentItem++;
                smoothScroller.setTargetPosition(currentItem);
                layoutManager.startSmoothScroll(smoothScroller);
                currentTopic.setText(currentItem + 1 + "/" + size);
            }
        });
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int currentItem = layoutManager.findFirstCompletelyVisibleItemPosition();
                    if(currentItem != RecyclerView.NO_POSITION){
                        currentTopic.setText(currentItem + 1 + "/" + size);
                    }
                }
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        List<FavoriteTopic> favoriteTopicList = topic.getChildtopic().stream().map(subtopic->new FavoriteTopic(subtopic.getChildtopic(),subtopic.getImage())).collect(Collectors.toList());

        Fractice_adapter fracticeAdapter = new Fractice_adapter(view, new Fractice_adapter.ClickDetail() {
            @Override
            public void moveToFractice2() {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_fragment_practice_to_fragment_pratice2);
            }
        });
        fracticeAdapter.setData(favoriteTopicList);
        recyclerView.setAdapter(fracticeAdapter);

        return view;
    }
}
