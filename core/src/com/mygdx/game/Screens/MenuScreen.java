package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

public class MenuScreen implements Screen {
    private MyGdxGame game;
    private Viewport viewport;
    public Stage stage;
    private Texture bg_menu;
    private Music music;
    private AudioManager audioManager;

    public MenuScreen(final MyGdxGame game) {
        this.game = game;
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        bg_menu = new Texture(Gdx.files.internal("resource/bg_menu.png"));

        Table table = new Table();
        table.setFillParent(true);
//        table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        TextButton play = new TextButton("Play", skin, "round");
        TextButton option = new TextButton("Options", skin, "round");
        TextButton statistic = new TextButton("Statistic", skin, "round");
        TextButton quit = new TextButton("Quit", skin, "round");

        table.add(play).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(option).fillX().uniformX();
        table.row();
        table.add(statistic).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(quit).fillX().uniformX();

        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Constants.MAP);
            }
        });

        option.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Constants.OPTION);
            }
        });

        statistic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.changeScreen(Constants.STATISTIC);
            }
        });

        audioManager = AudioManager.getInstance();
        music = audioManager.getMusic(Constants.MUSIC_MENU);
        music.setLooping(true);
        music.setVolume(MyGdxGame.MUSIC_VOLUME);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        if (MyGdxGame.IS_MUSIC_ENABLED)
            music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(bg_menu, Constants.V_WIDTH / 2.0f - bg_menu.getWidth() / 2.0f, Constants.V_HEIGHT / 2.0f - bg_menu.getHeight() / 2.0f, bg_menu.getWidth(), bg_menu.getHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / (30 * 1.0f)));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        music.stop();
    }

    @Override
    public void dispose() {
        System.out.println("MenuScreen dispose()");
        stage.dispose();
        bg_menu.dispose();
        music.dispose();
    }
}
