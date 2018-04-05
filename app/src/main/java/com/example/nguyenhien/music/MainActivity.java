package com.example.nguyenhien.music;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textSong,textTimeSong, textTimeEnd;
    SeekBar sbSong;
    ImageView imgDisc;
    ImageButton btnFowards, btnRewind, btnPlay,btnStop;
    ArrayList<Song> list;
    int index=0;
    MediaPlayer mediaPlayer;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        CreateListMusic();
        InitMediaPlayer();
        animation= AnimationUtils.loadAnimation(this,R.anim.disc_rotate);
        btnRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if(index < 0){
                    index=list.size()-1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                InitMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause_button);
                setTimeSong();
                UpdateTimeSong();
                imgDisc.startAnimation(animation);
            }
        });
        btnFowards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if(index > list.size()-1){
                    index=0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                InitMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause_button);
                setTimeSong();
                UpdateTimeSong();
                imgDisc.startAnimation(animation);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play_button);
                InitMediaPlayer();
                imgDisc.clearAnimation();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play_button);
                    imgDisc.clearAnimation();
                }
                else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause_button);
                    imgDisc.startAnimation(animation);
                }
                setTimeSong();
                UpdateTimeSong();


            }
        });

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sbSong.getProgress());
            }
        });
    }

    public void UpdateTimeSong(){
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat formatTime=new SimpleDateFormat("mm:ss");
                textTimeSong.setText(formatTime.format(mediaPlayer.getCurrentPosition()));
                sbSong.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        index++;
                        if(index > list.size()-1){
                            index=0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        InitMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause_button);
                        setTimeSong();
                        UpdateTimeSong();
                        imgDisc.startAnimation(animation);
                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }
    public void setTimeSong(){
        SimpleDateFormat formatTime=new SimpleDateFormat("mm:ss");
        textTimeEnd.setText(formatTime.format(mediaPlayer.getDuration()));

        sbSong.setMax(mediaPlayer.getDuration());
    }
    public void InitMediaPlayer(){
        mediaPlayer=MediaPlayer.create(MainActivity.this,list.get(index).getFile());
        textSong.setText(list.get(index).getTitle());
    }
    public void CreateListMusic(){
        list=new ArrayList<>();
        list.add(new Song("Điều Anh Biết",R.raw.dieu_anh_biet));
        list.add(new Song("Gọi Tên Em Trong Đêm",R.raw.goi_ten_em_trong_dem));
        list.add(new Song("Hóa Ra Em Vẫn Chờ",R.raw.hoa_ra_em_van_cho));
        list.add(new Song("Ngắm Hoa Lệ Rơi",R.raw.ngam_hoa_le_roi));
        list.add(new Song("Nhớ Kẻ Phụ Tình",R.raw.nho_ke_phu_tinh));
    }
    public void Init(){
        textSong        =(TextView)findViewById(R.id.textViewSong);
        textTimeSong    =(TextView)findViewById(R.id.timeSong);
        textTimeEnd     =(TextView)findViewById(R.id.timeEnd);
        sbSong          =(SeekBar)findViewById(R.id.seekBar);
        btnFowards      =(ImageButton)findViewById(R.id.imageButtonFowards);
        btnRewind       =(ImageButton)findViewById(R.id.imageButtonRewind);
        btnPlay         =(ImageButton)findViewById(R.id.imageButtonPlay);
        btnStop         =(ImageButton)findViewById(R.id.imageButtonStop);
        imgDisc         =(ImageView) findViewById(R.id.imageViewDisc);
    }
}
