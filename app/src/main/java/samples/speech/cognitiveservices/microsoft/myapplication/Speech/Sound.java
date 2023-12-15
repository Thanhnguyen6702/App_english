package samples.speech.cognitiveservices.microsoft.myapplication.Speech;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import samples.speech.cognitiveservices.microsoft.myapplication.CallAPI.ApiService;

public class Sound {
    private static Sound instance;
    private Context context;
    private String word;
    private MediaPlayer mediaPlayer;
    public Sound(Context context){
        this.context = context;
    }
    public static Sound getInstance(Context context){
        if(instance == null){
            instance = new Sound(context);
        }
        return instance;
    }
    public void getSound(boolean isPlay){
        ApiService.apiService.getSound(word).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResponseBody responseBody) {
                saveAudioFile(responseBody);
                if (isPlay){
                    playAudio(word);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void saveAudioFile(ResponseBody responseBody) {
        try {
            File cacheDir = context.getCacheDir();
            File audio = new File(cacheDir,"word.mp3");
            FileOutputStream fileOutputStream = new FileOutputStream(audio);
            fileOutputStream.write(responseBody.bytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void playAudio(String text){
        if(text.equalsIgnoreCase(word)) {
            try {
                File cacheDir = context.getCacheDir();
                File audioFile = new File(cacheDir, "word.mp3");

                if (audioFile.exists()) {
                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build());
                    }
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(audioFile.getAbsolutePath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } else {
                    // File not found in cache
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            word = text;
            getSound(true);
        }
    }

    public void setWord(String word) {
        this.word = word;
        getSound(false);
    }
}
