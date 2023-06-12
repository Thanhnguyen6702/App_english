package samples.speech.cognitiveservices.microsoft.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Detail_info;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topic;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topicphatam;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;

public class ShareViewModel extends ViewModel {
    private final MutableLiveData<String> shareData = new MutableLiveData<>();
    private final MutableLiveData<Detail_info> shareData_detail = new MutableLiveData<>();
    private final MutableLiveData<List<Value>> share_listValue = new MutableLiveData<>();
    private  final  MutableLiveData<List<Vocabulary>> share_chuahoc = new MutableLiveData<>();
    private final MutableLiveData<List<Vocabulary>> share_phatam = new MutableLiveData<>();
    private final MutableLiveData<List<String>> share_sotuphatam = new MutableLiveData<>();
    private final MutableLiveData<Integer> nav = new MutableLiveData<>();
    private final MutableLiveData<List<Vocabulary>> share_chuahoc_phatam = new MutableLiveData<>();
    private final MutableLiveData<String> topic_phatam = new MutableLiveData<>();
    private final MutableLiveData<List<Topicphatam>> share_listTopicphatam = new MutableLiveData<>();
    public void setShare_listTopicphatam(List<Topicphatam> topicphatam){
        share_listTopicphatam.setValue(topicphatam);
    };
    public LiveData<List<Topicphatam>> getListtopicphatam(){
        return share_listTopicphatam;
    }
    public void setTopic_phatam(String topic_phatam){
        this.topic_phatam.setValue(topic_phatam);
    }
    public LiveData<String> getTopicphatam(){
        return topic_phatam;
    }
    public void setShare_chuahoc_phatam(List<Vocabulary> chuahoc_phatam){
        share_chuahoc_phatam.setValue(chuahoc_phatam);
    }
    public LiveData<List<Vocabulary>> getchuahoc_phatam(){
        return share_chuahoc_phatam;
    }
    public void setNav(Integer id){
        nav.setValue(id);
    }
    public LiveData<Integer> getNav(){
        return nav;
    }
    public void setShare_sotuphatam(List<String> sotuphatam){
        share_sotuphatam.setValue(sotuphatam);
    }
    public LiveData<List<String>> getSotuphatam(){
        return share_sotuphatam;
    }
    public void setShare_chuahoc(List<Vocabulary> vocabulary){
        share_chuahoc.setValue(vocabulary);
    }
    public LiveData<List<Vocabulary>> getChuahoc(){
        return share_chuahoc;
    }
    public void setData(String data){
        shareData.setValue(data);
    }
    public LiveData<String> getData(){
       return shareData;
    }
    public void setShareData_detail(Detail_info detailInfo){
        shareData_detail.setValue(detailInfo);
    }
    public LiveData<Detail_info> getData_detail(){return shareData_detail;}
    public void setShare_listValue(List<Value> listValue){
        share_listValue.setValue(listValue);
    }
    public LiveData<List<Value>> getListValue(){
        return share_listValue;
    }
    public LiveData<List<Vocabulary>> get_phatam(){
        return share_phatam;
    }
    public void setShare_phatam(List<Vocabulary> phatam){
        share_phatam.setValue(phatam);
    }
}
