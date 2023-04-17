package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.ActivityMainBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_dahoc;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    private ShareViewModel data_login;
    private Share_dahoc data_dahoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        data_login = new ViewModelProvider(this).get(ShareViewModel.class);
        data_dahoc = new ViewModelProvider(this).get(Share_dahoc.class);
        mainBinding.navBottom.setOnItemSelectedListener(item -> {
            int i = item.getItemId();
            NavController navController = Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView);
            if (i == R.id.action_home) {
                navController.navigate(R.id.action_activitymain_to_fragment_home);
                return true;
            } else if (i == R.id.action_collection) {
                navController.navigate(R.id.action_activitymain_to_fragment_list);
                return true;
            } else if(i ==R.id.action_favorite){
                navController.navigate(R.id.action_activitymain_to_fragment_favorite);
                return true;
            }
            else return true;


        });
    }
    public ShareViewModel getData_login(){
        return data_login;
    }
    public Share_dahoc getData_dahoc(){
        return data_dahoc;
    }
}