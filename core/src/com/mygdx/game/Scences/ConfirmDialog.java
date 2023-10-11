package com.mygdx.game.Scences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

public class ConfirmDialog implements Disposable {
    public Stage stage;
    public Dialog dialog;
    Label pauseIcon, spaceLabel, messageDialog;
    TextButton btnHome, btnReset, btnContinue;
    Skin skin;
    private static boolean check = false;

    public ConfirmDialog(SpriteBatch sb, final MyGdxGame game) {
        Gdx.app.log("ConfirmDialog", "onConstructor");
        Viewport viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table(skin);
        table.top();
        table.setFillParent(true);

        dialog = new Dialog("Notify", skin);
        messageDialog = new Label("Do you want to ...?", skin);
        pauseIcon = new Label(" || ", skin, "title");
        btnHome = new TextButton("Home", skin);
        btnReset = new TextButton("Reset", skin);
        btnContinue = new TextButton("Continue", skin);
        spaceLabel = new Label("   ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(spaceLabel).expandX().padTop(10);
        table.add(spaceLabel).expandX().padTop(10);
        table.add(spaceLabel).expandX().padTop(10);
        table.add(spaceLabel).expandX().padTop(10);
        table.add(pauseIcon).expandX().padTop(10).right().padRight(30);

        dialog.getContentTable().add(messageDialog);
        dialog.getButtonTable().add(btnHome);
        dialog.getButtonTable().add(btnReset);
        dialog.getButtonTable().add(btnContinue);

        stage.addActor(table);
        pauseIcon.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dialog.show(stage);
                check = true;
                Gdx.app.log("pauseIcon", "clicked");
//                Gdx.input.setInputProcessor(stage);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        btnHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                check = false;
                game.changeScreen(Constants.MENU);
            }
        });
        btnReset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.changeScreen(Constants.PLAY_DISPOSE);
            }
        });
        btnContinue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                check = false;
            }
        });

    }

    public void update(float dt) {
        Gdx.input.setInputProcessor(stage);
        if(!check) dialog.hide();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
