package samples.speech.cognitiveservices.microsoft.myapplication.CallAPI;

import java.util.List;

public class Definition {
    private Meaning meaning;
    public Meaning getMeaning() {
        return meaning;
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = meaning;
    }

    public class Meaning {
        private List<String> noun;
        private List<String> verb;
        private List<String> adverb;
        private List<String> adjective;

        public Meaning(List<String> noun, List<String> verb, List<String> adverb, List<String> adjective) {
            this.noun = noun;
            this.verb = verb;
            this.adverb = adverb;
            this.adjective = adjective;
        }

        public List<String> getNoun() {
            return noun;
        }

        public void setNoun(List<String> noun) {
            this.noun = noun;
        }

        public List<String> getVerb() {
            return verb;
        }

        public void setVerb(List<String> verb) {
            this.verb = verb;
        }

        public List<String> getAdverb() {
            return adverb;
        }

        public void setAdverb(List<String> adverb) {
            this.adverb = adverb;
        }

        public List<String> getAdjective() {
            return adjective;
        }

        public void setAdjective(List<String> adjective) {
            this.adjective = adjective;
        }
    }
}


