package samples.speech.cognitiveservices.microsoft.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Definition;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Example;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topic;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;

public class Share_revise extends ViewModel {
    MutableLiveData<List<Value>> value_resive = new MutableLiveData<>();
    MutableLiveData<List<Topic>> list_topic = new MutableLiveData<>();
    MutableLiveData<List<String>> topic = new MutableLiveData<>();
    MutableLiveData<Example> example = new MutableLiveData<>();
    MutableLiveData<Definition> definition = new MutableLiveData<>();

    public void setExample(Example example) {
        this.example.setValue(example);
    }

    public LiveData<Example> getExample() {
        return example;
    }

    public void setDefinition(Definition definition) {
        this.definition.setValue(definition);
    }

    public LiveData<Definition> getDefinition() {
        return definition;
    }

    public void setTopic(List<String> topic) {
        this.topic.setValue(topic);
    }

    public LiveData<List<String>> gettopic() {
        return topic;
    }

    public void setList_topic(List<Topic> listtopic) {
        list_topic.setValue(listtopic);
    }

    public LiveData<List<Topic>> getlistTopic() {
        return list_topic;
    }

    public void setValue_resive(List<Value> value_resive) {
        this.value_resive.setValue(value_resive);
    }

    public LiveData<List<Value>> getValue_resive() {
        return value_resive;
    }
}
