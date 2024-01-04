package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Quiz_above_adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Quiz_below_adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Sound;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_quiz3 extends Fragment {
    private ShareViewModel shareViewModel;
    private Quiz_above_adapter quizAboveAdapter;
    private Quiz_below_adapter quizBelowAdapter;
    private List<Vocabulary> vocabularies;
    FlexboxLayoutManager flexboxLayoutManager_above;
    FlexboxLayoutManager flexboxLayoutManager_below;

    TextView english, phonetic, topic, english1, english2, english3, english4;
    ProgressBar progressBar;
    ImageView sound, back;
    View layoutSound,layoutGuess,rootView;
    private int start = 0;
    private int  MAXQUIZ;
    char[] chars;
    int pos;
    int numberTrue = 0;
    private Sound playAudio ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz3, container, false);
        rootView = view;
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        MAXQUIZ = shareViewModel.getSotuquiz3().getValue();
        progressBar = view.findViewById(R.id.progress_quiz3);
        layoutGuess = view.findViewById(R.id.layout1_quiz3);
        layoutSound = view.findViewById(R.id.layout2_quiz3);
        playAudio = Sound.getInstance(requireContext());
        back = view.findViewById(R.id.back_quiz3);
        back.setOnClickListener(view1 -> Navigation.findNavController(view).popBackStack());
        InitLayoutGuessWord(view);
        InitLayoutGuessSound(view);
        StartQuiz();
        return view;
    }

    private void InitLayoutGuessWord(View view) {
        english = view.findViewById(R.id.textE_layouttext);
        phonetic = view.findViewById(R.id.textP_layouttext);
        topic = view.findViewById(R.id.title_quiz3);
        vocabularies = shareViewModel.getVocabulary().getValue().second;
        topic.setText(shareViewModel.getVocabulary().getValue().first);
        quizAboveAdapter = new Quiz_above_adapter(new Quiz_above_adapter.ItemClick() {
            @Override
            public void charaterClick(int p) {
                pos = p;
                quizAboveAdapter.setData(chars, pos);
            }

            @Override
            public void result(boolean result) {
                if (result){
                    numberTrue++;
                }
            }
        });
        quizBelowAdapter = new Quiz_below_adapter(c -> {
            if (pos < chars.length) {
                chars[pos] = c;
                pos++;
                quizAboveAdapter.setData(chars, pos);
            }
            if (pos == chars.length){
                pos++;
                english.setVisibility(View.VISIBLE);
                phonetic.setVisibility(View.VISIBLE);
                playAudio.playAudio(english.getText().toString());
                new Handler().postDelayed(() -> StartQuiz(), 2500);
            }
        });
        RecyclerView recycler_below = view.findViewById(R.id.recycler_below);
        flexboxLayoutManager_above = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager_below = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager_above.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager_above.setJustifyContent(JustifyContent.CENTER);
        flexboxLayoutManager_above.setAlignItems(AlignItems.CENTER);
        flexboxLayoutManager_below.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager_below.setJustifyContent(JustifyContent.CENTER);
        flexboxLayoutManager_below.setAlignItems(AlignItems.CENTER);
        RecyclerView recycler_above = view.findViewById(R.id.recycler_above);
        recycler_above.setLayoutManager(flexboxLayoutManager_above);
        recycler_below.setLayoutManager(flexboxLayoutManager_below);
        recycler_above.setAdapter(quizAboveAdapter);
        recycler_below.setAdapter(quizBelowAdapter);
    }

    private void InitLayoutGuessSound(View view) {
        sound = view.findViewById(R.id.img_quiz3);
        english1 = view.findViewById(R.id.english1_quiz3);
        english2 = view.findViewById(R.id.english2_quiz3);
        english3 = view.findViewById(R.id.english3_quiz3);
        english4 = view.findViewById(R.id.english4_quiz3);
        setClickTextview(english1);
        setClickTextview(english2);
        setClickTextview(english3);
        setClickTextview(english4);
    }

    private void StartQuiz() {
        if (start < MAXQUIZ) {
            progressBar.setProgress((int) Math.ceil(100.0*start/MAXQUIZ));
            int index = start % vocabularies.size();
            pos = 0;
            setButton();
            randomLayout(layoutGuess,layoutSound);
            // Tạo mảng trộn các đáp án
            List<String> result = new ArrayList<>();
            List<Vocabulary> vocabularyRandom = new ArrayList<>(vocabularies);
            String textEnglish = vocabularies.get(index).getTienganh();
            String textPhonetic = vocabularies.get(index).getPhienam();
            result.add(textEnglish);
            playAudio.setWord(textEnglish);
            sound.setOnClickListener(view -> playAudio.playAudio(textEnglish));
            vocabularyRandom.remove(vocabularies.get(index));
            Collections.shuffle(vocabularyRandom);
            result.add(vocabularyRandom.get(0).getTienganh());
            result.add(vocabularyRandom.get(1).getTienganh());
            result.add(vocabularyRandom.get(2).getTienganh());
            Collections.shuffle(result);
            english1.setText(result.get(0));
            english2.setText(result.get(1));
            english3.setText(result.get(2));
            english4.setText(result.get(3));
            int textSize = textEnglish.length();
            chars = new char[textSize];
            quizBelowAdapter.setData(shuffleArray(textEnglish.toCharArray()));
            quizAboveAdapter.setData(chars, pos);
            quizAboveAdapter.setWords_correct(textEnglish.toCharArray());
            english.setText(textEnglish);
            phonetic.setText(textPhonetic);
            english.setVisibility(View.GONE);
            phonetic.setVisibility(View.GONE);
            start++;
        }
        else {
            int numberFalse = MAXQUIZ - numberTrue;
            Log.e("loi",MAXQUIZ+" "+numberFalse);
            NavDirections action = Fragment_quiz3Directions.actionFragmentQuiz3ToResultQuiz3(numberTrue,numberFalse);
            Navigation.findNavController(rootView).navigate(action);
        }
    }



    private char[] shuffleArray(char[] chars) {
        List<Character> list = new ArrayList<>();
        for (char c : chars) {
            list.add(c);
        }
        Collections.shuffle(list);
        for (int i = 0; i < list.size(); i++) {
            chars[i] = list.get(i);
        }
        return chars;
    }



    private void setClickTextview(TextView textview) {
        textview.setOnClickListener(view1 -> {
            String result = vocabularies.get(start-1).getTienganh();
            if (textview.getText().toString().equals(result)) {
                numberTrue++;
                textview.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
            } else {
                textview.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                showAnswerTrue(result);
            }
            disableTextview();
            playAudio.playAudio(result);
            new Handler().postDelayed(() -> StartQuiz(),750);
        });
    }

    private void showAnswerTrue(String result) {
        if (english1.getText().toString().equals(result)){
            english1.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
        } else if (english2.getText().toString().equals(result)){
            english2.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
        } else if (english3.getText().toString().equals(result)){
            english4.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
        } else if (english4.getText().toString().equals(result)){
            english4.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
        }
    }

    public void setButton() {
        english1.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_button));
        english2.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_button));
        english3.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_button));
        english4.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_button));
        english1.setEnabled(true);
        english2.setEnabled(true);
        english3.setEnabled(true);
        english4.setEnabled(true);
    }
    private void randomLayout(View view1,View view2){
        Random random = new Random();
        if(random.nextInt(2) == 0){
            view1.setVisibility(View.VISIBLE);
            view2.setVisibility(View.GONE);
        }
        else {
            view2.setVisibility(View.VISIBLE);
            view1.setVisibility(View.GONE);
        }
    }
    private void disableTextview(){
        english1.setEnabled(false);
        english2.setEnabled(false);
        english3.setEnabled(false);
        english4.setEnabled(false);
    }
}
