package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_study2 extends Fragment {
    LinearLayout linearLayout1, linearLayout2;
    ShareViewModel shareViewModel;
    Share_revise share_revise;
    TextView text_example, tienganh, tiengviet, phienam, category, tienganh_overleaf, define;
    Button buttonNext;
    int start = 0;
    int end = -1;
    List<Prep> preps = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study2, container, false);
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        share_revise = ((MainActivity) requireActivity()).getData_revise();
        List<Vocabulary> chuahoc = shareViewModel.getChuahoc().getValue();
        linearLayout1 = view.findViewById(R.id.linearLayout1);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        linearLayout1.setEnabled(false);
        buttonNext = view.findViewById(R.id.button_next);
        tienganh = linearLayout1.findViewById(R.id.Tienganh_study);
        tiengviet = linearLayout1.findViewById(R.id.tiengviet_study);
        phienam = linearLayout1.findViewById(R.id.phienam_2);
        text_example = linearLayout1.findViewById(R.id.text_example);
        category = linearLayout2.findViewById(R.id.category);
        define = linearLayout2.findViewById(R.id.definition);
        tienganh_overleaf = linearLayout2.findViewById(R.id.Tienganh_study_overleaf);
        share_revise.getExample().observe(getViewLifecycleOwner(), example -> {
            if (example.getExample() != null) {
                text_example.setText("Eg: " + example.getExample().get(0));
            }
        });
        share_revise.getDefinition().observe(getViewLifecycleOwner(), definition1 -> {
            if (definition1 != null) {
                linearLayout1.setEnabled(true);
                if (definition1.getMeaning().getNoun() !=null)
                    preps.add(new Fragment_study2.Prep("noun", definition1.getMeaning().getNoun().get(0)));
                if (definition1.getMeaning().getVerb()!=null)
                    preps.add(new Fragment_study2.Prep("verb", definition1.getMeaning().getVerb().get(0)));
                if (definition1.getMeaning().getAdjective()!=null)
                    preps.add(new Fragment_study2.Prep("adjective", definition1.getMeaning().getAdjective().get(0)));
                if (definition1.getMeaning().getAdverb()!=null)
                    preps.add(new Fragment_study2.Prep("adverb", definition1.getMeaning().getAdverb().get(0)));
                end = preps.size() - 1;
            }
        });
        tiengviet.setText(chuahoc.get(0).getTiengviet());
        tienganh.setText(chuahoc.get(0).getTienganh());
        phienam.setText(chuahoc.get(0).getPhienam());
        buttonNext.setOnClickListener(view1 -> {
            chuahoc.remove(0);
            shareViewModel.setShare_chuahoc(chuahoc);
            NavController navController = Navigation.findNavController(view);
            if (!chuahoc.isEmpty()) {
                navController.popBackStack();
            } else {
                navController.navigate(R.id.action_fragment_study2_to_fragment_finish);
            }

        });
        linearLayout1.setOnClickListener(view1 -> {
            flipViews(linearLayout1, linearLayout2);
            tienganh_overleaf.setText(chuahoc.get(0).getTienganh());
            define.setText(preps.get(0).value);
            set_background(preps.get(0).key);

        });
        linearLayout2.setOnClickListener(view1 -> {
            try {
                if (start < end) {
                    start++;
                    flipViews(linearLayout2, linearLayout2);
                    define.setText(preps.get(start).value);
                    set_background(preps.get(start).key);
                } else {
                    flipViews(linearLayout2, linearLayout1);
                    start = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return view;
    }


    public void set_background(String s) {
        switch (s) {
            case "noun":
                linearLayout2.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.blue_100));
                category.setText("N");
                break;
            case "adjective":
                linearLayout2.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.orange_100));
                category.setText("Adj");
                break;
            case "verb":
                linearLayout2.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.green_100));
                category.setText("V");
                break;
            case "adverb":
                linearLayout2.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.purple_200));
                category.setText("Adv");
                break;
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

    static class Prep {
        String key;
        String value;

        public Prep(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
