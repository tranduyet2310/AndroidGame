package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public SpriteBatch batch;
    private Screen currentScreen;
    // Music variables
    private AudioManager audioManager;
    public static Float MUSIC_VOLUME = 0.5f;
    public static boolean IS_MUSIC_ENABLED = true;
    public static boolean IS_SFX_ENABLED = true;

    public void changeScreen(int screen) {
//		if (currentScreen != null){
//			currentScreen.dispose();
//		}

        switch (screen) {
            case Constants.MAP:
                if (mapScreen == null)
                    mapScreen = new MapScreen(this);
//				currentScreen = (Screen) mapScreen;
                this.setScreen(mapScreen);
                break;
            case Constants.PLAY:
                if (playScreen == null)
                    playScreen = new PlayScreen(this);
//				currentScreen = (Screen) playScreen;
                this.setScreen(playScreen);
                break;
            case Constants.OPTION:
                if (optionScreen == null)
                    optionScreen = new OptionScreen(this);
//				currentScreen = (Screen) optionScreen;
                this.setScreen(optionScreen);
                break;
            case Constants.STATISTIC:
                if (statisticScreen == null)
                    statisticScreen = new StatisticScreen(this);
//				currentScreen = (Screen) statisticScreen;
                this.setScreen(statisticScreen);
                break;
            case Constants.STORY:
                if (storylineScreen == null)
                    storylineScreen = new StorylineScreen(this);
//				currentScreen = (Screen) storylineScreen;
                this.setScreen(storylineScreen);
                break;
            case Constants.MENU:
                if (menuScreen == null)
                    menuScreen = new MenuScreen(this);
//				currentScreen = (Screen) menuScreen;
                this.setScreen(menuScreen);
                break;
            case Constants.MENU_DISPOSE:
                playScreen.pause();
                if (menuScreen == null)
                    menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case Constants.PLAY_DISPOSE:
                playScreen = new PlayScreen(this);
                this.setScreen(playScreen);
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
