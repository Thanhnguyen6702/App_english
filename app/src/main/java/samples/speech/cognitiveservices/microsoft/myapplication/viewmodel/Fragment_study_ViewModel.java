package samples.speech.cognitiveservices.microsoft.myapplication.viewmodel;

import androidx.databinding.ObservableField;

public class Fragment_study_ViewModel {
    ObservableField<String> textQuestion = new ObservableField<>();
    ObservableField<String> pronunciation = new ObservableField<>();
    ObservableField<String> answer1 = new ObservableField<>();
    ObservableField<String> answer2 = new ObservableField<>();
    ObservableField<String> answer3 = new ObservableField<>();
    ObservableField<String> answer4 = new ObservableField<>();

    public ObservableField<String> getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation.set(pronunciation);
    }

    public ObservableField<String> getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion.set(textQuestion);
    }

    public ObservableField<String> getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1.set(answer1);
    }

    public ObservableField<String> getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2.set(answer2);
    }

    public ObservableField<String> getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3.set(answer3);
    }

    public ObservableField<String> getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4.set(answer4);
    }
}
