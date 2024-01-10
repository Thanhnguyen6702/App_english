package samples.speech.cognitiveservices.microsoft.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.Vocabulary;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.FavoriteVoca;
import samples.speech.cognitiveservices.microsoft.myapplication.Database.VocabDatabase;
import samples.speech.cognitiveservices.microsoft.myapplication.R;
import samples.speech.cognitiveservices.microsoft.myapplication.Speech.Sound;

public class Flashcard_adapter extends RecyclerView.Adapter<Flashcard_adapter.Flashcard_viewholder> {
    List<Vocabulary> vocabularies;
    Sound playAudio;
    public Flashcard_adapter(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    @NonNull
    @Override
    public Flashcard_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flashcard, parent, false);
        Flashcard_viewholder viewholder = new Flashcard_viewholder(view);
        viewholder.setIsRecyclable(false);
        playAudio = Sound.getInstance(view.getContext());
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Flashcard_viewholder holder, int position) {
        Vocabulary vocabulary = vocabularies.get(position);
        holder.english_f.setText(vocabulary.getTienganh());
        holder.english_b.setText(vocabulary.getTienganh());
        holder.phonetic.setText(vocabulary.getPhienam());
        holder.vietnamese.setText(vocabulary.getTiengviet());
        if (vocabulary.getExample().size() >0) {
            holder.example.setText(vocabulary.getExample().get(0));
        }
        holder.volume.setOnClickListener(view -> {
                playAudio.playAudio(vocabularies.get(position).getTienganh());
        });
        if(VocabDatabase.getInstance(holder.itemView.getContext()).daoVocab().checkVocab(vocabulary.getTienganh())>0){
            holder.checkFavorite = true;
            holder.favorite.setImageResource(R.drawable.like);
        }
        else {
            holder.checkFavorite = false;
            holder.favorite.setImageResource(R.drawable.unlike);
        }
        holder.favorite.setOnClickListener(view -> {
            if(holder.checkFavorite){
                holder.checkFavorite = false;
                holder.favorite.setImageResource(R.drawable.unlike);
                VocabDatabase.getInstance(holder.itemView.getContext()).daoVocab().removeTopic(vocabulary.getTienganh());
            }
            else {
                holder.checkFavorite = true;
                holder.favorite.setImageResource(R.drawable.like);
                VocabDatabase.getInstance(holder.itemView.getContext()).daoVocab().insertTopic(new FavoriteVoca(vocabulary.getTienganh(),vocabulary.getTiengviet(),vocabulary.getPhienam()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vocabularies.size();
    }

    public static class Flashcard_viewholder extends RecyclerView.ViewHolder {
        TextView english_f, english_b, phonetic, vietnamese, example;
        ImageView favorite, volume;
        Boolean checkFavorite;

        public Flashcard_viewholder(@NonNull View itemView) {
            super(itemView);
            english_f = itemView.findViewById(R.id.english_f);
            english_b = itemView.findViewById(R.id.english_b);
            phonetic = itemView.findViewById(R.id.phonetic_b);
            vietnamese = itemView.findViewById(R.id.vietnamese_b);
            example = itemView.findViewById(R.id.example_b);
            favorite = itemView.findViewById(R.id.favorite);
            volume = itemView.findViewById(R.id.volume_flashcard);
            itemView.setOnClickListener(view -> {
                    Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.flip_flashcard);
                    view.setAnimation(animation);
                    if (english_f.getVisibility() == View.VISIBLE) {
                        english_f.setVisibility(View.GONE);
                        english_b.setVisibility(View.VISIBLE);
                        phonetic.setVisibility(View.VISIBLE);
                        vietnamese.setVisibility(View.VISIBLE);
                        example.setVisibility(View.VISIBLE);
                        volume.setVisibility(View.VISIBLE);
                    } else {
                        english_f.setVisibility(View.VISIBLE);
                        english_b.setVisibility(View.GONE);
                        phonetic.setVisibility(View.GONE);
                        vietnamese.setVisibility(View.GONE);
                        example.setVisibility(View.GONE);
                        volume.setVisibility(View.GONE);
                    }
            });
        }
    }
}
