package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import samples.speech.cognitiveservices.microsoft.myapplication.R;

public class Fragment_logout extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout,container,false);
        BottomNavigationView bottomnav = requireActivity().findViewById(R.id.nav_bottom);
        bottomnav.setVisibility(View.VISIBLE);
        bottomnav.setSelectedItemId(R.id.action_setting);
        TextView textView = view.findViewById(R.id.text_tk);
        Button button = view.findViewById(R.id.button_logout);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        textView.setText("Tài khoản: "+sharedPreferences.getString("account",""));
        button.setOnClickListener(view1->{
            editor.putString("password","");
            editor.putString("account","");
            editor.apply();
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_fragment_logout_to_fragment_login);
        });
        return view;

    }
}
