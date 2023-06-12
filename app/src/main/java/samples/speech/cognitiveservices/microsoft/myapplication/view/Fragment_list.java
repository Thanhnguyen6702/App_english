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

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Value_Adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.FragmentListBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Fragment_list_ViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_list extends Fragment {
    FragmentListBinding fragmentListBinding;
    private Value_Adapter valueAdapter;
    private RecyclerView rcvData;
    private List<Value> valueList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentListBinding = FragmentListBinding.inflate(inflater, container, false);
        fragmentListBinding.setFragmentList(new Fragment_list_ViewModel());
        ShareViewModel data_login = ((MainActivity) requireActivity()).getData_login();
        String account = data_login.getData().getValue();
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_load_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvData = fragmentListBinding.rcvValue;
        rcvData.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        rcvData.addItemDecoration(dividerItemDecoration);
        ApiService.apiService.getListValue(account).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Value>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                dialog.show();
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Value> values) {
                valueList = values;
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
                valueAdapter = new Value_Adapter(valueList);
                rcvData.setAdapter(valueAdapter);
                dialog.dismiss();
            }
        });
        return fragmentListBinding.getRoot();
    }
}
