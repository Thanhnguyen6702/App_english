package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.FavoriteTopic;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.TopicDatabase;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.view.MainActivity;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;

public class Fractice_adapter extends RecyclerView.Adapter<Fractice_adapter.Fractice_viewHolder> {
    List<FavoriteTopic> topicList;
    ShareViewModel shareViewModel;
    View view;
    public interface ClickDetail{
        void moveToFractice2();
    }
    ClickDetail clickDetail;
    public Fractice_adapter(View view,ClickDetail clickDetail) {
        this.view = view;
        this.clickDetail = clickDetail;
        shareViewModel = ((MainActivity) view.getContext()).getData_login();
    }
    public void setData(List<FavoriteTopic> topicList){
        this.topicList = topicList;
    }
    @NonNull
    @Override
    public Fractice_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_practice, parent, false);
        return new Fractice_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Fractice_viewHolder holder, int position) {
        holder.shimmerFrameLayout.startShimmerAnimation();
        holder.shimmerFrameLayout.setVisibility(View.VISIBLE);
        Glide.with(holder.imgTopic)
                .load(topicList.get(position).getImage())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.shimmerFrameLayout.stopShimmerAnimation();
                        holder.shimmerFrameLayout.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.shimmerFrameLayout.stopShimmerAnimation();
                        holder.shimmerFrameLayout.setVisibility(View.GONE);

                        return false;
                    }
                })
                .into(holder.imgTopic);
        holder.textTopic.setText(topicList.get(position).getTopic());
        if(TopicDatabase.getInstance(view.getContext()).daoTopic().checkTopic(topicList.get(position).getTopic())>0){
            holder.favorite.setImageResource(R.drawable.like);
            holder.checkFavorite = true;
        }else {
            holder.favorite.setImageResource(R.drawable.unlike);
            holder.checkFavorite = false;
        }
        holder.favorite.setOnClickListener(view -> holder.setFavorite(holder.favorite,topicList.get(position)));
        holder.detail.setOnClickListener(view -> {
            getVocabulary(topicList.get(position).getTopic());
            clickDetail.moveToFractice2();
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public static class Fractice_viewHolder extends RecyclerView.ViewHolder {
        ImageView imgTopic;
        TextView textTopic;
        ImageView favorite;
        ShimmerFrameLayout shimmerFrameLayout;
        Button detail;
        Boolean checkFavorite ;

        public Fractice_viewHolder(@NonNull View itemView) {
            super(itemView);
            imgTopic = itemView.findViewById(R.id.img_fractice);
            textTopic = itemView.findViewById(R.id.topic_practice);
            favorite = itemView.findViewById(R.id.favorite);
            detail = itemView.findViewById(R.id.detail_practice);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer_practice);
        }
        private void setFavorite(ImageView imageView, FavoriteTopic topic){
            if(!checkFavorite){
                imageView.setImageResource(R.drawable.like);
                TopicDatabase.getInstance(itemView.getContext()).daoTopic().insertTopic(topic);
            }
            else {
                imageView.setImageResource(R.drawable.unlike);
                TopicDatabase.getInstance(imageView.getContext()).daoTopic().removeTopic(topic.getTopic());
            }
            checkFavorite = !checkFavorite;
        }
    }

    private void getVocabulary(String topic) {
        ApiService.apiService.getVocabulary(topic).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Vocabulary>>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Vocabulary> vocabularies) {
                Pair<String,List<Vocabulary>> vocabulary = new Pair<>(topic,vocabularies) ;
                shareViewModel.setShare_vocabulary(vocabulary);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
