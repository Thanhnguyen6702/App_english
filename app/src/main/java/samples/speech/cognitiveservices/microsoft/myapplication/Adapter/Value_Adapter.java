package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Value;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Text_to_voice;

public class Value_Adapter extends RecyclerView.Adapter<Value_Adapter.Value_ViewHolder>{
    public Value_Adapter(List<Value> list_value) {
        this.list_value = list_value;
    }
    public Text_to_voice text_to_voice = new Text_to_voice();
    List<Value> list_value;
    @NonNull
    @Override
    public Value_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_value,parent,false);
        return new Value_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Value_ViewHolder holder, int position) {
            Value value = list_value.get(position);
            if(value==null) return;
            holder.item_value.setText(value.getTienganh());
            holder.mic.setOnClickListener(view->{

            });
            holder.volume.setOnClickListener(view-> text_to_voice.voice(value.getTienganh()));
    }

    @Override
    public int getItemCount() {
        return list_value.size();
    }

    public static class Value_ViewHolder extends RecyclerView.ViewHolder{
        private final TextView item_value;
        private final ImageView mic,volume;
        public Value_ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_value = itemView.findViewById(R.id.item_value);
            mic = itemView.findViewById(R.id.mic);
            volume = itemView.findViewById(R.id.volume_image);
        }
    }
}
