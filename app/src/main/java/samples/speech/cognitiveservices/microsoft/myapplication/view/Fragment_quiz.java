package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_quiz extends Fragment {
    private Button list20, list50, list100, chooseWord, buttonStart;
    private ShareViewModel shareViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        TextView title = view.findViewById(R.id.title_quiz);
        ImageView back = view.findViewById(R.id.back_quiz);
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        list20 = view.findViewById(R.id.list20);
        list50 = view.findViewById(R.id.list50);
        list100 = view.findViewById(R.id.list100);
        chooseWord = view.findViewById(R.id.chooseWord);
        buttonStart = view.findViewById(R.id.startQuiz);
        list20.setOnClickListener(view1 -> chooseButton(list20, 20));
        list50.setOnClickListener(view1 -> chooseButton(list50, 50));
        list100.setOnClickListener(view1 -> chooseButton(list100, 100));
        list20.performClick();
        shareViewModel.getVocabulary().observe(getViewLifecycleOwner(),vocabularies->{
            title.setText(vocabularies.first);
        });
        back.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();

        });
        chooseWord.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_quiz_to_fragment_quiz1);
        });
        buttonStart.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_quiz_to_fragment_quiz3);
        });
        return view;
    }

    private void chooseButton(Button button, int quizList) {
        shareViewModel.setShare_sotuquiz3(quizList);
        defaultColorButton();
        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.teal_200)));
    }

    private void defaultColorButton() {
        list20.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green_100)));
        list50.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green_100)));
        list100.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green_100)));
    }
}
