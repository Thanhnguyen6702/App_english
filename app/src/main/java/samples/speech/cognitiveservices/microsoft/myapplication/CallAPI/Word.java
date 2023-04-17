package samples.speech.cognitiveservices.microsoft.myapplication.CallAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Word {
    @SerializedName("word")
    private String Word;
    @SerializedName("phonetic")
    private String Phonetic;
    @SerializedName("phonetics")
    private List<Phonetics> phonetics;
    public class Phonetics{
        private String Text;
        private String audio;

        public String getText() {
            return Text;
        }

        public void setText(String text) {
            Text = text;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }
    }
    @SerializedName("meanings")
    private List<Meaning> meaning;
    public class Meaning{
        @SerializedName("partOfSpeech")
        private String PartOfSpeech;
        @SerializedName("definitions")
        private List<Definition> definitions;

        public String getPartOfSpeech() {
            return PartOfSpeech;
        }

        public void setPartOfSpeech(String partOfSpeech) {
            PartOfSpeech = partOfSpeech;
        }

        public List<Definition> getDefinitions() {
            return definitions;
        }

        public void setDefinitions(List<Definition> definitions) {
            this.definitions = definitions;
        }

        public class Definition{
            @SerializedName("definition")
            private String Definition;
            @SerializedName("example")
            private String Example;

            public String getDefinition() {
                return Definition;
            }

            public void setDefinition(String definition) {
                Definition = definition;
            }

            public String getExample() {
                return Example;
            }

            public void setExample(String example) {
                Example = example;
            }
        }
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public String getPhonetic() {
        return Phonetic;
    }

    public void setPhonetic(String phonetic) {
        Phonetic = phonetic;
    }

    public List<Phonetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetics> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Meaning> getMeaning() {
        return meaning;
    }

    public void setMeaning(List<Meaning> meaning) {
        this.meaning = meaning;
    }
}
