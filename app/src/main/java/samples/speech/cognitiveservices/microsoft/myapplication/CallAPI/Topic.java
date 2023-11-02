package samples.speech.cognitiveservices.microsoft.myapplication.CallAPI;

import java.util.List;

public class Topic {
    String topic;
    List<Subtopic> childtopics;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Subtopic> getChildtopic() {
        return childtopics;
    }

    public void setChildtopic(List<Subtopic> childtopic) {
        this.childtopics = childtopic;
    }
    public class Subtopic {
        String childtopic;
        String image;
        String sotu;
        String sotu_chuahoc;

        public String getSotu_chuahoc() {
            return sotu_chuahoc;
        }

        public void setSotu_chuahoc(String sotu_chuahoc) {
            this.sotu_chuahoc = sotu_chuahoc;
        }

        public String getChildtopic() {
            return childtopic;
        }

        public void setChildtopic(String childtopic) {
            this.childtopic = childtopic;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSotu() {
            return sotu;
        }

        public void setSotu(String sotu) {
            this.sotu = sotu;
        }
    }
}

