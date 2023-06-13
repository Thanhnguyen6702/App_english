package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.ActivityMainBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    private ShareViewModel data_login;
    private Share_revise data_revise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        data_login = new ViewModelProvider(this).get(ShareViewModel.class);
        data_revise = new ViewModelProvider(this).get(Share_revise.class);
        mainBinding.navBottom.setOnItemSelectedListener(item -> {
            int i = item.getItemId();
            NavController navController = Navigation.findNavController(MainActivity.this, R.id.fragmentContainerView);
            int currentDestinationId = navController.getCurrentDestination().getId();

            if (i == R.id.action_home) {
                if (currentDestinationId != R.id.fragment_home) {
                    navController.navigate(R.id.action_activitymain_to_fragment_home);
                }
                return true;
            } else if (i == R.id.action_collection) {
                if (currentDestinationId != R.id.fragment_list) {
                    navController.navigate(R.id.action_activitymain_to_fragment_list);
                }
                return true;
            } else if (i == R.id.action_voice) {
                if (currentDestinationId != R.id.fragment_phatam) {
                    navController.navigate(R.id.action_activitymain_to_fragment_phatam);
                }
                return true;
            } else {
                if (currentDestinationId != R.id.fragment_logout) {
                    navController.navigate(R.id.action_activitymain_to_fragment_logout);
                }
                return true;
            }
        });
    }

    public ShareViewModel getData_login() {
        return data_login;
    }

    public Share_revise getData_revise() {
        return data_revise;
    }

    @Override
    public void onBackPressed() {

    }
}