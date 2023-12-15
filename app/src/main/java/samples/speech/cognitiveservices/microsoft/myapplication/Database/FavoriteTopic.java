package samples.speech.cognitiveservices.microsoft.myapplication.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavoriteTopic")
public class FavoriteTopic {
    private String Topic;
    private String image;
    @PrimaryKey(autoGenerate = true)
    private int ID;
    public FavoriteTopic(){}

    public FavoriteTopic(String topic, String image) {
        Topic = topic;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}