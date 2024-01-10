package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Sound;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;


public class Fragment_Challenge extends Fragment {
    private List<Vocabulary> vocabularies;
    TextView english, phonetic, english1, english2, english3, english4, number;
    ProgressBar progressBar;
    ImageView sound;
    private Sound playAudio;
    View view, viewInclude;
    int start = 0;
    CountDownTimer countDownTimer;
    int totalHeart = 3;
    Handler handler;
    boolean isSound;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenge, container, false);
        ImageView back = view.findViewById(R.id.back_challenge);
        back.setOnClickListener(view1 -> Navigation.findNavController(view).popBackStack());
        handler = new Handler();
        ShareViewModel shareViewModel = ((MainActivity) requireActivity()).getData_login();
        vocabularies = shareViewModel.getVocabulary().getValue().second;
        playAudio = Sound.getInstance(requireContext());
        initLayout();
        StartQuiz();
        return view;
    }

    private void initLayout() {
        viewInclude = view.findViewById(R.id.item_heart_include);
        progressBar = view.findViewById(R.id.progress_challenge);
        phonetic = view.findViewById(R.id.phonetic_challenge);
        sound = view.findViewById(R.id.img_challenge);
        number = view.findViewById(R.id.number_challenge);
        english = view.findViewById(R.id.english_challenge);
        english1 = view.findViewById(R.id.english1_challenge);
        english2 = view.findViewById(R.id.english2_challenge);
        english3 = view.findViewById(R.id.english3_challenge);
        english4 = view.findViewById(R.id.english4_challenge);
        setClickTextview(english1);
        setClickTextview(english2);
        setClickTextview(english3);
        setClickTextview(english4);
    }

    public void disableImageview(View view) {
        handler.postDelayed(() -> view.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_disable)), 500);
    }

    private void setClickTextview(TextView textview) {
        textview.setOnClickListener(view1 -> {
            String resultVietnamese = vocabularies.get(start - 1).getTiengviet();
            String resultEnglish = vocabularies.get(start - 1).getTienganh();
            if (textview.getText().toString().equals(resultEnglish) | textview.getText().toString().equals(resultVietnamese)) {
                textview.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                disableTextview();
                playAudio.playAudio(resultEnglish);
                countDownTimer.cancel();
                handler.postDelayed(() -> StartQuiz(), 750);
            } else {
                textview.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                disableImageview(view1);
                textview.setEnabled(false);
                setHeart();
            }

        });
    }

    private void StartQuiz() {
        if (start < vocabularies.size()) {
            number.setText((start + 1) + "/" + vocabularies.size());
            setButton();
            randomLayout();
            startCountDownTimer();
            // Tạo mảng trộn các đáp án
            List<String> result = new ArrayList<>();
            List<Vocabulary> vocabularyRandom = new ArrayList<>(vocabularies);
            String textVietnamese = vocabularies.get(start).getTiengviet();
            String textEnglish = vocabularies.get(start).getTienganh();
            String textPhonetic = vocabularies.get(start).getPhienam();
            sound.setOnClickListener(view -> playAudio.playAudio(textEnglish));
            vocabularyRandom.remove(vocabularies.get(start));
            Collections.shuffle(vocabularyRandom);
            if (isSound) {
                playAudio.playAudio(textEnglish);
                result.add(textEnglish);
                result.add(vocabularyRandom.get(0).getTienganh());
                result.add(vocabularyRandom.get(1).getTienganh());
                result.add(vocabularyRandom.get(2).getTienganh());
            } else {
                playAudio.setWord(textEnglish);
                result.add(textVietnamese);
                result.add(vocabularyRandom.get(0).getTiengviet());
                result.add(vocabularyRandom.get(1).getTiengviet());
                result.add(vocabularyRandom.get(2).getTiengviet());
            }
            Collections.shuffle(result);
            english1.setText(result.get(0));
            english2.setText(result.get(1));
            english3.setText(result.get(2));
            english4.setText(result.get(3));
            english.setText(textEnglish);
            phonetic.setText(textPhonetic);
            start++;
        } else {
            openResultDialog(true);
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

    private void randomLayout() {
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            isSound = true;
            english.setVisibility(View.GONE);
            phonetic.setVisibility(View.GONE);
            sound.setVisibility(View.VISIBLE);
        } else {
            isSound = false;
            english.setVisibility(View.VISIBLE);
            phonetic.setVisibility(View.VISIBLE);
            sound.setVisibility(View.GONE);
        }
    }

    public void startCountDownTimer() {
        long totalTime = 10000;
        long stepTime = 200;
        countDownTimer = new CountDownTimer(totalTime, stepTime) {
            @Override
            public void onTick(long l) {
                progressBar.setProgress((int) (l * 100 / totalTime));
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(0);
                setHeart();
                if (totalHeart >= 0) {
                    startCountDownTimer();
                }
            }
        };
        countDownTimer.start();
    }

    private void setHeart() {
        totalHeart--;
        if (totalHeart == 2) {
            ((ImageView) viewInclude.findViewById(R.id.heart3)).setImageResource(R.drawable.heart_false);
        } else if (totalHeart == 1) {
            ((ImageView) viewInclude.findViewById(R.id.heart2)).setImageResource(R.drawable.heart_false);
        } else if (totalHeart == 0) {
            ((ImageView) viewInclude.findViewById(R.id.heart1)).setImageResource(R.drawable.heart_false);
        } else {
//            Toast.makeText(requireContext(), "Thử thách thất bại", Toast.LENGTH_SHORT).show();
//            Navigation.findNavController(view).popBackStack();
            openResultDialog(false);
            cancelHandle();
        }
    }

    private void disableTextview() {
        english1.setEnabled(false);
        english2.setEnabled(false);
        english3.setEnabled(false);
        english4.setEnabled(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelHandle();
    }

    private void openResultDialog(boolean result) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!result) {
            dialog.setContentView(R.layout.result_challenge_failed);
        } else {
            dialog.setContentView(R.layout.result_challenge_passed);
        }
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAtribuites = window.getAttributes();
        windowAtribuites.gravity = Gravity.CENTER;
        window.setAttributes(windowAtribuites);
        Button replay = dialog.findViewById(R.id.replay_resultchallenge);
        Button cacel = dialog.findViewById(R.id.cancel_resultchallenge);
        replay.setOnClickListener(view1 -> {
            dialog.dismiss();
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
            navController.navigate(R.id.fragment_Challenge);
        });
        cacel.setOnClickListener(view1 -> {
            dialog.dismiss();
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void cancelHandle() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
