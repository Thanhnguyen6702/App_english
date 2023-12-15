package samples.speech.cognitiveservices.microsoft.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoTopic {
    @Insert
    void insertTopic(FavoriteTopic topic);
    @Query("DELETE FROM FavoriteTopic WHERE Topic = :topic")
    void removeTopic(String topic);
    @Query("SELECT * FROM FavoriteTopic")
    List<FavoriteTopic> getListTopicFavorite();
    @Query("SELECT COUNT(*) FROM FavoriteTopic WHERE Topic = :topic")
    int checkTopic(String topic);
}
