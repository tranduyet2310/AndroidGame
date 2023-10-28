package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Utils;

public class LevelCompletedScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private MyGdxGame game;
    private Skin skin;
    private Sound sound;
    private AudioManager audioManager;
    private Utils utils;

    public LevelCompletedScreen(MyGdxGame game) {
        this.game = game;
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label titleLabel = new Label("LEVEL COMPLETED!!!", skin);
        Label playAgainLabel = new Label("Click to next level", skin);
        titleLabel.setFontScale(3.0f);
        playAgainLabel.setFontScale(2.0f);

        table.add(titleLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);

        audioManager = AudioManager.getInstance();
        sound = audioManager.getSound(Constants.SOUND_LVLCOMPLETED);
        long idSound = sound.play(MyGdxGame.MUSIC_VOLUME);
        sound.setLooping(idSound, true);

        utils = Utils.getInstance();
        if (utils.getLevel() > 5) {
            titleLabel.setText("CONGRATULATIONS!!!");
            playAgainLabel.setText("You have explored the land successfully!!!\n\n\t   The next land is: ASTARTE");
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            if (utils.getLevel() > 5) {
                utils.setLevel(5);
                game.changeScreen(Constants.MAP);
                dispose();
            } else {
                game.setScreen(new StorylineScreen(game));
                dispose();
            }

        }
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        sound.stop();
        sound.dispose();
    }
}
