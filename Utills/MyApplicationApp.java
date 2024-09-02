package com.ep.video.player.videoplayer.Utills;

import com.ep.video.player.videoplayer.Activitys.MusicActivity.PlayerActivity;
import com.ep.video.player.videoplayer.Fragments.SongFragment;

public class MyApplicationApp {


    public static SharePrefrenceHelper sharePrefrenceHelper;


    public static void autoCloseApplication(){
        if(PlayerActivity.songServices != null) {
            PlayerActivity.songServices.stopForeground(true);
            PlayerActivity.songServices.mediaPlayer.release();
            PlayerActivity.songServices = null;
            PlayerActivity.songServices.audioManager.abandonAudioFocus(PlayerActivity.songServices);
        }
        System.exit(03);

    }
    public static void setSongPosition(boolean incre) {
        if(PlayerActivity.repeat == false) {
            if (incre) {
                if (SongFragment.songlist.size() - 1 == PlayerActivity.poisition)
                    PlayerActivity.poisition = 0;
                else
                    ++PlayerActivity.poisition;
            } else {
                if (PlayerActivity.poisition == 0)
                    PlayerActivity.poisition = SongFragment.songlist.size() - 1;
                else
                    --PlayerActivity.poisition;
            }
        }
    }
}
