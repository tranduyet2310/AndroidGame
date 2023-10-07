package com.mygdx.game;

public class Constants {
    //
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 768;
    public static final float PPM = 100;
    // Box2D Collision Bits
    public static final short COLLECTED_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short SILVER_COIN_BIT = 4;
    public static final short GOLD_COIN_BIT = 8;
    public static final short NPC_BIT = 16;
    public static final short SPIKE_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short GOLDEN_SKULL = 128;
    public static final short BLUE_DIAMOND = 256;
    public static final short BIG_MAP = 512;
    public static final short WATER_BIT = 1024;
    public static final short SPECIAL_ITEM_BIT = 2048;
    public static final short SWORD_ATTACK_BIT = 4096;
    public static final short SWORD_BIT = 500;
    public static final short SMALL_MAP_1 = 17;
    public static final short SMALL_MAP_2 = 37;
    public static final short SMALL_MAP_3 = 9;
    public static final short SMALL_MAP_4 = 12;
    public static final short BLUE_POITION_BIT = 13;
    public static final short RED_POITION_BIT = 20;
    public static final short GREEN_POITION_BIT = 21;
    public static final short RED_DIAMOND = 24;
    public static final short CHEST_KEY = 28;

    // flag to change screen
    public static final int MAP = 0;
    public static final int PLAY = 1;
    public static final int OPTION = 2;
    public static final int STATISTIC = 3;
    public static final int STORY = 4;
    public static final int MENU = 5;
    public static final int MENU_DISPOSE = 6;
    public static final int PLAY_DISPOSE = 7;
    // Enemy + Player
    public static final int PLAYER_MAXMANA = 100;
    public static final int PLAYER_MAXHEALTH = 200;
    public static final int CRABBY_MAXHEALTH = 50;
    public static final int SHARK_MAXHEALTH = 75;
    public static final int STAR_MAXHEALTH = 75;
    public static final int CRABBY_ATTACK = 15;
    public static final int SHARK_ATTACK = 25;
    public static final int STAR_ATTACK = 20;
    public static final int PLAYER_ATTACK = 25;
    // Music + Sounds
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
}
