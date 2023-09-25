package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Screens.MapScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.OptionScreen;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Screens.StatisticScreen;
import com.mygdx.game.Screens.StorylineScreen;

public class MyGdxGame extends Game {
    private MapScreen mapScreen;
    private PlayScreen playScreen;
    private OptionScreen optionScreen;
    private StatisticScreen statisticScreen;
    private StorylineScreen storylineScreen;
    private MenuScreen menuScreen;
    public static final int MAP = 0;
    public static final int PLAY = 1;
    public static final int OPTION = 2;
    public static final int STATISTIC = 3;
    public static final int STORY = 4;
    public static final int MENU = 5;

    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 768;
    public static final float PPM = 100;
    public SpriteBatch batch;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short SILVER_COIN_BIT = 4;
    public static final short GOLD_COIN_BIT = 8;
    public static final short COLLECTED_BIT = 16;

    private Screen currentScreen;
    private AudioManager audioManager;

    public static Float MUSIC_VOLUME = 0.5f;
    public static boolean IS_MUSIC_ENABLED = true;
    public static boolean IS_SFX_ENABLED = true;

    public void changeScreen(int screen) {
//		if (currentScreen != null){
//			currentScreen.dispose();
//		}

        switch (screen) {
            case MAP:
                if (mapScreen == null)
                    mapScreen = new MapScreen(this);
//				currentScreen = (Screen) mapScreen;
                this.setScreen(mapScreen);
                break;
            case PLAY:
                if (playScreen == null)
                    playScreen = new PlayScreen(this);
//				currentScreen = (Screen) playScreen;
                this.setScreen(playScreen);
                break;
            case OPTION:
                if (optionScreen == null)
                    optionScreen = new OptionScreen(this);
//				currentScreen = (Screen) optionScreen;
                this.setScreen(optionScreen);
                break;
            case STATISTIC:
                if (statisticScreen == null)
                    statisticScreen = new StatisticScreen(this);
//				currentScreen = (Screen) statisticScreen;
                this.setScreen(statisticScreen);
                break;
            case STORY:
                if (storylineScreen == null)
                    storylineScreen = new StorylineScreen(this);
//				currentScreen = (Screen) storylineScreen;
                this.setScreen(storylineScreen);
                break;
            case MENU:
                if (menuScreen == null)
                    menuScreen = new MenuScreen(this);
//				currentScreen = (Screen) menuScreen;
                this.setScreen(menuScreen);
                break;
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        audioManager = AudioManager.getInstance();
        audioManager.loadAssets();
        audioManager.finishLoading();

//		currentScreen = (Screen) menuScreen;
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        audioManager.dispose();
    }
}
