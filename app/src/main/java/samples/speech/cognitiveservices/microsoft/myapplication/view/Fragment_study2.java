package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Word;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_study2 extends Fragment {
    LinearLayout linearLayout1, linearLayout2;
    ShareViewModel shareViewModel;
    TextView text_example, tienganh, tiengviet, phienam, category, tienganh_overleaf, define;
    ImageView voice;
    Word listdata;
    int start = 0;
    int end;
    Boolean check = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study2, container, false);
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        List<Vocabulary> chuahoc = shareViewModel.getChuahoc().getValue();
        linearLayout1 = view.findViewById(R.id.linearLayout1);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout1.setEnabled(false);
        getData(chuahoc.get(0).getTienganh());
        voice = view.findViewById(R.id.voice_study);
        tienganh = linearLayout1.findViewById(R.id.Tienganh_study);
        tiengviet = linearLayout1.findViewById(R.id.tiengviet_study);
        phienam = linearLayout1.findViewById(R.id.phienam_2);
        text_example = linearLayout2.findViewById(R.id.example);
        category = linearLayout2.findViewById(R.id.category);
        define = linearLayout2.findViewById(R.id.definition);
        tienganh_overleaf = linearLayout2.findViewById(R.id.Tienganh_study_overleaf);
        tiengviet.setText(chuahoc.get(0).getTiengviet());
        tienganh.setText(chuahoc.get(0).getTienganh());
        phienam.setText(chuahoc.get(0).getPhienam());
        voice.setOnClickListener(view1->{
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
            chuahoc.remove(0);
            shareViewModel.setShare_chuahoc(chuahoc);
        });
        linearLayout1.setOnClickListener(view1 -> {
            flipViews(linearLayout1, linearLayout2);
            tienganh_overleaf.setText(chuahoc.get(0).getTienganh());
            try {

            if (check) {
                boolean check_ex = true;
                for(int i=0;i<listdata.getMeaning().get(start).getDefinitions().size();i++){
                    if (listdata.getMeaning().get(start).getDefinitions().get(i).getExample() != null){
                        text_example.setText("Eg: " + listdata.getMeaning().get(start).getDefinitions().get(i).getExample());
                        check_ex = false;
                        break;
                    }
                }

                if(check_ex){
                    text_example.setText("Mình chưa có ví dụ cho từ này :(");
                }
                if(listdata.getMeaning().get(start).getDefinitions().get(0).getDefinition()!=null && listdata.getMeaning().get(start).getPartOfSpeech()!=null ) {
                    define.setText(listdata.getMeaning().get(start).getDefinitions().get(0).getDefinition());
                    set_background(listdata.getMeaning().get(start).getPartOfSpeech());
                }
                else {
                    linearLayout2.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_100));
                }
            }}catch (Exception e){
                e.printStackTrace();
            }
        });
        linearLayout2.setOnClickListener(view1->{
            try{
            if(start<end) {
                start++;
                flipViews(linearLayout2, linearLayout2);
                if (check) {
                    boolean check_ex = true;
                    for(int i=0;i<listdata.getMeaning().get(start).getDefinitions().size();i++){
                        if (listdata.getMeaning().get(start).getDefinitions().get(i).getExample() != null){
                            text_example.setText("Eg: " + listdata.getMeaning().get(start).getDefinitions().get(i).getExample());
                            Log.e("loi",""+i);
                            check_ex = false;
                            break;
                        }
                    }

                    if(check_ex){
                        text_example.setText("Mình chưa có ví dụ cho từ này :(");
                    }
                    if(listdata.getMeaning().get(start).getDefinitions().get(0).getDefinition()!=null && listdata.getMeaning().get(start).getPartOfSpeech()!=null ) {
                        define.setText(listdata.getMeaning().get(start).getDefinitions().get(0).getDefinition());
                        set_background(listdata.getMeaning().get(start).getPartOfSpeech());
                    }
                    else {
                        linearLayout2.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_100));
                    }
                }

            }else {
                flipViews(linearLayout2,linearLayout1);
                start=0;
            }
            }catch (Exception e){
                e.printStackTrace();
            }

        });
        return view;
    }

    public void getData(String word) {
        ApiService.apiService2.getWord(word).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Word>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Word> word) {
                listdata = word.get(0);
                check = true;
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                check = false;
                linearLayout1.setEnabled(true);

            }

            @Override
            public void onComplete() {
                linearLayout1.setEnabled(true);
                end=listdata.getMeaning().size()-1;
            }
        });
    }

    public void set_background(String s) {
        if (s.equals("noun")) {
            linearLayout2.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.blue_100));
            category.setText("N");
        }
       else if (s.equals("adjective")) {
            linearLayout2.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.orange_100));
            category.setText("Adj");
        }
       else if (s.equals("verb")) {
            linearLayout2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_100));
            category.setText("V");
        }

    }

    private void flipViews(final LinearLayout frontLayout, final LinearLayout backLayout) {
        Animation flipOut = AnimationUtils.loadAnimation(requireContext(), R.anim.flip_out);
        Animation flipIn = AnimationUtils.loadAnimation(requireContext(), R.anim.flip_in);

        flipOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                frontLayout.setVisibility(View.GONE);
                backLayout.setVisibility(View.VISIBLE);
                backLayout.startAnimation(flipIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        frontLayout.startAnimation(flipOut);
    }

}
