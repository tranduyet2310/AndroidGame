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
import com.mygdx.game.Tools.Utils;

public class MyGdxGame extends Game {
    private MapScreen mapScreen;
    private PlayScreen playScreen;
    private OptionScreen optionScreen;
    private StatisticScreen statisticScreen;
    private StorylineScreen storylineScreen;
    private MenuScreen menuScreen;
    // batch to render sprite for Screens
    public SpriteBatch batch;
    // Music variables
    private AudioManager audioManager;
    public static Float MUSIC_VOLUME;
    public static boolean IS_MUSIC_ENABLED;
    public static boolean IS_SFX_ENABLED;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // inital Value
        IS_MUSIC_ENABLED = true;
        IS_SFX_ENABLED = true;
        MUSIC_VOLUME = 0.5f;
        // create Audio Manager
        audioManager = AudioManager.getInstance();
        audioManager.loadAssets();
        audioManager.finishLoading();
        // inital global value
        Utils utils = Utils.getInstance();
        utils.setPlayerOnWater(false);
        utils.setLevel(1);

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

    public void changeScreen(int screen) {
        switch (screen) {
            case Constants.MAP:
                if (mapScreen == null)
                    mapScreen = new MapScreen(this);
                this.setScreen(mapScreen);
                break;
            case Constants.PLAY:
                if (playScreen == null)
                    playScreen = new PlayScreen(this);
                this.setScreen(playScreen);
                break;
            case Constants.OPTION:
                if (optionScreen == null)
                    optionScreen = new OptionScreen(this);
                this.setScreen(optionScreen);
                break;
            case Constants.STATISTIC:
                if (statisticScreen == null)
                    statisticScreen = new StatisticScreen(this);
                this.setScreen(statisticScreen);
                break;
            case Constants.STORY:
                if (storylineScreen == null)
                    storylineScreen = new StorylineScreen(this);
                this.setScreen(storylineScreen);
                break;
            case Constants.MENU:
                if (menuScreen == null)
                    menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
//            case Constants.MENU_DISPOSE:
//                playScreen.pause();
//                if (menuScreen == null)
//                    menuScreen = new MenuScreen(this);
//                this.setScreen(menuScreen);
//                break;
            case Constants.PLAY_DISPOSE:
                playScreen = new PlayScreen(this);
                this.setScreen(playScreen);
                break;
        }
    }

}
