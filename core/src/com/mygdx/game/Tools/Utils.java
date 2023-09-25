package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Utils {
    public static final String MUSIC_LEVEL1 = "audio/level1.wav";
    public static final String MUSIC_LEVEL2 = "audio/level2.wav";
    public static final String MUSIC_MENU = "audio/menu.wav";
    public static final String SOUND_ATTACK1 = "audio/attack1.wav";
    public static final String SOUND_ATTACK2 = "audio/attack2.wav";
    public static final String SOUND_ATTACK3 = "audio/attack3.wav";
    public static final String SOUND_DIE = "audio/die.wav";
    public static final String SOUND_GAMEOVER = "audio/gameover.wav";
    public static final String SOUND_JUMP = "audio/jump.wav";
    public static final String SOUND_LVLCOMPLETED = "audio/lvlcompleted.wav";

    static public TextureRegion getRegion(String path){
        return new TextureRegion(new Texture(Gdx.files.internal(path)));
    }
}
