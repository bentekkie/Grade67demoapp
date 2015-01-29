package com.dragonfruit.codinghouse.soundcart;

//© DragonFruit CodingHouse 2015
//Code written by: Benjmain Segall
import android.content.Context;
import android.database.Cursor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import java.io.IOException;

public class sButton extends Button implements MediaPlayer.OnCompletionListener {
    MediaPlayer mediaPlayer;
    Context context;
    Uri data;
    String name;
    int state = 0;
    public sButton(Context context) {
        super(context);
        this.setText("Add");
        this.setMinHeight(dimensions().y/10);
        this.setMinWidth(dimensions().x);
    }
    public void getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = super.getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            } finally {
                if (cursor != null) cursor.close();

            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        name = result;
    }
    public void setState(int newState){
        state=newState;
    }
    public int getState(){
        return state;
    }
    public String getName(){
        return name;
    }
    public void prepareMediaPlayer(Context newContext, Uri newData){
        context = newContext;
        data = newData;
        getFileName(newData);
    }
    public Point dimensions(){
        WindowManager wm = (WindowManager) super.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }
    private void load(){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(context, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(this);
    }
    public void play(){
        load();
        mediaPlayer.start();
    }
    public void stop(){
        mediaPlayer.stop();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        this.setText("Play " +getName());
        setState(1);
    }
}
