package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.util.Log;
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

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Flashcard_adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_flashcard extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_flashcard, container, false);
        ShareViewModel shareViewModel = ((MainActivity) requireActivity()).getData_login();
        TextView title = view.findViewById(R.id.title_flashcard);
        title.setText(shareViewModel.getVocabulary().getValue().first);
        ImageView back_item = view.findViewById(R.id.back_item_flashcard);
        ImageView next_item = view.findViewById(R.id.next_item_flashcard);
        ImageView back = view.findViewById(R.id.back_flashcard);
        TextView current_item = view.findViewById(R.id.text_flashcard);
        RecyclerView recyclerView = view.findViewById(R.id.rcvFlashcard);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Flashcard_adapter flashcardAdapter = new Flashcard_adapter(shareViewModel.getVocabulary().getValue().second);
        recyclerView.setAdapter(flashcardAdapter);
        int size = shareViewModel.getVocabulary().getValue().second.size();
        current_item.setText("1/"+size);
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getHorizontalSnapPreference() {
                return SNAP_TO_END;
            }
        };
        back_item.setOnClickListener(view1 -> {
            int current = layoutManager.findFirstCompletelyVisibleItemPosition();
            if (current > 0) {
                current--;
                smoothScroller.setTargetPosition(current);
                layoutManager.startSmoothScroll(smoothScroller);
                current_item.setText(current+1+"/"+size);
            }
        });
        next_item.setOnClickListener(view1 -> {
            int current = layoutManager.findFirstCompletelyVisibleItemPosition();
            if(current<size-1) {
                current++;
                smoothScroller.setTargetPosition(current);
                layoutManager.startSmoothScroll(smoothScroller);
                current_item.setText(current + 1 + "/" + size);
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
                        current_item.setText(currentItem + 1 + "/" + size);
                    }
                }
            }
        });
        back.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_flashcard_to_fragment_pratice2);
        });
        return view;
    }
}
