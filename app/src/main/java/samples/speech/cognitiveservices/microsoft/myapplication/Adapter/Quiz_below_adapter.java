package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import samples.speech.cognitiveservices.microsoft.myapplication.R;


public class Quiz_below_adapter extends RecyclerView.Adapter<Quiz_below_adapter.Quiz_above_Viewholder> {
    private char[] words;
    private ItemClick itemClick;
    public Quiz_below_adapter(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    public interface ItemClick{
        void charaterClick(char c);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(char[] words){
        this.words = words;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Quiz_above_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text2,parent,false);
        return new Quiz_above_Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Quiz_above_Viewholder holder, int position) {
        holder.textView.setText(String.valueOf(words[position]));
        holder.textView.setOnClickListener(view1 -> {
            itemClick.charaterClick(words[position]);
        });
    }

    @Override
    public int getItemCount() {
        return words.length;
    }

    public static class Quiz_above_Viewholder extends RecyclerView.ViewHolder{
        TextView textView;
        public Quiz_above_Viewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.characterTextView);
        }
    }

}
