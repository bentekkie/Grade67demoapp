package com.dragonfruit.codinghouse.soundcart;
//© DragonFruit CodingHouse 2015
//Code written by: Benjmain Segall
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import java.io.IOException;


public class MainActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {
    GridLayout grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setTitle("Sound Cart Demo App");
        super.onCreate(savedInstanceState);
        ScrollView frame = new ScrollView(this);
        grid = new GridLayout(this);
        grid.setColumnCount(1);
        addButton();
        frame.setSmoothScrollingEnabled(true);
        frame.addView(grid);
        super.setContentView(frame);
    }
    public void addButton(){
        sButton button = new sButton(this);
        button.setOnClickListener(this);
        button.setOnLongClickListener(this);
        grid.addView(button);
    }
    @Override
    public void onClick(View v) {
        sButton temp = (sButton)v;
        switch(temp.getState()){
            case sButton.Add:
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose Sound"), 1);
                break;
            case sButton.Play:
                temp.play();
                temp.setText("Playing "+ temp.getName());
                temp.setState(sButton.Playing);
                break;
            case sButton.Playing:
                temp.stop();
                temp.setText("Play "+ temp.getName());
                temp.setState(sButton.Play);
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode==RESULT_OK)
        {
            Uri selectedAudio = data.getData();
            MediaPlayer mediaPlayer = new MediaPlayer();
            Boolean dataOk = true;
            try {
                mediaPlayer.setDataSource(getApplicationContext(), selectedAudio);
            } catch (IOException e) {
                dataOk = false;
                Toast toast = Toast.makeText(getApplicationContext(), "Unable to open audio", Toast.LENGTH_SHORT);
                toast.show();
            }
            if(selectedAudio != null && dataOk) {
                sButton temp = ((sButton) grid.getChildAt(grid.getChildCount() - 1));
                temp.prepareMediaPlayer(getApplicationContext(),selectedAudio);
                temp.setText("Play "+temp.getName());
                temp.setState(sButton.Play);
                addButton();
            }

        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(((sButton)v).getState() != sButton.Add){
        v.setVisibility(View.GONE);
        grid.removeView(v);
        return true;
        }else return false;

    }
}
