package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Value_Adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topic;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.FragmentListBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Fragment_list_ViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_list extends Fragment {
    FragmentListBinding fragmentListBinding;
    private Value_Adapter valueAdapter;
    private RecyclerView rcvData;
    private List<Topic> topics;
    Share_revise data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentListBinding = FragmentListBinding.inflate(inflater, container, false);
        fragmentListBinding.setFragmentList(new Fragment_list_ViewModel());
        BottomNavigationView bottomnav = requireActivity().findViewById(R.id.nav_bottom);
        bottomnav.setVisibility(View.VISIBLE);
        bottomnav.setSelectedItemId(R.id.action_collection);
        data = ((MainActivity) requireActivity()).getData_revise();
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_load_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvData = fragmentListBinding.rcvValue;
        rcvData.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        rcvData.addItemDecoration(dividerItemDecoration);
        ApiService.apiService.getTopic("Vocabulary").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Topic>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                dialog.show();
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Topic> topicList) {
                topics = topicList;
                data.setList_topic_practice(topicList);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
                valueAdapter = new Value_Adapter(topics,fragmentListBinding.getRoot());
                rcvData.setAdapter(valueAdapter);
                dialog.dismiss();
            }
        });
        return fragmentListBinding.getRoot();
    }
}
