package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Finish_Adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Detail_info;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_finish extends Fragment {
    Finish_Adapter finish_adapter;
    RecyclerView recyclerView;
    List<Value> value_reviseList;
    Share_revise share_revise;
    ShareViewModel shareViewModel;
    Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish, container, false);
        button = view.findViewById(R.id.button_finish);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.rcv_finish);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        share_revise = ((MainActivity) requireActivity()).getData_revise();
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        get_data();
        value_reviseList = share_revise.getValue_revise().getValue();
        TextView tuhoanthanh = view.findViewById(R.id.textView_finish1);
        tuhoanthanh.setText("Chúc mừng bạn vừa hoàn thành thêm được "+value_reviseList.size()+" từ");
        finish_adapter = new Finish_Adapter(value_reviseList);
        recyclerView.setAdapter(finish_adapter);
        ImageView close = view.findViewById(R.id.close_finish);
        close.setOnClickListener(view1->{
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_finish_to_fragment_home);
            share_revise.setValue_revise(null);
        });
        button.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            if(button.getText().toString().equals("Tiếp tục ôn tập")){
                navController.navigate(R.id.action_fragment_finish_to_fragment_learn);
            }else {
                navController.navigate(R.id.action_fragment_finish_to_fragment_home);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        share_revise.setValue_revise(null);
    }
    public void get_data() {
        ApiService.apiService.getDetailInfo(shareViewModel.getData().getValue()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Detail_info>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    button.setEnabled(false);
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
                    button.setEnabled(true);
                    checkData();
                    share_revise.setValue_revise(null);
            }
        });
    }
    public  void checkData(){

        if(shareViewModel.getData_detail().getValue().getHoc().size()>0){
            button.setText("Tiếp tục ôn tập");
        } else {
            button.setText("Hoàn thành");
        }
    }
}
