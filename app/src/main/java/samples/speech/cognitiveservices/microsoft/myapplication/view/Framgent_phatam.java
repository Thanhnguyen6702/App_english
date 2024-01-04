package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topicphatam;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Framgent_phatam extends Fragment  {
    View view;
    TextView sotudahoc;
    Button buttonhoc, buttonthap, buttontrungbinh, buttonxuatsac;
    ShareViewModel shareViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.topic_pronun, container, false);
        BottomNavigationView bottomnav = requireActivity().findViewById(R.id.nav_bottom);
        bottomnav.setVisibility(View.VISIBLE);
        bottomnav.setSelectedItemId(R.id.action_voice);
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        buttonhoc = view.findViewById(R.id.tieptuchoc);
        sotudahoc = view.findViewById(R.id.sotuphatam);
        buttonthap = view.findViewById(R.id.thap);
        buttontrungbinh = view.findViewById(R.id.trungbinh);
        buttonxuatsac = view.findViewById(R.id.xuatsac);
        if (shareViewModel.getSotuphatam().getValue() == null || shareViewModel.getSotuphatam().getValue().isEmpty()) {
            getData(shareViewModel.getData().getValue());
        }
        shareViewModel.getSotuphatam().observe(getViewLifecycleOwner(), data -> {
            if (data != null && !data.isEmpty()) {
                buttonthap.setText(data.get(0));
                buttontrungbinh.setText(data.get(1));
                buttonxuatsac.setText(data.get(2));
                sotudahoc.setText(data.get(3) + " từ");
            } else {
                getData(shareViewModel.getData().getValue());
            }
        });
        buttonhoc.setOnClickListener(view1 -> {
            shareViewModel.setNav(R.id.action_fragment_topic_to_fragment_voice);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_framgent_phatam_to_fragment_topic);
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_bottom);
            bottomNavigationView.setVisibility(View.GONE);
        });
        return view;
    }

    public void getData(String account) {
        List<String> data = new ArrayList<>();
        ApiService.apiService.getTopicphatam(account).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Topicphatam>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Topicphatam> topicphatam) {
                int dahoc = Integer.parseInt(topicphatam.get(0).getThap()) + Integer.parseInt(topicphatam.get(0).getTrungbinh()) + Integer.parseInt(topicphatam.get(0).getCao());
                data.add(0, topicphatam.get(0).getThap());
                data.add(1, topicphatam.get(0).getTrungbinh());
                data.add(2, topicphatam.get(0).getCao());
                data.add(3, String.valueOf(dahoc));
                shareViewModel.setShare_listTopicphatam(topicphatam);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                shareViewModel.setShare_sotuphatam(data);
            }
        });
    }

}
