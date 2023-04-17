package samples.speech.cognitiveservices.microsoft.myapplication.viewmodel;

import androidx.databinding.ObservableField;

public class Fragment_signup_ViewModel {
   public ObservableField<String> taikhoan = new ObservableField<>();
   public ObservableField<String> matkhau = new ObservableField<>();
   public ObservableField<String> nhaplaimatkhau = new ObservableField<>();

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

    public ObservableField<String> getNhaplaimatkhau() {
        return nhaplaimatkhau;
    }

    public void setNhaplaimatkhau(ObservableField<String> nhaplaimatkhau) {
        this.nhaplaimatkhau = nhaplaimatkhau;
    }
}
