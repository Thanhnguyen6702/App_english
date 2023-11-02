package samples.speech.cognitiveservices.microsoft.myapplication.CallAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Example {
    private List<String> example;
    private String response;
    public Example(List<String> example) {
        this.example = example;
    }

    public List<String> getExample() {
        return example;
    }

    public void setExample(List<String> example) {
        this.example = example;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
