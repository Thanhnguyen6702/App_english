package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.FragmentLoginBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Fragment_login_ViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_login extends Fragment {
    FragmentLoginBinding fragmentLoginBinding;
    ShareViewModel data_login;
    SharedPreferences sharedPreferences;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        Fragment_login_ViewModel fragment_login_viewModel = new Fragment_login_ViewModel();
        fragmentLoginBinding.setFragmentLoginViewModel(fragment_login_viewModel);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_bottom);
        bottomNavigationView.setVisibility(View.GONE);
        data_login = ((MainActivity) requireActivity()).getData_login();
        sharedPreferences = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        dialog = new Dialog(requireContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);
        fragmentLoginBinding.buttonLogin.setOnClickListener(view1 -> ApiService.apiService.login(fragmentLoginBinding.taikhoan.getText().toString(), fragmentLoginBinding.matkhau.getText().toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                fragmentLoginBinding.buttonLogin.setEnabled(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody s) {
                try {
                    if (s.string().equals("success")) {
                        editor.putString("account", fragmentLoginBinding.taikhoan.getText().toString());
                        editor.putString("password", fragmentLoginBinding.matkhau.getText().toString());
                        editor.apply();
                        data_login.setData(fragmentLoginBinding.taikhoan.getText().toString());
                        NavController navController = Navigation.findNavController(fragmentLoginBinding.getRoot());
                        navController.navigate(R.id.action_fragment_login_to_fragment_home);
                        navController.popBackStack(R.id.fragment_login, true);
                        getData(fragmentLoginBinding.taikhoan.getText().toString());
                    } else {
                        fragmentLoginBinding.taikhoan.setError("Tài khoản hoặc mật khẩu không chính xác");
                        fragmentLoginBinding.matkhau.setError("Tài khoản hoặc mật khẩu không chính xác");
                        fragmentLoginBinding.buttonLogin.setEnabled(true);
                    }
                    dialog.dismiss();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                fragmentLoginBinding.buttonLogin.setEnabled(true);
                dialog.dismiss();
            }

            @Override
            public void onComplete() {

            }
        }));
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input1 = fragmentLoginBinding.taikhoan.getText().toString().trim();
                String input2 = fragmentLoginBinding.matkhau.getText().toString().trim();
                if (!input1.equals("") && !input2.equals("")) {
                    fragmentLoginBinding.buttonLogin.setEnabled(true);
                    fragmentLoginBinding.buttonLogin.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                } else {
                    fragmentLoginBinding.buttonLogin.setEnabled(false);
                    fragmentLoginBinding.buttonLogin.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        fragmentLoginBinding.taikhoan.addTextChangedListener(textWatcher);
        fragmentLoginBinding.matkhau.addTextChangedListener(textWatcher);
        SpannableString spannableString = new SpannableString("Bạn chưa có tài khoản? Đăng kí ngay!");
        int start = spannableString.toString().indexOf("Đăng kí ngay!");
        int end = start + "Đăng kí ngay!".length();
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                NavController navController = Navigation.findNavController(fragmentLoginBinding.getRoot());
                navController.navigate(R.id.action_fragment_login_to_fragment_signup);
            }
        };
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.teal_200)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        fragmentLoginBinding.taomoi.setText(spannableString);
        fragmentLoginBinding.taomoi.setMovementMethod(LinkMovementMethod.getInstance());
        fragmentLoginBinding.getRoot().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!(v instanceof EditText)) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                    fragmentLoginBinding.getRoot().clearFocus();
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                fragmentLoginBinding.getRoot().performClick();
            }
            return false;
        });
        return fragmentLoginBinding.getRoot();
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
                .subscribe(success -> getActivity().runOnUiThread(()->{
                    dialog.dismiss();
        }), throwable -> {

                });
    }

}

