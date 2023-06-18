package samples.speech.cognitiveservices.microsoft.myapplication.Speech;

import android.util.Log;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

public class Text_to_voice {
    private SpeechConfig speechConfig;
    private SpeechSynthesizer synthesizer;

    public Text_to_voice() {
        setConfig();
    }

    public void voice(String text, String locale) {
        try {
            speechConfig.setSpeechSynthesisVoiceName(getVoiceName(locale));
            SpeechSynthesizer synthesizer = new SpeechSynthesizer(speechConfig);
            SpeechSynthesisResult result = synthesizer.SpeakText(text);
            assert (result != null);
            result.close();
        } catch (Exception ex) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.getMessage());
            assert (false);
        }
    }

    private void setConfig() {
        speechConfig = SpeechConfig.fromSubscription(Key_API.SpeechSubscriptionKey, Key_API.SpeechRegion);
    }

    private String getVoiceName(String locale) {
        switch (locale) {
            case "en-US":
                return "en-US-JennyNeural";
            case "vi-VN":
                return "vi-VN-HoaiMyNeural";
            default:
                throw new IllegalArgumentException("Unsupported locale: " + locale);
        }
    }
}