package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {
    public static final String PREF_MUSIC_VOLUME = "volume";
    public static final String PREF_MUSIC_ENABLED = "music.enabled";
    public static final String PREF_EFFECT_ENABLED = "effect.enabled";
    public static final String PREFS_NAME = "mygdxgame";

    protected Preferences getPrefs(){
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public float getMusicVolume(){
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume){
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public boolean isMusicEnabled(){
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled){
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public boolean isEffectEnabled(){
        return getPrefs().getBoolean(PREF_EFFECT_ENABLED, true);
    }

    public void setEffectEnabled(boolean effectEnabled){
        getPrefs().putBoolean(PREF_EFFECT_ENABLED, true);
        getPrefs().flush();
    }
}
