package samples.speech.cognitiveservices.microsoft.myapplication.viewmodel;

import androidx.databinding.ObservableField;

public class Fragment_list_ViewModel {
    ObservableField<String> TiengAnh = new ObservableField<>();
    ObservableField<String> TiengViet = new ObservableField<>();

    public ObservableField<String> getTiengAnh() {
        return TiengAnh;
    }

    public void setTiengAnh(ObservableField<String> tiengAnh) {
        TiengAnh = tiengAnh;
    }

    public ObservableField<String> getTiengViet() {
        return TiengViet;
    }

    public void setTiengViet(ObservableField<String> tiengViet) {
        TiengViet = tiengViet;
    }
}
