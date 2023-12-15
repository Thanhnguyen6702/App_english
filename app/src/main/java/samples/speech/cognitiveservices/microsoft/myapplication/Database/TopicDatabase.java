package samples.speech.cognitiveservices.microsoft.myapplication.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FavoriteTopic.class},version = 2)
public abstract class TopicDatabase extends RoomDatabase {
    static Migration migration_from_1_to_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("ALTER TABLE FavoriteTopic ADD COLUMN image TEXT");
        }
    };
    private static final String DATABASE_NAME = "topic.db";
    private  static TopicDatabase instance;
    public static synchronized TopicDatabase getInstance(Context context){
        if (instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),TopicDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract DaoTopic daoTopic();
}
