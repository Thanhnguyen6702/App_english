package samples.speech.cognitiveservices.microsoft.myapplication.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {FavoriteVoca.class},version = 1)
public abstract class VocabDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "vocab.db";
    private static VocabDatabase instance;
    public static VocabDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), VocabDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract DaoVocab daoVocab();
}
