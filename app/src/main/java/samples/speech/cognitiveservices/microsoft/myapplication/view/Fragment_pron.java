package samples.speech.cognitiveservices.microsoft.myapplication.view;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGradingSystem;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGranularity;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentResult;
import com.microsoft.cognitiveservices.speech.PropertyId;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.audio.AudioProcessingConstants;
import com.microsoft.cognitiveservices.speech.audio.AudioProcessingOptions;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pl.droidsonroids.gif.GifDrawable;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Example;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Key_API;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.MicrophoneStream;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Text_to_voice;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_pron extends Fragment {
    View view;
    AudioTrack audioTrack;
    String filePath;
    ImageView mic, volume, close, head_phone;
    Button next, phatamcumtu;
    TextView tienganh, phienam, tiengviet, textboy;
    //Voice_to_text voice_to_text = new Voice_to_text();
    Text_to_voice text_to_voice = new Text_to_voice();
    ShareViewModel shareViewModel;
    List<Vocabulary> vocabularies = new ArrayList<>();
    String Text, tieng_anh;
    int start = 0;
    boolean isNetwork = false;
    SpeechConfig speechConfig;
    private MicrophoneStream microphoneStream;

    private MicrophoneStream createMicrophoneStream() {
        this.releaseMicrophoneStream();
        microphoneStream = new MicrophoneStream(filePath);
        return microphoneStream;
    }

    private void releaseMicrophoneStream() {
        if (microphoneStream != null) {
            microphoneStream.close();
            microphoneStream = null;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pron, container, false);
        filePath = getContext().getExternalFilesDir(null).getAbsolutePath() + "/record.wav";
        // create config
        speechConfig = SpeechConfig.fromSubscription(Key_API.SpeechSubscriptionKey, Key_API.SpeechRegion);
        // kwsModel = KeywordRecognitionModel.fromFile(copyAssetToCacheAndGetFilePath(Key_API.KwsModelFile));
        mic = view.findViewById(R.id.mic_assess);
        next = view.findViewById(R.id.next_pron);
        close = view.findViewById(R.id.close_pronun);
        close.setOnClickListener(view1 -> {
            close_fragment();
        });
        volume = view.findViewById(R.id.volume_assess);
        tienganh = view.findViewById(R.id.tienganh_assess);
        phienam = view.findViewById(R.id.phienam_assess);
        tiengviet = view.findViewById(R.id.tiengviet_assess);
        textboy = view.findViewById(R.id.textBoy);
        phatamcumtu = view.findViewById(R.id.phatamcumtu);
        head_phone = view.findViewById(R.id.head_phone);
        disableHeadphone();
        Drawable draw_back = ContextCompat.getDrawable(requireContext(), R.drawable.back_teal200);
        draw_back.setBounds(0, 0, draw_back.getIntrinsicWidth(), draw_back.getIntrinsicHeight());
        Drawable draw_next = ContextCompat.getDrawable(requireContext(), R.drawable.next);
        draw_next.setBounds(0, 0, draw_next.getIntrinsicWidth(), draw_next.getIntrinsicHeight());
        batdau();
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        getData(shareViewModel.getData().getValue(), shareViewModel.getTopicphatam().getValue());
        shareViewModel.getchuahoc_phatam().observe(getViewLifecycleOwner(), listVovab -> {
            tienganh.setText(listVovab.get(start).getTienganh());
            tiengviet.setText(listVovab.get(start).getTiengviet());
            phienam.setText(listVovab.get(start).getPhienam());
            Text = tienganh.getText().toString();
            tieng_anh = tienganh.getText().toString();
            getExample(Text);

        });
        try {
            // a unique number within the application to allow
            // correlating permission request responses with the request.
            int permissionRequestId = 5;
            // Request permissions needed for speech recognition
            ActivityCompat.requestPermissions(requireActivity(), new String[]{RECORD_AUDIO, INTERNET, READ_EXTERNAL_STORAGE}, permissionRequestId);
        } catch (Exception ex) {
            Log.e("SpeechSDK", "could not init sdk, " + ex);
        }


        volume.setOnClickListener(view1 -> {
            text_to_voice.voice(tienganh.getText().toString(), "en-US");
        });
        next.setOnClickListener(view1 -> {
            disableHeadphone();
            if (start % 2 == 0) {
                start += 2;
            } else {
                start += 1;
            }
            batdau();
            next.setEnabled(false);
            if (start >= vocabularies.size() * 2) {
                close_fragment();
                Toast.makeText(requireContext(), "Bạn đã hoàn thành xong chủ đề này!", Toast.LENGTH_SHORT).show();
            } else {
                phatamcumtu.setText("Phát âm cụm từ ");
                phatamcumtu.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, draw_next, null);
                tienganh.setText(vocabularies.get(start / 2).getTienganh());
                tiengviet.setText(vocabularies.get(start / 2).getTiengviet());
                phienam.setText(vocabularies.get(start / 2).getPhienam());
                Text = tienganh.getText().toString();
                tieng_anh = tienganh.getText().toString();
                tiengviet.setVisibility(View.VISIBLE);
                phienam.setVisibility(View.VISIBLE);
                getExample(Text);
            }
        });
        phatamcumtu.setOnClickListener(view1 -> {
            disableHeadphone();
            if (start % 2 == 0) {
                start += 1;
                phatamcumtu.setText(" Phát âm từ");
                phatamcumtu.setCompoundDrawablesRelativeWithIntrinsicBounds(draw_back, null, null, null);
                tienganh.setText(Text);
                tiengviet.setVisibility(View.GONE);
                phienam.setVisibility(View.GONE);
            } else {
                start = start - 1;
                phatamcumtu.setText("Phát âm cụm từ ");
                phatamcumtu.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, draw_next, null);
                tienganh.setText(vocabularies.get(start / 2).getTienganh());
                tiengviet.setText(vocabularies.get(start / 2).getTiengviet());
                phienam.setText(vocabularies.get(start / 2).getPhienam());
                Text = tienganh.getText().toString();
                tieng_anh = tienganh.getText().toString();
                tiengviet.setVisibility(View.VISIBLE);
                phienam.setVisibility(View.VISIBLE);
                getExample(Text);
            }
        });
        head_phone.setOnClickListener(view1 -> {
            playAudio(filePath);
        });
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest networkRequest = new NetworkRequest.Builder().build();
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                isNetwork = true;
            }

            @Override
            public void onLost(@NonNull Network network) {
                isNetwork = false;
                getActivity().runOnUiThread(() -> stoprecord());
                Toast.makeText(getContext(), "Không có kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        };
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        mic.setOnClickListener(view1 -> {
            if(isNetwork){
                getActivity().runOnUiThread(() -> record());
            }
        });

        return view;
    }

    /* private String copyAssetToCacheAndGetFilePath(String filename) {
         File cacheFile = new File(getActivity().getCacheDir() + "/" + filename);
         if (!cacheFile.exists()) {
             try {
                 InputStream is = getActivity().getAssets().open(filename);
                 int size = is.available();
                 byte[] buffer = new byte[size];
                 is.read(buffer);
                 is.close();
                 FileOutputStream fos = new FileOutputStream(cacheFile);
                 fos.write(buffer);
                 fos.close();
             } catch (Exception e) {
                 throw new RuntimeException(e);
             }
         }
         return cacheFile.getPath();
     }
 */
    public void getExample(String word) {
        ApiService.apiService.getExample(word).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Example>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Example example) {
                if (example.getExample() != null && !example.getExample().isEmpty() && example.getResponse().equals(word)) {
                    phatamcumtu.setVisibility(View.VISIBLE);
                    Text = example.getExample().get(0);
                } else {
                    phatamcumtu.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        });
    }

    public int countText(String text) {
        text = text.trim();
        return text.split("\\s+").length;
    }

    public SpannableString paintText(String text, @ColorInt int id) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(id), 0, spannableString.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public SpannableStringBuilder concatText(SpannableStringBuilder span1, SpannableString span2) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(span1);
        builder.append(" ");
        builder.append(span2);
        return builder;
    }

    public void batdau() {
        getActivity().runOnUiThread(() -> {
            SpannableString spantext = new SpannableString("Nhấn vào biểu tượng  để bắt đầu!");
            Drawable draw = ContextCompat.getDrawable(requireContext(), R.drawable.mic);
            draw.setBounds(0, 0, (int) textboy.getTextSize(), (int) textboy.getTextSize());
            ImageSpan imageSpan = new ImageSpan(draw, ImageSpan.ALIGN_BASELINE);
            spantext.setSpan(imageSpan, 19, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textboy.setText(spantext);
        });
    }

    public void dangnghe() {
        getActivity().runOnUiThread(() -> {
            textboy.setText("Mình đang nghe ...");
        });
    }

    public void nghexong() {
        getActivity().runOnUiThread(() -> {
            SpannableString spantext = new SpannableString("Đợi mình chút  ");
            try {
                GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.ellipsis);
                gifDrawable.setBounds(0, 0, (int) textboy.getTextSize(), (int) textboy.getTextSize());
                ImageSpan imageSpan = new ImageSpan(gifDrawable);
                spantext.setSpan(imageSpan, spantext.toString().length() - 1, spantext.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textboy.setText(spantext);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void ketqua(double score) {
        enableHeadphone();
        if (score > 75) {
            textboy.setText("Xuất sắc! điểm của bạn là " + (int) score);
        } else if (score > 30) {
            textboy.setText("Khá ổn! điểm của bạn là " + (int) score);
        } else textboy.setText("Hơi tệ! điểm của bạn là " + (int) score);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text_to_voice.voice(textboy.getText().toString(), "vi-VN");
            }
        }, 100);
    }

    public void record() {
        Handler handler = new Handler();
        Runnable runnable = () -> mic.performClick();
        if (this.microphoneStream != null) {
            nghexong();
            handler.removeCallbacks(runnable);
            this.mic.setImageResource(R.drawable.mic);
            mic.setEnabled(false);
            this.releaseMicrophoneStream();
            return;
        }
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.sound);
            this.mic.setImageDrawable(gifDrawable);
            dangnghe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            createMicrophoneStream();
            AudioProcessingOptions audioProcessingOptions = AudioProcessingOptions.create(
                    AudioProcessingConstants.AUDIO_INPUT_PROCESSING_DISABLE_ECHO_CANCELLATION |
                            AudioProcessingConstants.AUDIO_INPUT_PROCESSING_DISABLE_NOISE_SUPPRESSION
            );
            AudioConfig audioConfig = AudioConfig.fromStreamInput(createMicrophoneStream(), audioProcessingOptions);
            audioConfig.setProperty(PropertyId.SpeechServiceConnection_EndSilenceTimeoutMs, "1500");
            audioConfig.setProperty(PropertyId.SpeechServiceConnection_InitialSilenceTimeoutMs, "2000");
            final SpeechRecognizer reco = new SpeechRecognizer(speechConfig, audioConfig);
            // Replace this referenceText to match your input.
            String referenceText = tienganh.getText().toString().trim();
            int count = countText(tienganh.getText().toString());
            handler.postDelayed(runnable, 2000 + count * 1000L);
            PronunciationAssessmentConfig pronConfig =
                    new PronunciationAssessmentConfig(referenceText,
                            PronunciationAssessmentGradingSystem.HundredMark,
                            PronunciationAssessmentGranularity.Phoneme);

            pronConfig.applyTo(reco);
            reco.recognized.addEventListener((o, speechRecognitionResultEventArgs) -> {
                PronunciationAssessmentResult pronResult = PronunciationAssessmentResult.fromResult(speechRecognitionResultEventArgs.getResult());
                SpannableStringBuilder spannableString = new SpannableStringBuilder();
                double score_tienganh = 0;
                if (pronResult != null) {
                    for (int i = 0; i < count; i++) {
                        double score = pronResult.getWords().get(i).getAccuracyScore();
                        if (count > 1) {
                            score_tienganh = pronResult.getPronunciationScore();
                        }
                        if (tieng_anh.equalsIgnoreCase(pronResult.getWords().get(i).getWord())) {
                            score_tienganh = pronResult.getWords().get(i).getAccuracyScore();
                        }
                        if (score >= 75) {
                            spannableString = concatText(spannableString, paintText(pronResult.getWords().get(i).getWord(), ContextCompat.getColor(requireContext(), R.color.green)));
                        } else if (score >= 30) {

                            spannableString = concatText(spannableString, paintText(pronResult.getWords().get(i).getWord(), ContextCompat.getColor(requireContext(), R.color.yellow)));
                        } else {

                            spannableString = concatText(spannableString, paintText(pronResult.getWords().get(i).getWord(), ContextCompat.getColor(requireContext(), R.color.red)));
                        }
                    }
                    SpannableStringBuilder finalSpannableString = spannableString;
                    double score_tienganh1 = score_tienganh;
                    getActivity().runOnUiThread(() -> {
                        ketqua(score_tienganh1);
                        // this.mic.setImageResource(R.drawable.mic);
                        tienganh.setText(finalSpannableString);
                        postScore(shareViewModel.getData().getValue(), tieng_anh, (int) score_tienganh1);
                    });
                } else {
                    getActivity().runOnUiThread(() -> {
                        textboy.setText("Vui lòng phát âm lại!");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                text_to_voice.voice("Vui lòng phát âm lại!", "vi-VN");
                            }
                        }, 100);
                    });

                }

            });
            reco.sessionStopped.addEventListener((o, s) -> {
                handler.removeCallbacks(runnable);
                reco.stopContinuousRecognitionAsync();
                this.releaseMicrophoneStream();
                getActivity().runOnUiThread(() -> {
                    next.setEnabled(true);
                    this.mic.setImageResource(R.drawable.mic);
                    mic.setEnabled(true);
                });
            });

            reco.recognizeOnceAsync();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
    }

    public void stoprecord() {
        if (this.microphoneStream != null) {
            batdau();
            mic.setEnabled(true);
            this.mic.setImageResource(R.drawable.mic);
            this.releaseMicrophoneStream();
        }
    }

    public void getData(String account, String topic) {
        Dialog dialog = new Dialog(requireContext());
        ApiService.apiService.getStudy_topic_phatam(account, topic).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Vocabulary>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.dialog_login);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Vocabulary> vocabularies1) {
                vocabularies = vocabularies1;
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                shareViewModel.setShare_chuahoc_phatam(vocabularies);
                dialog.dismiss();
            }
        });
    }

    public void postScore(String account, String word, int score) {
        ApiService.apiService.postScore(account, word, score).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Void>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Void unused) {

            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void close_fragment() {
        shareViewModel.setShare_sotuphatam(null);
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_fragment_voice_to_framgent_phatam);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_bottom);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void disableHeadphone() {
        head_phone.setEnabled(false);
        head_phone.setColorFilter(R.color.grey);
    }

    public void enableHeadphone() {
        head_phone.setEnabled(true);
        head_phone.clearColorFilter();
    }

    private void playAudio(String filePath) {
        try {
            File file = new File(filePath);
            int bufferSize = 8192;
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);

            byte[] buffer = new byte[bufferSize];
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));

            audioTrack.play();

            while (dataInputStream.available() > 0) {
                int bytesRead = dataInputStream.read(buffer, 0, buffer.length);
                if (bytesRead != AudioTrack.ERROR_INVALID_OPERATION) {
                    audioTrack.write(buffer, 0, bytesRead);
                }
            }

            audioTrack.stop();
            audioTrack.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
