package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.FragmentHomeBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Fragment_home_ViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fragment_home extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    Fragment_home_ViewModel fragment_home_viewModel;
    ShareViewModel shareViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        fragment_home_viewModel = new Fragment_home_ViewModel("Ôn tập", "Hôm nay cần học: 0 từ", "0", "0", "0");
        fragmentHomeBinding.setFragmentHomeViewModel(fragment_home_viewModel);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_bottom);
        bottomNavigationView.setVisibility(View.VISIBLE);
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        shareViewModel.getData_detail().observe(getViewLifecycleOwner(), detail_info -> {
            if (detail_info != null) {
                fragment_home_viewModel.setTuchuathuoc(detail_info.getChua_thuoc().size() + "");
                fragment_home_viewModel.setTudathuoc(detail_info.getDa_thuoc().size() + "");
                fragment_home_viewModel.setTusapthuoc(detail_info.getSap_thuoc().size() + "");
                fragment_home_viewModel.setTuhomnayhoc("Hôm nay cần học: " + detail_info.getHoc().size() + " từ");
                if (detail_info.getHoc().size() == 0) fragment_home_viewModel.setName("Học từ mới");
                else fragment_home_viewModel.setName("Ôn tập");
            }
        });

        fragmentHomeBinding.buttonLearn.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(fragmentHomeBinding.getRoot());
            if (shareViewModel.getData_detail().getValue() != null) {
                if (shareViewModel.getData_detail().getValue().getHoc().size() > 0) {
                    navController.navigate(R.id.action_fragment_home_to_fragment_learn);
                } else {
                    shareViewModel.setShare_chuahoc(shareViewModel.getData_detail().getValue().getChua_hoc());
                    if(shareViewModel.getData_detail().getValue().getChua_hoc().size()>0)
                    navController.navigate(R.id.action_fragment_home_to_fragment_study);
                }
            }
            bottomNavigationView.setVisibility(View.GONE);

        });
        return fragmentHomeBinding.getRoot();
    }

}
