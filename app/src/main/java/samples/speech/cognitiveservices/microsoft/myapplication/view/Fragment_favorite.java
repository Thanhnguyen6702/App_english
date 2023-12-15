package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import samples.speech.cognitiveservices.microsoft.myapplication.Adapter.CustomPageAdapter;
import samples.speech.cognitiveservices.microsoft.myapplication.R;

public class Fragment_favorite extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite,container,false);
        BottomNavigationView bottomnav = requireActivity().findViewById(R.id.nav_bottom);
        bottomnav.setVisibility(View.GONE);
        ImageView back = view.findViewById(R.id.back_favorite);
        TabLayout tabLayout = view.findViewById(R.id.tabsFavorite);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewpageFavorite);
        CustomPageAdapter customPageAdapter = new CustomPageAdapter(requireActivity());
        back.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });
        customPageAdapter.addFragmnet(new Vocabulary_Favorite());
        customPageAdapter.addFragmnet(new Topic_Favorite());
        viewPager2.setAdapter(customPageAdapter);
        new TabLayoutMediator(tabLayout,viewPager2,
                (tab,position)->{
                    switch (position){
                        case 0:
                            tab.setText("Từ vựng yêu thích");
                            break;
                        case 1:
                            tab.setText("Chủ đề yêu thích");
                            break;
                        default: break;
                    }
                }).attach();

        return view;
    }

}
