package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import samples.speech.cognitiveservices.microsoft.myapplication.R;


public class Quiz_above_adapter extends RecyclerView.Adapter<Quiz_above_adapter.Quiz_above_Viewholder> {
    private char[] words;
    private char[] words_correct;
    private int pos;
    private Context context;
    private ItemClick itemClick;
    public Quiz_above_adapter(ItemClick itemClick){
        this.itemClick = itemClick;
    }
    public interface ItemClick{
        void charaterClick(int p);
        void result(boolean result);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(char[] words, int pos){
        this.words = words;
        this.pos = pos;
        if(pos == words.length){
            if(String.valueOf(words).equals(String.valueOf(words_correct))){
                itemClick.result(true);
            }
        }
        notifyDataSetChanged();
    }
    public void setWords_correct(char[] words_correct){
        this.words_correct = words_correct;
    }
    @NonNull
    @Override
    public Quiz_above_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text1,parent,false);
        context = parent.getContext();
        return new Quiz_above_Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Quiz_above_Viewholder holder, int position) {
        holder.textView.setText(String.valueOf(words[position]));
        if(pos<=words.length-1) {
            holder.textView.setOnClickListener(view1 -> {
                itemClick.charaterClick(position);
            });
            if (position == pos) {
                setBackground(holder.textView);
            } else {
                removeBackground(holder.textView);
            }
        }
        else {
                setResult(holder.textView,position);
        }
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
    public void setBackground(TextView textView){
        textView.setBackground(ContextCompat.getDrawable(context,R.drawable.background_item_text1_select));
        textView.setTextColor(ContextCompat.getColor(context,R.color.teal_200));
    }
    private void removeBackground(TextView textView) {
        textView.setBackground(ContextCompat.getDrawable(context,R.drawable.background_item_text1));
        textView.setTextColor(ContextCompat.getColor(context,R.color.teal_200));
    }
    private void setResult(TextView textView,int p) {
        if(words[p] == words_correct[p]){
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.background_item_text1_true));
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
        }
        else {
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.background_item_text1_false));
            textView.setTextColor(ContextCompat.getColor(context,R.color.black));
        }
    }

}
