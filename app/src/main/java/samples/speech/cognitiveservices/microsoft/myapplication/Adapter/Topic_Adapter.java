package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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

    public Topic_Adapter(List<Topic> listTopic, OnclickItemadapter onclickItemadapter) {
        this.listTopic = listTopic;
        for (int i = 0; i < listTopic.size(); i++) {
            topic_hori_adapterList.add(new Topic_hori_Adapter(listTopic.get(i), onclickItemadapter));
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

    public Topic_hori_Adapter(Topic topic, Topic_Adapter.OnclickItemadapter onclickItemadapter) {
        this.topic = topic;
        this.onclickItemadapter = onclickItemadapter;
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
        Glide.with(holder.itemView)
                .load(topic.getChildtopic().get(position).getImage())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(view -> {
            if( onclickItemadapter.Onclick()==1) {
                Share_revise share_revise = ((MainActivity) holder.itemView.getContext()).getData_revise();
                share_revise.setTopic(new ArrayList<>(Arrays.asList(topic.getChildtopic().get(position).getChildtopic(), topic.getChildtopic().get(position).getImage())));
            }
            else{
                ShareViewModel shareViewModel = ((MainActivity) holder.itemView.getContext()).getData_login();
                shareViewModel.setTopic_phatam(topic.getChildtopic().get(position).getChildtopic());
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

        public Topic_hori_Viewholder(@NonNull View itemView) {
            super(itemView);
            tenchude = itemView.findViewById(R.id.name_topic);
            imageView = itemView.findViewById(R.id.image_topic);
            progressBar = itemView.findViewById(R.id.percent_learn);
            sotu = itemView.findViewById(R.id.sotu);
        }
    }

}
