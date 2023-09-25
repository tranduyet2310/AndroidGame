package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Tools.Utils;

public class AudioManager {
    private AssetManager assetManager;
    private static AudioManager instance;

    private AudioManager() {
        assetManager = new AssetManager();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void loadAssets() {
        assetManager.load(Utils.MUSIC_LEVEL1, Music.class);
        assetManager.load(Utils.MUSIC_LEVEL2, Music.class);
        assetManager.load(Utils.MUSIC_MENU, Music.class);
        assetManager.load(Utils.SOUND_ATTACK1, Sound.class);
        assetManager.load(Utils.SOUND_ATTACK2, Sound.class);
        assetManager.load(Utils.SOUND_ATTACK3, Sound.class);
        assetManager.load(Utils.SOUND_DIE, Sound.class);
        assetManager.load(Utils.SOUND_JUMP, Sound.class);
        assetManager.load(Utils.SOUND_GAMEOVER, Sound.class);
        assetManager.load(Utils.SOUND_LVLCOMPLETED, Sound.class);
    }

    public void finishLoading() {
        assetManager.finishLoading();
    }

    public Music getMusic(String path) {
        return assetManager.get(path, Music.class);
    }

    public Sound getSound(String path) {
        return assetManager.get(path, Sound.class);
    }

    public void dispose() {
        assetManager.dispose();
    }
}
