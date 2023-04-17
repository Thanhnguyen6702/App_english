package samples.speech.cognitiveservices.microsoft.myapplication.viewmodel;

import androidx.databinding.ObservableField;

public class Fragment_home_ViewModel {
    ObservableField<String> name = new ObservableField<>();
    ObservableField<String> tuchuathuoc = new ObservableField<>();
    ObservableField<String> tudathuoc = new ObservableField<>();
    ObservableField<String> tusapthuoc = new ObservableField<>();
    ObservableField<String> tuhomnayhoc = new ObservableField<>();
    public Fragment_home_ViewModel(String name,String tuhomnayhoc, String tuchuathuoc, String tudathuoc, String tusapthuoc) {
        this.name.set(name);
        this.tuchuathuoc.set(tuchuathuoc);
        this.tudathuoc.set(tudathuoc);
        this.tusapthuoc.set(tusapthuoc);
        this.tuhomnayhoc.set(tuhomnayhoc);
    }

    public ObservableField<String> getTuhomnayhoc() {
        return tuhomnayhoc;
    }

    public void setTuhomnayhoc(String tuhomnayhoc) {
        this.tuhomnayhoc.set(tuhomnayhoc);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String newname) {
        name.set(newname);
    }

    public ObservableField<String> getTuchuathuoc() {
        return tuchuathuoc;
    }

    public void setTuchuathuoc(String newtuchuathuoc) {
        tuchuathuoc.set(newtuchuathuoc);
    }

    public ObservableField<String> getTudathuoc() {
        return tudathuoc;
    }

    public void setTudathuoc(String newtudathuoc) {
        tudathuoc.set(newtudathuoc);
    }

    public ObservableField<String> getTusapthuoc() {
        return tusapthuoc;
    }

    public void setTusapthuoc(String newtusapthuoc) {
        tusapthuoc.set(newtusapthuoc);
    }
}
