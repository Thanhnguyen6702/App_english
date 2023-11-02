package samples.speech.cognitiveservices.microsoft.myapplication.CallAPI;

import java.util.List;

public class Vocabulary extends Value {
    private String PhienAm;
    private List<String> Example;

    public List<String> getExample() {
        return Example;
    }

    public void setExample(List<String> example) {
        Example = example;
    }
    public String getPhienam() {
        return PhienAm;
    }

    public void setPhienam(String phienam) {
        PhienAm = phienam;
    }
}
