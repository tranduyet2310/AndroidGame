package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

public class OptionScreen implements Screen {
    private MyGdxGame game;
    public Stage stage;
    private Texture bg_option;
    private Label titleLabel, volumeMusicLabel, effectLabel, musicLabel;
    private Slider volumeMusicSlider;
    private CheckBox effectCheckBox, musicCheckBox;
    private TextButton backButton;
    private Skin skin;
    private Music music;
    private AudioManager audioManager;
    private Viewport viewport;

    public OptionScreen(final MyGdxGame game) {
        this.game = game;
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);
        bg_option = new Texture(Gdx.files.internal("resource/bg_option.png"));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        titleLabel = new Label("Preferences", skin, "title");
        musicLabel = new Label("Music", skin, "title-plain");
        effectLabel = new Label("SFX", skin, "title-plain");
        volumeMusicLabel = new Label("Music Volume", skin, "title-plain");

        musicCheckBox = new CheckBox(null, skin);
        effectCheckBox = new CheckBox(null, skin);
        volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        backButton = new TextButton("Back", skin, "round");

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
        stage.addActor(table);

        musicCheckBox.setChecked(MyGdxGame.IS_MUSIC_ENABLED);
        effectCheckBox.setChecked(MyGdxGame.IS_SFX_ENABLED);
        volumeMusicSlider.setValue(MyGdxGame.MUSIC_VOLUME);

        table.add(titleLabel).colspan(2);
        table.row();
        table.row().pad(10, 10, 10, 10);
        table.add(effectLabel);
        table.padLeft(50);
        table.add(effectCheckBox);
        table.row().pad(10, 10, 10, 10);
        table.add(musicLabel);
        table.padLeft(50);
        table.add(musicCheckBox);
        table.row().pad(10, 10, 10, 10);
        table.add(volumeMusicLabel);
        table.add(volumeMusicSlider);
        table.row();
        table.row().pad(10, 10, 10, 10);
        table.add(backButton).colspan(2);

        updateValue();

        audioManager = AudioManager.getInstance();
        music = audioManager.getMusic(Constants.MUSIC_MENU);
        music.setLooping(true);
        music.setVolume(MyGdxGame.MUSIC_VOLUME);

        System.out.println("OptionScreen on constructor");
    }

    @Override
    public void show() {
        System.out.println("OptionScreen on show()");
        if (MyGdxGame.IS_MUSIC_ENABLED)
            music.play();
        Gdx.input.setInputProcessor(stage);
    }

    public void updateValue() {
        musicCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                MyGdxGame.IS_MUSIC_ENABLED = musicCheckBox.isChecked();
                return false;
            }
        });

        effectCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                MyGdxGame.IS_SFX_ENABLED = effectCheckBox.isChecked();
                return false;
            }
        });

        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                MyGdxGame.MUSIC_VOLUME = volumeMusicSlider.getValue();
                return false;
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Constants.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bg_option, Constants.V_WIDTH / 2.0f - bg_option.getWidth() / 2.0f, Constants.V_HEIGHT / 2.0f - bg_option.getHeight() / 2.0f, bg_option.getWidth(), bg_option.getHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / (30 * 1.0f)));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        System.out.println("OptionScreen on hide()");
        music.stop();
    }

    @Override
    public void dispose() {
        System.out.println("OptionScreen dispose()");
        stage.dispose();
        bg_option.dispose();
        music.dispose();
    }
}
