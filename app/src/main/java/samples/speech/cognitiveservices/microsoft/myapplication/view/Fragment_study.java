package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Definition;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Detail_info;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Example;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.FragmentStudyBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_study extends Fragment {
    FragmentStudyBinding fragmentStudyBinding;
    ShareViewModel shareViewModel;
    List<Vocabulary> chua_hoc;
    List<Value> listvalue;
    Share_revise share_revise;
    List<Value> reviseList = new ArrayList<>();
    String[] arrayAnswer = new String[4];
    Random random = new Random();
    int check, start;
    private final Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentStudyBinding = FragmentStudyBinding.inflate(inflater, container, false);
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        share_revise = ((MainActivity) requireActivity()).getData_revise();
        chua_hoc = shareViewModel.getChuahoc().getValue();
        String account = shareViewModel.getData().getValue();
        getDefin_Examp(chua_hoc.get(0).getTienganh());
        listvalue = shareViewModel.getListValue().getValue();
        check = listvalue.size() - 1;
        start = random.nextInt(1000) % check;
        fragmentStudyBinding.textQuestionStudy.setText(chua_hoc.get(0).getTienganh());
        fragmentStudyBinding.phienam.setText(chua_hoc.get(0).getPhienam());
        int sotu=0;
        if (share_revise.getValue_revise().getValue() != null) {
            sotu = share_revise.getValue_revise().getValue().size();
        }
        fragmentStudyBinding.socauStudy.setText((sotu+1) + "/" + (chua_hoc.size()+sotu));
        arrayAnswer[0] = chua_hoc.get(0).getTiengviet();
        for (int i = 1; i <= 3; i++) {
            if (arrayAnswer[0].equals(listvalue.get(start).getTiengviet())) {
                start = (start + 1) % check;
            }
            arrayAnswer[i] = listvalue.get(start).getTiengviet();
            start = (start + 1) % check;
        }
        Collections.shuffle(Arrays.asList(arrayAnswer));
        fragmentStudyBinding.Tiengviet1Study.setText(arrayAnswer[0]);
        fragmentStudyBinding.Tiengviet2Study.setText(arrayAnswer[1]);
        fragmentStudyBinding.Tiengviet3Study.setText(arrayAnswer[2]);
        fragmentStudyBinding.Tiengviet4Study.setText(arrayAnswer[3]);
        fragmentStudyBinding.closeStudy.setOnClickListener(view -> {
            share_revise.setValue_revise(reviseList);
            if (reviseList != null) {
                NavController navController = Navigation.findNavController(fragmentStudyBinding.getRoot());
                navController.navigate(R.id.action_fragment_study_to_fragment_finish);
            }
            get_data();
        });
        fragmentStudyBinding.Tiengviet1Study.setOnClickListener(view -> {
            if (fragmentStudyBinding.Tiengviet1Study.getText().equals(chua_hoc.get(0).getTiengviet())) {
                fragmentStudyBinding.Tiengviet1Study.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                study(account, chua_hoc.get(0).getTienganh());
                next();
                share_revise.setValue_revise(reviseList);
            } else {
                fragmentStudyBinding.Tiengviet1Study.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                disableImageview(view);
            }


        });
        fragmentStudyBinding.Tiengviet2Study.setOnClickListener(view -> {
            if (fragmentStudyBinding.Tiengviet2Study.getText().equals(chua_hoc.get(0).getTiengviet())) {
                fragmentStudyBinding.Tiengviet2Study.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                study(account, chua_hoc.get(0).getTienganh());
                next();
                share_revise.setValue_revise(reviseList);

            } else {
                fragmentStudyBinding.Tiengviet2Study.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                disableImageview(view);

            }


        });
        fragmentStudyBinding.Tiengviet3Study.setOnClickListener(view -> {
            if (fragmentStudyBinding.Tiengviet3Study.getText().equals(chua_hoc.get(0).getTiengviet())) {
                fragmentStudyBinding.Tiengviet3Study.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                study(account, chua_hoc.get(0).getTienganh());
                next();
                share_revise.setValue_revise(reviseList);

            } else {
                fragmentStudyBinding.Tiengviet3Study.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                disableImageview(view);
            }


        });
        fragmentStudyBinding.Tiengviet4Study.setOnClickListener(view -> {
            if (fragmentStudyBinding.Tiengviet4Study.getText().equals(chua_hoc.get(0).getTiengviet())) {
                fragmentStudyBinding.Tiengviet4Study.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_true));
                study(account, chua_hoc.get(0).getTienganh());
                next();
                share_revise.setValue_revise(reviseList);
            } else {
                fragmentStudyBinding.Tiengviet4Study.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_false));
                disableImageview(view);
            }
        });
        return fragmentStudyBinding.getRoot();
    }

    public void next() {
        chua_hoc.get(0).setCheckday("1");
        reviseList.add(chua_hoc.get(0));
        handler.postDelayed(() -> {
            NavController navController = Navigation.findNavController(fragmentStudyBinding.getRoot());
            navController.navigate(R.id.action_fragment_study_to_fragment_study2);
        }, 750);


    }


    public void study(String account, String answer) {
        ApiService.apiService.postStudy(account, answer).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Void>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                disableImageview();
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

    public void getDefin_Examp(String word) {
        ApiService.apiService.getDefinition(word).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Definition>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Definition definition1) {
                share_revise.setDefinition(definition1);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        ApiService.apiService.getExample(word).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Example>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull Example example1) {
                share_revise.setExample(example1);
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
        fragmentStudyBinding.Tiengviet1Study.setEnabled(false);
        fragmentStudyBinding.Tiengviet2Study.setEnabled(false);
        fragmentStudyBinding.Tiengviet3Study.setEnabled(false);
        fragmentStudyBinding.Tiengviet4Study.setEnabled(false);
    }

    public void disableImageview(View view) {
        view.setEnabled(false);
        handler.postDelayed(() -> view.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.button_disable)), 500);

    }
}
