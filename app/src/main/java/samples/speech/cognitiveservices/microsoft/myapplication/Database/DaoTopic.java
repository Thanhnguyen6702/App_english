package samples.speech.cognitiveservices.microsoft.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoUser {

    // FavoriteTopic
    @Query("INSERT INTO favoriteTopic (Topic) VALUES (:topic)")
    void insertTopic(String topic);
    @Query("DELETE FROM favoriteTopic WHERE Topic = :topic")
    void removeTopic(String topic);
    @Query("SELECT Topic FROM favoriteTopic")
    List<String> getListTopicFavorite();
}
