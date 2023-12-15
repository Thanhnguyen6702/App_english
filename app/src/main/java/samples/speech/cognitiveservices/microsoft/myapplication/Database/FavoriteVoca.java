package samples.speech.cognitiveservices.microsoft.myapplication.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavoriteVoca")
public class FavoriteVoca {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String English;
    private String Vietnamese;
    private String Phonetic;

    public FavoriteVoca() {
    }

    public FavoriteVoca(String english, String vietnamese, String phonetic) {
        English = english;
        Vietnamese = vietnamese;
        Phonetic = phonetic;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String english) {
        English = english;
    }

    public String getVietnamese() {
        return Vietnamese;
    }

    public void setVietnamese(String vietnamese) {
        Vietnamese = vietnamese;
    }

    public String getPhonetic() {
        return Phonetic;
    }

    public void setPhonetic(String phonetic) {
        Phonetic = phonetic;
    }
}
