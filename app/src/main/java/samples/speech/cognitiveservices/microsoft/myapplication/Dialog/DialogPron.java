package samples.speech.cognitiveservices.microsoft.myapplication.Dialog;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Key_API;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.MicrophoneStream;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Text_to_voice;

public class DialogPron extends Dialog {
    Activity activity;
    Animation top, bottom;
    ConnectivityManager connectivityManager;
    NetworkRequest networkRequest;
    ConnectivityManager.NetworkCallback networkCallback;
    String filePath, english, pronoun;
    TextView textboy, tienganh, phienam;
    ImageView mic;
    Text_to_voice text_to_voice = new Text_to_voice();
    SpeechConfig speechConfig;
    boolean isNetwork = false;
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

    public DialogPron(@NonNull Context context) {
        super(context);
        activity = (Activity) context;
    }

    public void setData(String enlish, String pronoun) {
        this.english = enlish;
        this.pronoun = pronoun;
    }

    @Override
    public void dismiss() {
        connectivityManager.unregisterNetworkCallback(networkCallback);
        super.dismiss();

    }

    @Override
    public void show() {
        connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkRequest = new NetworkRequest.Builder().build();
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                isNetwork = true;
            }

            @Override
            public void onLost(@NonNull Network network) {
                isNetwork = false;
                activity.runOnUiThread(() -> stoprecord());
                Toast.makeText(getContext(), "Không có kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        };
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        super.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_pron);
        mic = findViewById(R.id.mic);
        textboy = findViewById(R.id.textBoy);
        tienganh = findViewById(R.id.english);
        phienam = findViewById(R.id.pronoun);
        tienganh.setText(english);
        phienam.setText(pronoun);
        batdau();
        filePath = getContext().getExternalFilesDir(null).getAbsolutePath() + "/record.wav";
        speechConfig = SpeechConfig.fromSubscription(Key_API.SpeechSubscriptionKey, Key_API.SpeechRegion);
        try {
            // a unique number within the application to allow
            // correlating permission request responses with the request.
            int permissionRequestId = 5;
            // Request permissions needed for speech recognition
            ActivityCompat.requestPermissions(activity, new String[]{RECORD_AUDIO, INTERNET, READ_EXTERNAL_STORAGE}, permissionRequestId);
        } catch (Exception ex) {
        }

        mic.setOnClickListener(view1 -> {
            if (isNetwork) {
                activity.runOnUiThread(() -> record());
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
        activity.runOnUiThread(() -> {
            SpannableString spantext = new SpannableString("Nhấn vào biểu tượng  để bắt đầu!");
            Drawable draw = ContextCompat.getDrawable(getContext(), R.drawable.mic);
            draw.setBounds(0, 0, (int) textboy.getTextSize(), (int) textboy.getTextSize());
            ImageSpan imageSpan = new ImageSpan(draw, ImageSpan.ALIGN_BASELINE);
            spantext.setSpan(imageSpan, 19, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textboy.setText(spantext);
        });
    }

    public void dangnghe() {
        activity.runOnUiThread(() -> {
            textboy.setText("Mình đang nghe ...");
        });
    }

    public void nghexong() {
        activity.runOnUiThread(() -> {
            SpannableString spantext = new SpannableString("Đợi mình chút  ");
            try {
                GifDrawable gifDrawable = new GifDrawable(getContext().getResources(), R.drawable.ellipsis);
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
            GifDrawable gifDrawable = new GifDrawable(getContext().getResources(), R.drawable.sound);
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
            String referenceText = english.trim();
            int count = countText(english);
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
                        if (english.equalsIgnoreCase(pronResult.getWords().get(i).getWord())) {
                            score_tienganh = pronResult.getWords().get(i).getAccuracyScore();
                        }
                        if (score >= 75) {
                            spannableString = concatText(spannableString, paintText(pronResult.getWords().get(i).getWord(), ContextCompat.getColor(getContext(), R.color.green)));
                        } else if (score >= 30) {

                            spannableString = concatText(spannableString, paintText(pronResult.getWords().get(i).getWord(), ContextCompat.getColor(getContext(), R.color.yellow)));
                        } else {

                            spannableString = concatText(spannableString, paintText(pronResult.getWords().get(i).getWord(), ContextCompat.getColor(getContext(), R.color.red)));
                        }
                    }
                    SpannableStringBuilder finalSpannableString = spannableString;
                    double score_tienganh1 = score_tienganh;
                    activity.runOnUiThread(() -> {
                        ketqua(score_tienganh1);
                        // this.mic.setImageResource(R.drawable.mic);
                        tienganh.setText(finalSpannableString);
                    });
                } else {
                    activity.runOnUiThread(() -> {
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
                activity.runOnUiThread(() -> {
                    this.mic.setImageResource(R.drawable.mic);
                    mic.setEnabled(true);
                });
            });

            reco.recognizeOnceAsync();
        } catch (Exception ex) {

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


}
