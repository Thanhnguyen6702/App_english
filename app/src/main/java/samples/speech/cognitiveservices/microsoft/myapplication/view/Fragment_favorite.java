package samples.speech.cognitiveservices.microsoft.myapplication.view;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.microsoft.cognitiveservices.speech.CancellationDetails;
import com.microsoft.cognitiveservices.speech.KeywordRecognitionModel;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentConfig;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGradingSystem;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentGranularity;
import com.microsoft.cognitiveservices.speech.PronunciationAssessmentResult;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Key_API;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.MicrophoneStream;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Voice_to_text;

public class Fragment_favorite extends Fragment {
    View view;
    Button voice, mic;
    TextView textView;
    Voice_to_text voice_to_text = new Voice_to_text();
    ProgressBar progressBar;
    EditText editText;
    private MicrophoneStream microphoneStream;

    private MicrophoneStream createMicrophoneStream() {
        this.releaseMicrophoneStream();
        microphoneStream = new MicrophoneStream();
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

        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        voice = view.findViewById(R.id.voice);
        mic = view.findViewById(R.id.mic_assessment);
        textView = view.findViewById(R.id.textVoice);
        editText = view.findViewById(R.id.text_mic);
        progressBar = view.findViewById(R.id.progress_ring);
        progressBar.setProgress(10);

        try {
            // a unique number within the application to allow
            // correlating permission request responses with the request.
            int permissionRequestId = 5;

            // Request permissions needed for speech recognition
            ActivityCompat.requestPermissions(getActivity(), new String[]{RECORD_AUDIO, INTERNET, READ_EXTERNAL_STORAGE}, permissionRequestId);
        } catch (Exception ex) {
            Log.e("SpeechSDK", "could not init sdk, " + ex);
            textView.setText("Could not initialize: " + ex);
        }

        // create config
        final SpeechConfig speechConfig;
        final KeywordRecognitionModel kwsModel;
        try {
            speechConfig = SpeechConfig.fromSubscription(Key_API.SpeechSubscriptionKey, Key_API.SpeechRegion);
            kwsModel = KeywordRecognitionModel.fromFile(copyAssetToCacheAndGetFilePath(Key_API.KwsModelFile));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            displayException(ex);
            return view;
        }
        voice.setOnClickListener(view -> {
            clearTextBox();

            try {
                // In general, if the device default microphone is used then it is enough
                // to either have AudioConfig.fromDefaultMicrophoneInput or omit the audio
                // config altogether.
                // AudioConfig.fromStreamInput is specifically needed if you want to use an
                // external microphone (including Bluetooth that couldn't be otherwise used)
                // or mix audio from some other source to microphone audio.
                final AudioConfig audioInput = AudioConfig.fromStreamInput(voice_to_text.createMicrophoneStream());
                final SpeechRecognizer reco = new SpeechRecognizer(speechConfig, audioInput);

                final Future<SpeechRecognitionResult> task = reco.recognizeOnceAsync();
                setOnTaskCompletedListener(task, result -> {
                    String s = result.getText();
                    if (result.getReason() != ResultReason.RecognizedSpeech) {
                        String errorDetails = (result.getReason() == ResultReason.Canceled) ? CancellationDetails.fromResult(result).getErrorDetails() : "";
                        s = "Recognition failed with " + result.getReason() + ". Did you enter your subscription?" + System.lineSeparator() + errorDetails;
                    }

                    reco.close();
                    setRecognizedText(s);
                });
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                displayException(ex);
            }

        });
        mic.setOnClickListener(view1 -> {
            final String logTag = "pron";

            if (this.microphoneStream != null) {
                /*
                 The pronunciation assessment service has a longer default end silence timeout (5 seconds) than normal STT
                 as the pronunciation assessment is widely used in education scenario where kids have longer break in reading.
                 You can explicitly stop the recording stream to stop assessment and the service will return the results immediately
                 */
                this.releaseMicrophoneStream();
                return;
            }

            this.mic.setText("Stop recording");
            clearTextBox();
            try {
                createMicrophoneStream();
                final AudioConfig audioConfig = AudioConfig.fromStreamInput(createMicrophoneStream());
                final SpeechRecognizer reco = new SpeechRecognizer(speechConfig, audioConfig);

                // Replace this referenceText to match your input.
                String referenceText = editText.getText().toString();
                PronunciationAssessmentConfig pronConfig =
                        new PronunciationAssessmentConfig(referenceText,
                                PronunciationAssessmentGradingSystem.HundredMark,
                                PronunciationAssessmentGranularity.Phoneme);

                pronConfig.applyTo(reco);

                reco.recognized.addEventListener((o, speechRecognitionResultEventArgs) -> {
                    final String s = speechRecognitionResultEventArgs.getResult().getText();
                    AppendTextLine("Final result received: " + s, true);
                    PronunciationAssessmentResult pronResult = PronunciationAssessmentResult.fromResult(speechRecognitionResultEventArgs.getResult());
                    AppendTextLine("Accuracy score: " + pronResult.getAccuracyScore() +
                            ";  pronunciation score: " + pronResult.getPronunciationScore() +
                            ", completeness score: " + pronResult.getCompletenessScore() +
                            ", fluency score: " + pronResult.getFluencyScore(), false);
                });

                reco.sessionStopped.addEventListener((o, s) -> {
                    Log.i(logTag, "Session stopped.");
                    reco.stopContinuousRecognitionAsync();

                    this.releaseMicrophoneStream();
                    mic.setEnabled(true);
                    this.mic.setText("Pronunciation Assessment From Stream");
                });

                reco.recognizeOnceAsync();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                displayException(ex);
            }
        });
        return view;
    }

    private String copyAssetToCacheAndGetFilePath(String filename) {
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

    private void displayException(Exception ex) {
        textView.setText(ex.getMessage() + System.lineSeparator() + TextUtils.join(System.lineSeparator(), ex.getStackTrace()));
    }

    private void clearTextBox() {
        AppendTextLine("", true);
    }

    private void setRecognizedText(final String s) {
        AppendTextLine(s, true);
    }

    private void AppendTextLine(final String s, final Boolean erase) {
        getActivity().runOnUiThread(() -> {
            if (erase) {
                textView.setText(s);
            } else {
                String txt = textView.getText().toString();
                textView.setText(txt + System.lineSeparator() + s);
            }
        });
    }

    private <T> void setOnTaskCompletedListener(Future<T> task, OnTaskCompletedListener<T> listener) {
        s_executorService.submit(() -> {
            T result = task.get();
            listener.onCompleted(result);
            return null;
        });
    }

    private interface OnTaskCompletedListener<T> {
        void onCompleted(T taskResult);
    }

    private static final ExecutorService s_executorService;

    static {
        s_executorService = Executors.newCachedThreadPool();
    }

}
