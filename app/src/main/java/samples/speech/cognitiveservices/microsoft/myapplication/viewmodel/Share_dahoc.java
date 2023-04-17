package samples.speech.cognitiveservices.microsoft.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value_revise;

public class Share_dahoc extends ViewModel {
    MutableLiveData<List<Value_revise>> value_resive = new MutableLiveData<>();
    public void setValue_resive(List<Value_revise> value_resive){
        this.value_resive.setValue(value_resive);
    }
    public LiveData<List<Value_revise>> getValue_resive(){
        return value_resive;
    }
}
