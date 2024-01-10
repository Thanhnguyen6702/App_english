package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.stream.Collectors;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Fractice2_adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.FavoriteTopic;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.FavoriteVoca;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Sound;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_practice2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        LinearLayout flashcard,thuthach,luyentap;
        view = inflater.inflate(R.layout.fragment_practice2,container,false);
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmer_practice2);
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        TextView title = view.findViewById(R.id.title_practice2);
        flashcard = view.findViewById(R.id.item_flashcard);
        thuthach = view.findViewById(R.id.item_thuthach);
        luyentap =view.findViewById(R.id.item_luyentap);
        flashcard.setEnabled(false);
        thuthach.setEnabled(false);
        luyentap.setEnabled(false);
        ImageView back = view.findViewById(R.id.back_practice2);
        ShareViewModel shareViewModel = ((MainActivity) requireActivity()).getData_login();
        RecyclerView recyclerView = view.findViewById(R.id.rcv_pratice2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int firstItemTopMargin = (int) (getResources().getDisplayMetrics().density * 40); // Convert dp to pixels
            private int lastItemBottomMargin = (int) (getResources().getDisplayMetrics().density * 100); // Convert dp to pixels

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.top = firstItemTopMargin;
                }
                if (position == state.getItemCount() - 1) {
                    outRect.bottom = lastItemBottomMargin;
                }
            }
        });
        shareViewModel.getVocabulary().observe(getViewLifecycleOwner(),vocabularies -> {
            if(vocabularies != null) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                flashcard.setEnabled(true);
                thuthach.setEnabled(true);
                luyentap.setEnabled(true);
                title.setText(vocabularies.first);
                List<FavoriteVoca> listVocab = vocabularies.second.stream().map(vocabulary -> new FavoriteVoca(vocabulary.getTienganh(), vocabulary.getTiengviet(), vocabulary.getPhienam())).collect(Collectors.toList());
                Fractice2_adapter fractice2Adapter = new Fractice2_adapter(listVocab, Sound.getInstance(requireContext()));
                recyclerView.setAdapter(fractice2Adapter);
            }
        });

        flashcard.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_pratice2_to_fragment_flashcard);
        });
        luyentap.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_pratice2_to_fragment_quiz);
        });
        thuthach.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_pratice2_to_fragment_Challenge);
        });
        back.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });
        return  view;
    }
}
