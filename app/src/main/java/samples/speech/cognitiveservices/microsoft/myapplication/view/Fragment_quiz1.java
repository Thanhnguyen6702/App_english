package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.Gridview_Adapter;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_quiz1 extends Fragment {
    List<Vocabulary> vocabulariesUncheck = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz1,container,false);
        GridView gridView = view.findViewById(R.id.gridview);
        ImageView back = view.findViewById(R.id.back_quiz1);
        Button btConfirm = view.findViewById(R.id.button_quiz1);
        TextView item_select = view.findViewById(R.id.item_select);
        ShareViewModel shareViewModel = ((MainActivity) requireActivity()).getData_login();
        int quantity = shareViewModel.getVocabulary().getValue().second.size();
        setTextSelect(item_select,quantity);
        Gridview_Adapter gridviewAdapter = new Gridview_Adapter(getContext(), vocabularies -> {
            vocabulariesUncheck = vocabularies;
            setTextSelect(item_select,quantity-vocabularies.size());
        });
        gridviewAdapter.setData(shareViewModel.getVocabulary().getValue().second);
        gridView.setAdapter(gridviewAdapter);
        back.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });
        btConfirm.setOnClickListener(view1 -> {
                List<Vocabulary> newVocabularies = shareViewModel.getVocabulary().getValue().second;
                String topic = shareViewModel.getVocabulary().getValue().first;
                newVocabularies.removeAll(vocabulariesUncheck);
                shareViewModel.setShare_vocabulary(new Pair<>(topic,newVocabularies));
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_fragment_quiz1_to_fragment_quiz3);
        });
        return view;
    }
    private void setTextSelect(TextView textView,int quantity){
        textView.setText("Số từ đã chọn: " +quantity);
    }
}
