package samples.speech.cognitiveservices.microsoft.myapplication.Speech;

import android.util.Log;

import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;

public class Text_to_voice {
    private SpeechConfig speechConfig;
    private SpeechSynthesizer synthesizer;
    public void setConfig(){
        speechConfig = SpeechConfig.fromSubscription(Key_API.SpeechSubscriptionKey, Key_API.SpeechRegion);
        speechConfig.setSpeechSynthesisVoiceName("en-US-JennyNeural");
        synthesizer = new SpeechSynthesizer(speechConfig);
    }
    public Text_to_voice(){
        setConfig();
    }
    public void voice(String text) {
        try {
            SpeechSynthesisResult result = synthesizer.SpeakText(text);
            assert (result != null);
            result.close();
        } catch (Exception ex) {
            Log.e("SpeechSDKDemo", "unexpected " + ex.getMessage());
            assert (false);
        }
    }

}
