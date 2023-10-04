package com.mygdx.game;

public class Constants {
    //
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 768;
    public static final float PPM = 100;
    // Box2D Collision Bits
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short SILVER_COIN_BIT = 4;
    public static final short GOLD_COIN_BIT = 8;
    public static final short COLLECTED_BIT = 16;
    public static final short SPIKE_BIT = 32;
    public static final short ENEMY_BIT = 64;
    public static final short GOLDEN_SKULL = 128;
    public static final short BLUE_DIAMOND = 256;
    public static final short BIG_MAP = 512;
    public static final short WATER_BIT = 1024;
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
    public static final int PLAYER_MAXHEALTH = 150;
    public static final int CRABBY_MAXHEALTH = 50;
    public static final int SHARK_MAXHEALTH = 75;
    public static final int STAR_MAXHEALTH = 75;
    public static final int CRABBY_ATTACK = 15;
    public static final int SHARK_ATTACK = 25;
    public static final int STAR_ATTACK = 20;
    public static final int PLAYER_ATTACK = 25;
}
