package samples.speech.cognitiveservices.microsoft.myapplication.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topic;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.databinding.FragmentHomeBinding;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Fragment_home_ViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Fragment_home extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    Fragment_home_ViewModel fragment_home_viewModel;
    ShareViewModel shareViewModel;
    Share_revise chua_hoc;
    SharedPreferences account;
    List<Topic> topicList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        fragment_home_viewModel = new Fragment_home_ViewModel("Ôn tập", "Hôm nay cần học: 0 từ", "0", "0", "0");
        fragmentHomeBinding.setFragmentHomeViewModel(fragment_home_viewModel);
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.nav_bottom);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        shareViewModel = ((MainActivity) requireActivity()).getData_login();
        chua_hoc = ((MainActivity) requireActivity()).getData_revise();
        account = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        shareViewModel.getData_detail().observe(getViewLifecycleOwner(), detail_info -> {
            if (detail_info != null) {
                fragment_home_viewModel.setTuchuathuoc(detail_info.getChua_thuoc().size() + "");
                fragment_home_viewModel.setTudathuoc(detail_info.getDa_thuoc().size() + "");
                fragment_home_viewModel.setTusapthuoc(detail_info.getSap_thuoc().size() + "");
                if (detail_info.getHoc().size() == 0) {
                    fragmentHomeBinding.layoutLearn.findViewById(R.id.change_topic).setVisibility(View.VISIBLE);
                    fragmentHomeBinding.layoutLearn.findViewById(R.id.icon_change).setVisibility(View.VISIBLE);
                    fragment_home_viewModel.setName("Học từ mới");
                    fragment_home_viewModel.setTuhomnayhoc("");
                    fragmentHomeBinding.layoutLearn.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_learn));
                    getTopic(shareViewModel.getData().getValue());
                } else {
                    fragmentHomeBinding.layoutLearn.findViewById(R.id.change_topic).setVisibility(View.GONE);
                    fragmentHomeBinding.layoutLearn.findViewById(R.id.icon_change).setVisibility(View.GONE);
                    fragment_home_viewModel.setTuhomnayhoc("Hôm nay cần học: " + detail_info.getHoc().size() + " từ");
                    fragment_home_viewModel.setName("Ôn tập");
                    fragmentHomeBinding.layoutLearn.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.background_revise));
                    ImageView imageView = fragmentHomeBinding.layoutLearn.findViewById(R.id.imageview_home);
                    imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.magnifying_glass));
                    fragmentHomeBinding.topic.setText("");
                }
            }
        });
        fragmentHomeBinding.layoutLearn.findViewById(R.id.change_topic).setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(fragmentHomeBinding.getRoot());
            navController.navigate(R.id.action_fragment_home_to_fragment_topic);
            bottomNavigationView.setVisibility(View.GONE);
            shareViewModel.setNav(R.id.action_fragment_topic_to_fragment_home);
        });
        fragmentHomeBinding.buttonLearn.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(fragmentHomeBinding.getRoot());
            if (shareViewModel.getData_detail().getValue() != null) {
                if (shareViewModel.getData_detail().getValue().getHoc().size() > 0) {
                    navController.navigate(R.id.action_fragment_home_to_fragment_learn);
                    bottomNavigationView.setVisibility(View.GONE);
                } else {
                    if (shareViewModel.getChuahoc().getValue() != null && shareViewModel.getChuahoc().getValue().size() > 0) {
                        navController.navigate(R.id.action_fragment_home_to_fragment_study);
                        bottomNavigationView.setVisibility(View.GONE);
                    }
                }
            }
        });
        return fragmentHomeBinding.getRoot();
    }

    public void getTopic(String account) {
        ApiService.apiService.getTopic(account).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Topic>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                fragmentHomeBinding.buttonLearn.setEnabled(false);
                fragmentHomeBinding.shimmerHome.startShimmerAnimation();
                fragmentHomeBinding.shimmerHome.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Topic> topics) {
                topicList = topics;
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                chua_hoc.setList_topic(topicList);
                for (int i = 0; i < topicList.size(); i++) {
                    for (int j = 0; j < topicList.get(i).getChildtopic().size(); j++) {
                        if (chua_hoc.gettopic().getValue() != null) {
                            getChuahoc(account, chua_hoc.gettopic().getValue().get(0));
                            fragmentHomeBinding.topic.setText(chua_hoc.gettopic().getValue().get(0));
                            Glide.with(fragmentHomeBinding.getRoot())
                                    .load(chua_hoc.gettopic().getValue().get(1))
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            fragmentHomeBinding.shimmerHome.stopShimmerAnimation();
                                            fragmentHomeBinding.shimmerHome.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            fragmentHomeBinding.shimmerHome.stopShimmerAnimation();
                                            fragmentHomeBinding.shimmerHome.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .into(fragmentHomeBinding.imageviewHome);
                            i = topicList.size();
                            break;
                        }
                        if (Integer.parseInt(topicList.get(i).getChildtopic().get(j).getSotu_chuahoc()) > 0) {
                            getChuahoc(account, topicList.get(i).getChildtopic().get(j).getChildtopic());
                            fragmentHomeBinding.topic.setText(topicList.get(i).getChildtopic().get(j).getChildtopic());
                            Glide.with(fragmentHomeBinding.getRoot())
                                    .load(topicList.get(i).getChildtopic().get(j).getImage())
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            fragmentHomeBinding.shimmerHome.stopShimmerAnimation();
                                            fragmentHomeBinding.shimmerHome.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            fragmentHomeBinding.shimmerHome.stopShimmerAnimation();
                                            fragmentHomeBinding.shimmerHome.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .into(fragmentHomeBinding.imageviewHome);
                            i = topicList.size();
                            break;
                        }
                    }
                }
            }
        });
    }

    public void getChuahoc(String account, String topic) {
        ApiService.apiService.getStudy_topic(account, topic).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Vocabulary>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Vocabulary> vocabularies) {
                shareViewModel.setShare_chuahoc(vocabularies);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
                fragmentHomeBinding.buttonLearn.setEnabled(true);
            }
        });
    }
}
