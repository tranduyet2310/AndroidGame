package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
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
	private AppPreferences preferences;

	public void changeScreen(int screen){
		switch (screen){
			case MAP:
				if(mapScreen == null)
					mapScreen = new MapScreen(this);
				this.setScreen(mapScreen);
				break;
			case PLAY:
				if(playScreen == null)
					playScreen = new PlayScreen(this);
				this.setScreen(playScreen);
				break;
			case OPTION:
				if(optionScreen == null)
					optionScreen = new OptionScreen(this);
				this.setScreen(optionScreen);
				break;
			case STATISTIC:
				if(statisticScreen == null)
					statisticScreen = new StatisticScreen(this);
				this.setScreen(statisticScreen);
				break;
			case STORY:
				if(storylineScreen == null)
					storylineScreen = new StorylineScreen(this);
				this.setScreen(storylineScreen);
				break;
			case MENU:
				if(menuScreen == null)
					menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
		}
	}

	public AppPreferences getPreferences(){
		return this.preferences;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		preferences = new AppPreferences();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
