
package samples.speech.cognitiveservices.microsoft.myapplication.viewmodel;

import androidx.databinding.ObservableField;

public class Fragment_login_ViewModel {
    ObservableField<String> taikhoan = new ObservableField<>();
    ObservableField<String> matkhau = new ObservableField<>();

    public ObservableField<String> getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(ObservableField<String> taikhoan) {
        this.taikhoan = taikhoan;
    }

    public ObservableField<String> getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(ObservableField<String> matkhau) {
        this.matkhau = matkhau;
    }

}