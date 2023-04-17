package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.FragmentSignupBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Fragment_signup_ViewModel;

public class Fragment_signup extends Fragment {
    FragmentSignupBinding fragmentSignupBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentSignupBinding = FragmentSignupBinding.inflate(inflater, container, false);
        Fragment_signup_ViewModel fragment_signup_viewModel = new Fragment_signup_ViewModel();
        fragmentSignupBinding.setFragmentSignupViewModel(fragment_signup_viewModel);
        fragmentSignupBinding.getRoot().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!(v instanceof EditText)) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                    fragmentSignupBinding.getRoot().clearFocus();
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                fragmentSignupBinding.getRoot().performClick();
            }
            return false;
        });
        fragmentSignupBinding.backSignIn.setOnClickListener(view -> backLogin());
        SpannableString spannableString = new SpannableString("Bạn đã có tài khoản? Đăng nhập!");
        int start = spannableString.toString().indexOf("Đăng nhập!");
        int end = start + "Đăng nhập!".length();
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                backLogin();
            }
        };
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.teal_200)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        fragmentSignupBinding.quaylaidangnhap.setText(spannableString);
        fragmentSignupBinding.quaylaidangnhap.setMovementMethod(LinkMovementMethod.getInstance());
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String taikhoan = fragmentSignupBinding.taikhoanDk.getText().toString();
                String matkhau = fragmentSignupBinding.matkhauDk.getText().toString();
                String nhaplaimatkhau = fragmentSignupBinding.nhaplaimatkhau.getText().toString();
                if (!taikhoan.equals("") && !matkhau.equals("") && !nhaplaimatkhau.equals("")) {
                    if (!matkhau.equals(nhaplaimatkhau)) {
                        fragmentSignupBinding.nhaplaimatkhau.setError("Mật khẩu không trùng khớp");
                    } else {
                        fragmentSignupBinding.buttonSignup.setEnabled(true);
                        fragmentSignupBinding.buttonSignup.setOnClickListener(view12 -> sign_up());
                    }
                } else {
                    if (taikhoan.equals(""))
                        fragmentSignupBinding.taikhoanDk.setError("Vui lòng nhập đủ thông tin");
                    else if (matkhau.equals(""))
                        fragmentSignupBinding.matkhauDk.setError("Vui lòng nhập đủ thông tin");
                    else
                        fragmentSignupBinding.nhaplaimatkhau.setError("Vui lòng nhập đủ thông tin");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        fragmentSignupBinding.taikhoanDk.addTextChangedListener(textWatcher);
        fragmentSignupBinding.matkhauDk.addTextChangedListener(textWatcher);
        fragmentSignupBinding.nhaplaimatkhau.addTextChangedListener(textWatcher);
        return fragmentSignupBinding.getRoot();
    }

    public void backLogin() {
        NavController navController = Navigation.findNavController(fragmentSignupBinding.getRoot());
        navController.popBackStack();
    }

    public void sign_up() {
        ApiService.apiService.signup(fragmentSignupBinding.taikhoanDk.getText().toString(), fragmentSignupBinding.matkhauDk.getText().toString()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                fragmentSignupBinding.buttonSignup.setEnabled(false);
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {

                try {
                    String response = responseBody.string();
                    if (response.trim().equals("success")) {
                        Log.e("loi",response);
                        backLogin();
                        Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("loi1",response);
                        fragmentSignupBinding.taikhoanDk.setError("Tài khoản đã tồn tại");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                fragmentSignupBinding.buttonSignup.setEnabled(true);
            }

            @Override
            public void onComplete() {
                fragmentSignupBinding.buttonSignup.setEnabled(true);
            }
        });
    }
}
