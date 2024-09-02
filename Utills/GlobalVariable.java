package com.ep.video.player.videoplayer.Utills;

import com.ep.video.player.videoplayer.Models.MusicModels.MusicModel;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariable {
    public static boolean isDirectory = false;
    public static String iDirecotryName = "";
    public static List<MusicModel> playlistArray = new ArrayList<>();
    public static List<MusicModel> recentAddedPlaylist = new ArrayList<>();
    public static List<MusicModel> recentlyPlayed = new ArrayList<>();
    public static List<MusicModel> artistSongList = new ArrayList<>();
    public static final int iSelectdPos = 0;
    public static int iPlaylistSongPlayPostion = -1;
    public static boolean fromRecentAddedPlay = false;
    public static final String RECENTADDMORE = "Recently Add";
    public static final String RECENTPLAYMORE = "Recently Played";
    public static final String PLAYALL = "PLAYALL";
    public static final String SUFFLEALL = "SUFFLEALL";
    public static final String REMOVE = "Remove Playlist";
    public static final String FAVORITES = "FavouriteVideo";
    public static final String PLAYLISTSONG = "PlaylistSong";
    public static final String PLAYLIST = "Playlist";
    public static final String VIDEOPLAYLIST = "VideoPlaylist";
    public static final String PASSWORDTYPE = "PASSWORDTYPE";
    public static final String PASSWORD = "PASSWORD";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String LANGUAGESELECT = "LANGUAGESELECT";
    public static final String PATTERN = "PATTERN";
    public static final String FINGERPRINT = "FINGERPRINT";
    public static final String PLAYLISTNAME = "PlaylistName";
    public static final String FROMRECENT = "fromrecent";
    public static final String RECENTPLAYED = "recentplayed";
    public static final String MYACTION = "MyAction";
    public static final String FROM = "From";
    public static final String STORAGE = "STORAGE";
    public static final String NAME = "From";
    public static final String ARRAYLIST = "arraylist";
    public static final String POSITION = "position";
    public static final String ID = "id";
    public static final String VIDEO = "video";
    public static final String PHOTO = "photo";
    public static final String SNIPPET = "snippet";
    public static final String CONTENT = "contentDetails,statistics";
    public static final String RELEVANCE = "relevance";
    public static final String MAXRESULT = "20";
    public static final String IN = "IN";
    public static final String FBWATCH = "FBWATCH";
    public static final String INSTAGRAM = "INSTAGRAM";
    public static final String FACEBOOK = "FACEBOOK";
    public static final String GOOGLE = "GOOGLE";
    public static final String YOUTUBE = "YOUTUBE";
    public static final String AMAZONE = "AMAZONE";
    public static final String GMAIL = "GMAIL";
    public static final String WHATSAPP = "WHATSAPP";
    public static final String DAILLYM = "DAILLYM";
    public static final String TWITTER = "TWITTER";
    public static final String VIMEO = "VIMEO";
    public static final String MORE = "MORE";

    public static final String IsPasswordSet = "IsPasswordSet";

//    public static void overRidingTrasition(Activity activity, Class<?> cls)
//    {
//                activity.startActivity(new Intent(activity,cls));
//                activity.overridePendingTransition();
//    }

    public  enum PlayType {
        RECENTADDMORE,
        RECENTPLAYEDMORE,
        ARTISTLIST,
        RECENTLIST,
        PLAYLIST
    }
}
