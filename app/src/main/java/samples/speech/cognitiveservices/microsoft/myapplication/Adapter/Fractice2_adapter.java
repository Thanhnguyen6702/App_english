package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.FavoriteVoca;
import samples.speech.cognitiveservices.microsoft.myapplication.Dialog.DialogPron;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Sound;

public class Fractice2_adapter extends RecyclerView.Adapter<Fractice2_adapter.Fractice2_viewholder> {
    List<FavoriteVoca> vocabularies;
    Sound sound;

    public Fractice2_adapter(List<FavoriteVoca> vocabularies,Sound sound){
        this.vocabularies = vocabularies;
        this.sound = sound;
    }
    @NonNull
    @Override
    public Fractice2_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pratice2,parent,false);
        return new Fractice2_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Fractice2_viewholder holder, int position) {
                holder.english.setText(vocabularies.get(position).getEnglish());
                holder.phonetic.setText(vocabularies.get(position).getPhonetic());
                holder.vietnamese.setText(vocabularies.get(position).getVietnamese());
                holder.volume.setOnClickListener(view -> sound.playAudio(vocabularies.get(position).getEnglish()));
                holder.mic.setOnClickListener(view -> {
                    DialogPron dialogPron = new DialogPron(holder.itemView.getContext());
                    dialogPron.show();
                });
    }

    @Override
    public int getItemCount() {
        return vocabularies.size();
    }

    public static class Fractice2_viewholder extends RecyclerView.ViewHolder {
        TextView english,phonetic,vietnamese;
        ImageView volume,mic;
        public Fractice2_viewholder(@NonNull View itemView) {
            super(itemView);
            english = itemView.findViewById(R.id.english_practice2);
            phonetic = itemView.findViewById(R.id.phonetic_pratice2);
            vietnamese = itemView.findViewById(R.id.vietnamese_pratice2);
            volume = itemView.findViewById(R.id.volume_pratice2);
            mic = itemView.findViewById(R.id.mic_practice2);
        }
    }
}
