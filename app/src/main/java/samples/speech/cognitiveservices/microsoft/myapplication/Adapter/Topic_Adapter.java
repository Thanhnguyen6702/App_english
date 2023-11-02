package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topic;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.view.MainActivity;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.ShareViewModel;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Topic_Adapter extends RecyclerView.Adapter<Topic_Adapter.Topic_ViewHolder> {
    List<Topic> listTopic;
    List<Topic_hori_Adapter> topic_hori_adapterList = new ArrayList<>();
    View view_parent;

    public Topic_Adapter(List<Topic> listTopic, OnclickItemadapter onclickItemadapter, View view_parent) {
        this.listTopic = listTopic;
        this.view_parent = view_parent;
        for (int i = 0; i < listTopic.size(); i++) {
            topic_hori_adapterList.add(new Topic_hori_Adapter(listTopic.get(i), onclickItemadapter, view_parent));
        }
    }

    public interface OnclickItemadapter {
        int Onclick();
    }

    @NonNull
    @Override
    public Topic_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new Topic_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Topic_ViewHolder holder, int position) {
        List<Topic_hori_Adapter> hori_adapters = topic_hori_adapterList;
        List<Topic> topics = listTopic;
        holder.topic.setText(topics.get(position).getTopic());
        holder.recyclerView.setAdapter(hori_adapters.get(position));
    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }

    public static class Topic_ViewHolder extends RecyclerView.ViewHolder {
        TextView topic;
        RecyclerView recyclerView;

        public Topic_ViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topic_parent);
            recyclerView = itemView.findViewById(R.id.recycler_vertical);
            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }
    }
}

class Topic_hori_Adapter extends RecyclerView.Adapter<Topic_hori_Adapter.Topic_hori_Viewholder> {
    Topic topic;
    Topic_Adapter.OnclickItemadapter onclickItemadapter;
    View view_parent;

    public Topic_hori_Adapter(Topic topic, Topic_Adapter.OnclickItemadapter onclickItemadapter, View view_parent) {
        this.topic = topic;
        this.onclickItemadapter = onclickItemadapter;
        this.view_parent = view_parent;
    }

    @NonNull
    @Override
    public Topic_hori_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_hori, parent, false);
        return new Topic_hori_Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Topic_hori_Viewholder holder, int position) {
        int i = position + 1;
        holder.tenchude.setText(i + "." + topic.getChildtopic().get(position).getChildtopic());
        int sotuchuahoc = Integer.parseInt(topic.getChildtopic().get(position).getSotu_chuahoc());
        int sotu = Integer.parseInt(topic.getChildtopic().get(position).getSotu());
        int phantram = (int) ((sotu - sotuchuahoc) * 100.0 / sotu);
        holder.progressBar.setProgress(phantram);
        holder.sotu.setText(sotu + "");
        holder.shimmerFrameLayout.startShimmerAnimation();
        holder.shimmerFrameLayout.setVisibility(View.VISIBLE);
        Glide.with(holder.itemView)
                .load(topic.getChildtopic().get(position).getImage())
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
                .into(holder.imageView);
        holder.itemView.setOnClickListener(view -> {
            if (onclickItemadapter.Onclick() == R.id.action_fragment_topic_to_fragment_home) {
                Share_revise share_revise = ((MainActivity) holder.itemView.getContext()).getData_revise();
                if (Integer.parseInt(topic.getChildtopic().get(position).getSotu_chuahoc()) > 0) {
                    share_revise.setTopic(new ArrayList<>(Arrays.asList(topic.getChildtopic().get(position).getChildtopic(), topic.getChildtopic().get(position).getImage())));
                    NavController navController = Navigation.findNavController(view_parent);
                    navController.navigate(R.id.action_fragment_topic_to_fragment_home);
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Bạn đã hoàn thành chủ đề này rồi!", Toast.LENGTH_SHORT).show();
                }
            } else {
                ShareViewModel shareViewModel = ((MainActivity) holder.itemView.getContext()).getData_login();
                if (Integer.parseInt(topic.getChildtopic().get(position).getSotu_chuahoc()) > 0) {
                    shareViewModel.setTopic_phatam(topic.getChildtopic().get(position).getChildtopic());
                    NavController navController = Navigation.findNavController(view_parent);
                    navController.navigate(R.id.action_fragment_topic_to_fragment_voice);
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Bạn đã hoàn thành chủ đề này rồi!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return topic.getChildtopic().size();
    }

    public static class Topic_hori_Viewholder extends RecyclerView.ViewHolder {
        TextView tenchude, sotu;
        ImageView imageView;
        ProgressBar progressBar;
        ShimmerFrameLayout shimmerFrameLayout ;

        public Topic_hori_Viewholder(@NonNull View itemView) {
            super(itemView);
            tenchude = itemView.findViewById(R.id.name_topic);
            imageView = itemView.findViewById(R.id.image_topic);
            progressBar = itemView.findViewById(R.id.percent_learn);
            shimmerFrameLayout = itemView.findViewById(R.id.shimmer_topic);
            sotu = itemView.findViewById(R.id.sotu);
        }
    }

}
