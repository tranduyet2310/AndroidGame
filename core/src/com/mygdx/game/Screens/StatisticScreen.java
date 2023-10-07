package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.AudioManager;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

import java.util.HashMap;

public class StatisticScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private MyGdxGame game;
    private Skin skin;
    private Music music;
    private AudioManager audioManager;
    private Texture bg_statistic;
    private TextButton backButton;
    private Label landLabel, mapLabel, map1Label, map2Label, map3Label, map4Label, map5Label;
    private Label highestScoreLabel, score1, score2, score3, score4, score5;
    private int time1, time2, time3, time4, time5;
    private Preferences prefs;

    public StatisticScreen(final MyGdxGame game) {
        this.game = game;
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        bg_statistic = new Texture(Gdx.files.internal("resource/bg_statistic.png"));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Table table = new Table();
//        table.setDebug(true);
        table.setFillParent(true);

        landLabel = new Label("LUSTEL", skin, "title");
        mapLabel = new Label("MAP", skin, "title");
        map1Label = new Label("1-1", skin, "title-plain");
        map2Label = new Label("1-2", skin, "title-plain");
        map3Label = new Label("1-3", skin, "title-plain");
        map4Label = new Label("1-4", skin, "title-plain");
        map5Label = new Label("1-5", skin, "title-plain");

        highestScoreLabel = new Label("HIGHEST SCORE", skin, "title");
        score1 = new Label("00:00", skin, "title-plain");
        score2 = new Label("00:00", skin, "title-plain");
        score3 = new Label("00:00", skin, "title-plain");
        score4 = new Label("00:00", skin, "title-plain");
        score5 = new Label("00:00", skin, "title-plain");

        backButton = new TextButton("Back", skin, "round");

        table.add(landLabel).colspan(2);
        table.row();
        table.add(mapLabel).padTop(15f);
        table.add(highestScoreLabel).padTop(15f);
        table.row();
        table.add(map1Label).padTop(5f);
        table.add(score1).padTop(5f);
        table.row();
        table.add(map2Label).padTop(5f);
        table.add(score2).padTop(5f);
        table.row();
        table.add(map3Label).padTop(5f);
        table.add(score3).padTop(5f);
        table.row();
        table.add(map4Label).padTop(5f);
        table.add(score4).padTop(5f);
        table.row();
        table.add(map5Label).padTop(5f);
        table.add(score5).padTop(5f);
        table.row();
        table.add(backButton).padTop(15f).colspan(2);

        stage.addActor(table);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Constants.MENU);
            }
        });

        audioManager = AudioManager.getInstance();
        music = audioManager.getMusic(Constants.MUSIC_MENU);
        music.setLooping(true);
        music.setVolume(MyGdxGame.MUSIC_VOLUME);

        prefs = Gdx.app.getPreferences("mygdxgame");

    }

    public void convertToTime(int value, Label label) {
        int minutes = value / 60;
        int seconds = value % 60;
        label.setText(String.format("%02d:%02d", minutes, seconds));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        if (MyGdxGame.IS_MUSIC_ENABLED)
            music.play();
        Array<Integer> scores = new Array<>();
        for (int i = 1; i <= 5; i++) {
            String label = "score" + i;
            scores.add(prefs.getInteger(label), 0);
            Gdx.app.log("Hud", "in Preferences -label:" + label + "-value:" + scores.get(i - 1));
        }
        convertToTime(scores.get(0), score1);
        convertToTime(scores.get(1), score2);
        convertToTime(scores.get(2), score3);
        convertToTime(scores.get(3), score4);
        convertToTime(scores.get(4), score5);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bg_statistic, Constants.V_WIDTH / 2.0f - bg_statistic.getWidth() / 2.0f, Constants.V_HEIGHT / 2.0f - bg_statistic.getHeight() / 2.0f, bg_statistic.getWidth(), bg_statistic.getHeight());
        game.batch.end();

        stage.act();
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
    }
}
