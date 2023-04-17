package samples.speech.cognitiveservices.microsoft.myapplication.Speech;

public class Voice_to_text {
    private MicrophoneStream microphoneStream;

    public MicrophoneStream createMicrophoneStream() {
        this.releaseMicrophoneStream();
        microphoneStream = new MicrophoneStream();
        return microphoneStream;
    }

    public void releaseMicrophoneStream() {
        if (microphoneStream != null) {
            microphoneStream.close();
            microphoneStream = null;
        }
    }


}
