package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import samples.speech.cognitiveservices.microsoft.myapplication.R;

public class Result_quiz3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_quiz3,container,false);
        TextView txtNumberTrue, txtNumberFalse,txtPercent;
        ProgressBar progressBar = view.findViewById(R.id.progress_quiz3);
        ImageView back  = view.findViewById(R.id.back_resultquiz3);
        Button replay = view.findViewById(R.id.button_resultquiz3);
        txtNumberTrue = view.findViewById(R.id.number_true);
        txtNumberFalse = view.findViewById(R.id.number_false);
        txtPercent = view.findViewById(R.id.percent_quiz3);
        int numberTrue = Result_quiz3Args.fromBundle(getArguments()).getNumberTrue();
        int numberFalse = Result_quiz3Args.fromBundle(getArguments()).getNumberFalse();
        progressBar.setProgress((int) (numberTrue*100.0/(numberFalse+numberTrue)));
        txtPercent.setText((numberTrue*100.0/(numberFalse+numberTrue)) + "%");
        txtNumberTrue.setText("Đúng: " + numberTrue);
        txtNumberFalse.setText("Sai: " + numberFalse);
        back.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_result_quiz3_to_fragment_pratice2));
        replay.setOnClickListener(view1 -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_result_quiz3_to_fragment_quiz3);
        });
        return view;
    }
}
