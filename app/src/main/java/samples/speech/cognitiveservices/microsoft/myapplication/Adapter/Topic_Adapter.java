package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Topic;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.view.MainActivity;
import samples.speech.cognitiveservices.microsoft.myapplication.viewmodel.Share_revise;

public class Topic_Adapter extends RecyclerView.Adapter<Topic_Adapter.Topic_ViewHolder> {
    List<Topic> listTopic;
    NavController navController;
    public Topic_Adapter(List<Topic> listTopic,NavController navController){
        this.listTopic = listTopic;
        this.navController = navController;
    }
    @NonNull
    @Override
    public Topic_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic,parent,false);
        return new Topic_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Topic_ViewHolder holder, int position) {
        List<Topic> topic = listTopic;
        holder.topic.setText(topic.get(position).getTopic());
        holder.topic.setOnClickListener(v->{
            Share_revise share_revise = ((MainActivity) holder.itemView.getContext()).getData_revise();
            share_revise.setTopic(topic.get(position).getTopic());
            navController.popBackStack();
        });
    }

    @Override
    public int getItemCount() {
        return listTopic.size();
    }
    public static class Topic_ViewHolder extends RecyclerView.ViewHolder {
        TextView topic;
        public Topic_ViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.name_topic);

        }
    }
}
