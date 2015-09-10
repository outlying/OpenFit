package com.solderbyte.openfit;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.view.KeyEvent;

public class MediaController {

    public static String CURRENT_TRACK = "Open Fit Track";
    public static byte CURRENT_VOLUME = 15;
    public static int MAX_VOLUME = 0;
    public static int ACT_VOLUME = 0;
    public static AudioManager audioManager = null;

    public static void init( Context context ) {
        audioManager = (AudioManager) context.getSystemService( Context.AUDIO_SERVICE );
        MAX_VOLUME = audioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
        ACT_VOLUME = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        CURRENT_VOLUME = (byte) ACT_VOLUME;
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction( "com.android.music.metachanged" );
        intentFilter.addAction( "com.android.music.playstatechanged" );
        intentFilter.addAction( "com.android.music.playbackcomplete" );
        intentFilter.addAction( "com.android.music.queuechanged" );
        intentFilter.addAction( "com.htc.music.metachanged" );
        intentFilter.addAction( "fm.last.android.metachanged" );
        intentFilter.addAction( "com.sec.android.app.music.metachanged" );
        intentFilter.addAction( "com.nullsoft.winamp.metachanged" );
        intentFilter.addAction( "com.amazon.mp3.metachanged" );
        intentFilter.addAction( "com.miui.player.metachanged" );
        intentFilter.addAction( "com.real.IMP.metachanged" );
        intentFilter.addAction( "com.sonyericsson.music.metachanged" );
        intentFilter.addAction( "com.rdio.android.metachanged" );
        intentFilter.addAction( "com.samsung.sec.android.MusicPlayer.metachanged" );
        intentFilter.addAction( "com.andrew.apollo.metachanged" );
        return intentFilter;
    }

    public static void setTrack( String track ) {
        CURRENT_TRACK = track;
    }

    public static String getTrack() {
        return CURRENT_TRACK;
    }

    public static void setVolume( byte vol ) {
        if ( audioManager != null ) {
            if ( CURRENT_VOLUME != vol ) {
                audioManager.setStreamVolume( AudioManager.STREAM_MUSIC, vol, AudioManager.FLAG_PLAY_SOUND );
            }
            /*if(CURRENT_VOLUME < vol) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            }
            else if(CURRENT_VOLUME > vol){
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            }*/
        }
        CURRENT_VOLUME = vol;
    }

    public static byte getVolume() {
        return CURRENT_VOLUME;
    }

    public static byte getActualVolume() {
        ACT_VOLUME = audioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        return (byte) ACT_VOLUME;
    }

    public static String getArtist( Intent intent ) {
        String artist = intent.getStringExtra( "artist" );
        if ( artist == null ) {
            artist = "OpenFit Artist";
        }
        return artist;
    }

    public static String getAlbum( Intent intent ) {
        String album = intent.getStringExtra( "album" );
        if ( album == null ) {
            album = "OpenFit Album";
        }
        return album;
    }

    public static String getTrack( Intent intent ) {
        String track = intent.getStringExtra( "track" );
        if ( track == null ) {
            track = "OpenFit Track";
        }
        return track;
    }

    public static Intent prevTrackDown() {
        Intent intent = new Intent( Intent.ACTION_MEDIA_BUTTON );
        intent.putExtra( Intent.EXTRA_KEY_EVENT, new KeyEvent( KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS ) );
        return intent;
    }

    public static Intent prevTrackUp() {
        Intent intent = new Intent( Intent.ACTION_MEDIA_BUTTON );
        intent.putExtra( Intent.EXTRA_KEY_EVENT, new KeyEvent( KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS ) );
        return intent;
    }

    public static Intent nextTrackDown() {
        Intent intent = new Intent( Intent.ACTION_MEDIA_BUTTON );
        intent.putExtra( Intent.EXTRA_KEY_EVENT, new KeyEvent( KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT ) );
        return intent;
    }

    public static Intent nextTrackUp() {
        Intent intent = new Intent( Intent.ACTION_MEDIA_BUTTON );
        intent.putExtra( Intent.EXTRA_KEY_EVENT, new KeyEvent( KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT ) );
        return intent;
    }

    public static Intent playTrackDown() {
        Intent intent = new Intent( Intent.ACTION_MEDIA_BUTTON );
        intent.putExtra( Intent.EXTRA_KEY_EVENT, new KeyEvent( KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE ) );
        return intent;
    }

    public static Intent playTrackUp() {
        Intent intent = new Intent( Intent.ACTION_MEDIA_BUTTON );
        intent.putExtra( Intent.EXTRA_KEY_EVENT, new KeyEvent( KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE ) );
        return intent;
    }
}
