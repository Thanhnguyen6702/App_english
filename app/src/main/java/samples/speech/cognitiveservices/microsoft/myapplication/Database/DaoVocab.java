package samples.speech.cognitiveservices.microsoft.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoVocab {
    // FavoriteTopic
    @Insert
    void insertTopic(FavoriteVoca voab);
    @Query("DELETE FROM FAVORITEVOCA WHERE English =:english")
    void removeTopic(String english);
    @Query("SELECT * FROM FavoriteVoca")
    List<FavoriteVoca> getListVocabFavorite();
    @Query("SELECT COUNT(*) FROM FavoriteVoca WHERE English = :english")
    int checkVocab(String english);
}
