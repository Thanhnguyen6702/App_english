package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Detail_info;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class layout_login extends Fragment {
    private ShareViewModel data_login;
    private Dialog dialog;
    private static final int DELAY_LOGIN = 2000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_login, container, false);
        ImageView icon;
        TextView textView1, textView2;
        icon = view.findViewById(R.id.img_login);
        textView1 = view.findViewById(R.id.text_lt_login1);
        textView2 = view.findViewById(R.id.text_lt_login2);
        data_login = ((MainActivity) requireActivity()).getData_login();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", "");
        String pass = sharedPreferences.getString("password", "");
        Animation top, bottom;
        top = AnimationUtils.loadAnimation(getContext(), R.anim.anim_top);
        bottom = AnimationUtils.loadAnimation(getContext(), R.anim.anim_bottom);
        icon.setAnimation(top);
        textView1.setAnimation(bottom);
        textView2.setAnimation(bottom);
        new Handler().postDelayed(() -> {
            dialog = new Dialog(requireContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_login);
            dialog.show();
            checkLogin(account, pass, view);
        }, DELAY_LOGIN);
        return view;
    }

    public void checkLogin(String account, String password, View view) {
        ApiService.apiService.login(account, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {
                try {
                    if (responseBody.string().equals("success")) {
                        data_login.setData(account);
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_layout_login_to_fragment_home);
                        getData(account);
                    } else {
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_layout_login_to_fragment_login);
                        dialog.dismiss();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_layout_login_to_fragment_login);
                dialog.dismiss();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @SuppressLint("CheckResult")
    public void getData(String account) {
        Observable<Detail_info> detailInfoObservable = ApiService.apiService.getDetailInfo(account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<List<Value>> listValueObservable = ApiService.apiService.getListValue(account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable.zip(detailInfoObservable, listValueObservable, (detail_info, values) -> {
                    data_login.setShareData_detail(detail_info);
                    data_login.setShare_listValue(values);
                    return true;
                })
                .subscribe(success -> dialog.dismiss(), throwable -> {

                });
    }
}
