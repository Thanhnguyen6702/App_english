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

public class Value_Adapter extends RecyclerView.Adapter<Value_Adapter.Value_ViewHolder>{
    List<Topic> list_topic;
    Share_revise share_data;
    View view;
    public Value_Adapter(List<Topic> list_topic,View view) {
        this.list_topic = list_topic;
        this.view = view;
        share_data = ((MainActivity) view.getContext()).getData_revise();
    }
    @NonNull
    @Override
    public Value_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic2,parent,false);
        return new Value_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Value_ViewHolder holder, int position) {
            Topic topic = list_topic.get(position);
            if(topic==null) return;
            holder.item_topic.setText(topic.getTopic());
            holder.item_topic.setOnClickListener(view1 -> {
                share_data.set_topic_practice(topic);
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_fragment_list_to_fragment_practice);
            });
    }

    @Override
    public int getItemCount() {
        return list_topic.size();
    }

    public static class Value_ViewHolder extends RecyclerView.ViewHolder{
        private final TextView item_topic;
        public Value_ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_topic = itemView.findViewById(R.id.textviewtopic);
        }
    }


}
