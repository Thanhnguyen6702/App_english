package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Detail_info;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.FragmentLearnBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Fragment_learn_ViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_learn extends Fragment {
    FragmentLearnBinding fragmentLearnBinding;
    Fragment_learn_ViewModel fragment_learn_viewModel;
    ShareViewModel shareViewModel;
    Share_revise share_revise;
    List<Value> hoc;
    List<Value> listvalue;
    List<Value> reviseList = new ArrayList<>();
    String[] arrayAnswer = new String[4];
    Random random = new Random();
    int check, start, start_hoc;
    Boolean iscorrect = true;
    Boolean check_answer = true;
    private final Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLearnBinding = FragmentLearnBinding.inflate(inflater, container, false);
        fragment_learn_viewModel = new Fragment_learn_ViewModel();
        fragmentLearnBinding.setFragmentLearnViewModel(fragment_learn_viewModel);
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        share_revise = ((MainActivity) requireActivity()).getData_revise();
        String account = shareViewModel.getData().getValue();
        hoc = shareViewModel.getData_detail().getValue().getHoc();
        listvalue = shareViewModel.getListValue().getValue();
        check = listvalue.size() - 1;
        start = random.nextInt(1000) % check;
        start_hoc = 0;
        next(start_hoc);
        fragmentLearnBinding.close.setOnClickListener(view -> {
            share_revise.setValue_resive(reviseList);
            get_data();
            if(reviseList != null) {
                NavController navController = Navigation.findNavController(fragmentLearnBinding.getRoot());
                navController.navigate(R.id.action_fragment_learn_to_fragment_finish);
            }
        });
        fragmentLearnBinding.Tiengviet1.setOnClickListener(view -> {
            if (fragmentLearnBinding.Tiengviet1.getText().equals(hoc.get(start_hoc).getTiengviet())) {
                fragmentLearnBinding.Tiengviet1.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                answer_true(account, hoc.get(start_hoc).getTienganh());
                if (start_hoc < hoc.size() - 1) next(++start_hoc);
                else {
                    share_revise.setValue_resive(reviseList);
                    NavController navController = Navigation.findNavController(fragmentLearnBinding.getRoot());
                    navController.navigate(R.id.action_fragment_learn_to_fragment_finish);
                }
            } else {
                fragmentLearnBinding.Tiengviet1.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                answer_false(account, hoc.get(start_hoc).getTienganh());
                disableImageview(view);
            }

        });
        fragmentLearnBinding.Tiengviet2.setOnClickListener(view -> {
            if (fragmentLearnBinding.Tiengviet2.getText().equals(hoc.get(start_hoc).getTiengviet())) {
                fragmentLearnBinding.Tiengviet2.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                answer_true(account, hoc.get(start_hoc).getTienganh());
                if (start_hoc < hoc.size() - 1) next(++start_hoc);
                else {
                    share_revise.setValue_resive(reviseList);
                    NavController navController = Navigation.findNavController(fragmentLearnBinding.getRoot());
                    navController.navigate(R.id.action_fragment_learn_to_fragment_finish);
                }
            } else {
                fragmentLearnBinding.Tiengviet2.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                answer_false(account, hoc.get(start_hoc).getTienganh());
                disableImageview(view);
            }

        });
        fragmentLearnBinding.Tiengviet3.setOnClickListener(view -> {
            if (fragmentLearnBinding.Tiengviet3.getText().equals(hoc.get(start_hoc).getTiengviet())) {
                fragmentLearnBinding.Tiengviet3.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                answer_true(account, hoc.get(start_hoc).getTienganh());
                if (start_hoc < hoc.size() - 1) next(++start_hoc);
                else {
                    share_revise.setValue_resive(reviseList);
                    NavController navController = Navigation.findNavController(fragmentLearnBinding.getRoot());
                    navController.navigate(R.id.action_fragment_learn_to_fragment_finish);
                }
            } else {
                answer_false(account, hoc.get(start_hoc).getTienganh());
                fragmentLearnBinding.Tiengviet3.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                disableImageview(view);
            }

        });
        fragmentLearnBinding.Tiengviet4.setOnClickListener(view -> {
            if (fragmentLearnBinding.Tiengviet4.getText().equals(hoc.get(start_hoc).getTiengviet())) {
                fragmentLearnBinding.Tiengviet4.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                answer_true(account, hoc.get(start_hoc).getTienganh());
                if (start_hoc < hoc.size() - 1) next(++start_hoc);
                else {
                    share_revise.setValue_resive(reviseList);
                    NavController navController = Navigation.findNavController(fragmentLearnBinding.getRoot());
                    navController.navigate(R.id.action_fragment_learn_to_fragment_finish);
                }
            } else {
                answer_false(account, hoc.get(start_hoc).getTienganh());
                fragmentLearnBinding.Tiengviet4.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                disableImageview(view);
            }


        });
        return fragmentLearnBinding.getRoot();
    }

    public void next(int k) {
        iscorrect = false;
        if(k>0) {
            handler.postDelayed(() -> {
                enableImageview();
                fragmentLearnBinding.textQuestion.setText(hoc.get(k).getTienganh());
                arrayAnswer[0] = hoc.get(k).getTiengviet();
                for (int i = 1; i <= 3; i++) {
                    if (arrayAnswer[0].equals(listvalue.get(start).getTiengviet())) {
                        start = (start + 1) % check;
                    }
                    arrayAnswer[i] = listvalue.get(start).getTiengviet();
                    start = (start + 1) % check;
                }
                Collections.shuffle(Arrays.asList(arrayAnswer));
                fragmentLearnBinding.Tiengviet1.setText(arrayAnswer[0]);
                fragmentLearnBinding.Tiengviet2.setText(arrayAnswer[1]);
                fragmentLearnBinding.Tiengviet3.setText(arrayAnswer[2]);
                fragmentLearnBinding.Tiengviet4.setText(arrayAnswer[3]);
                setButton();
            }, 750);
        }
        else {
            enableImageview();
            fragmentLearnBinding.textQuestion.setText(hoc.get(k).getTienganh());
            arrayAnswer[0] = hoc.get(k).getTiengviet();
            for (int i = 1; i <= 3; i++) {
                if (arrayAnswer[0].equals(listvalue.get(start).getTiengviet())) {
                    start = (start + 1) % check;
                }
                arrayAnswer[i] = listvalue.get(start).getTiengviet();
                start = (start + 1) % check;
            }
            Collections.shuffle(Arrays.asList(arrayAnswer));
            fragmentLearnBinding.Tiengviet1.setText(arrayAnswer[0]);
            fragmentLearnBinding.Tiengviet2.setText(arrayAnswer[1]);
            fragmentLearnBinding.Tiengviet3.setText(arrayAnswer[2]);
            fragmentLearnBinding.Tiengviet4.setText(arrayAnswer[3]);
            setButton();
        }
    }

    public void setButton() {
        fragmentLearnBinding.Tiengviet1.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_button));
        fragmentLearnBinding.Tiengviet2.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_button));
        fragmentLearnBinding.Tiengviet3.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_button));
        fragmentLearnBinding.Tiengviet4.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.custom_button));
    }

    public void answer_true(String account, String answer) {
        reviseList.add(hoc.get(start_hoc));
        iscorrect = true;
        disableImageview();
        if(check_answer) {
            int check_day = Integer.parseInt(reviseList.get(start_hoc).getCheckday());
            if (check_day % 10 > 1) {
                check_day = check_day % 10 - 1;
                reviseList.get(start_hoc).setCheckday(check_day + "");
            } else {
                check_day = check_day / 10 % 10;
                reviseList.get(start_hoc).setCheckday("" + check_day);
            }
        }
        else  reviseList.get(start_hoc).setCheckday("1");
        ApiService.apiService.postTrue(account, answer).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Void>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Void unused) {
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void answer_false(String account, String answer) {
        check_answer = false;
        ApiService.apiService.postFalse(account, answer).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Void>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }


            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Void unused) {
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        get_data();
    }

    public void get_data() {
        ApiService.apiService.getDetailInfo(shareViewModel.getData().getValue()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Detail_info>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Detail_info detail_info) {
                shareViewModel.setShareData_detail(detail_info);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void disableImageview() {
        fragmentLearnBinding.Tiengviet1.setEnabled(false);
        fragmentLearnBinding.Tiengviet2.setEnabled(false);
        fragmentLearnBinding.Tiengviet3.setEnabled(false);
        fragmentLearnBinding.Tiengviet4.setEnabled(false);

    }

    public void disableImageview(View view) {
        view.setEnabled(false);
        handler.postDelayed(() -> view.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_disable)), 500);
    }

    public void enableImageview() {
        fragmentLearnBinding.Tiengviet1.setEnabled(true);
        fragmentLearnBinding.Tiengviet2.setEnabled(true);
        fragmentLearnBinding.Tiengviet3.setEnabled(true);
        fragmentLearnBinding.Tiengviet4.setEnabled(true);
    }
}
