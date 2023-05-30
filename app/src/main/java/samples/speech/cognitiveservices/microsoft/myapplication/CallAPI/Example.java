package samples.speech.cognitiveservices.microsoft.myapplication.CallAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Example {
    @SerializedName("example")
    private List<String> example;
    public Example(List<String> example) {
        this.example = example;
    }

    public List<String> getExample() {
        return example;
    }

    public void setExample(List<String> example) {
        this.example = example;
    }
}
