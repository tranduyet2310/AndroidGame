package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.AppPreferences;
import com.mygdx.game.MyGdxGame;

public class OptionScreen implements Screen {
    private MyGdxGame game;
    public Stage stage;
    private Texture bg_option;
    private Sprite sprite;
    private SpriteBatch batch;
    private Label titleLabel, volumeMusicLabel, effectLabel, musicLabel;
    private Slider volumeMusicSlider;
    private CheckBox effectCheckBox, musicCheckBox;
    private TextButton backButton;
    private Skin skin;

    public OptionScreen(final MyGdxGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        bg_option = new Texture(Gdx.files.internal("resource/bg_option.png"));
        sprite = new Sprite(bg_option);
        sprite.setPosition(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM);
        batch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        titleLabel = new Label("Preferences", skin, "title");
        musicLabel = new Label("Music", skin, "title-plain");
        effectLabel = new Label("SFX", skin, "title-plain");
        volumeMusicLabel = new Label("Music Volume", skin, "title-plain");

        musicCheckBox = new CheckBox(null, skin);
        effectCheckBox = new CheckBox(null, skin);
        volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        backButton = new TextButton("Back", skin, "round");

        updateValue();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        musicCheckBox.setChecked(game.getPreferences().isMusicEnabled());
        effectCheckBox.setChecked(game.getPreferences().isEffectEnabled());
        volumeMusicSlider.setValue(game.getPreferences().getMusicVolume());

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
    }

    public void updateValue(){
        musicCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckBox.isChecked();
                game.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        musicCheckBox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = effectCheckBox.isChecked();
                game.getPreferences().setEffectEnabled(enabled);
                return false;
            }
        });

        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(MyGdxGame.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30));
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

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        bg_option.dispose();
    }
}
