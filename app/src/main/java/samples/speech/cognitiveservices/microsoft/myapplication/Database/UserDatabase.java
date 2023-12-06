//package samples.speech.cognitiveservices.microsoft.myapplication.Database;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//@Database(entities = {Database.class},version = 1)
//
//public abstract class UserDatabase extends RoomDatabase {
//    public static final String DATABASE_NAME = "data.db";
//    private static UserDatabase userDatabase;
//    public static UserDatabase getInstance(Context context){
//        if (userDatabase == null){
//            userDatabase = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class,DATABASE_NAME)
//                    .allowMainThreadQueries()
//                    .build();
//        }
//        return userDatabase;
//    }
//    public abstract DaoUser daoUser();
//
//}
