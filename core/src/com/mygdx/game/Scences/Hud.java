package com.mygdx.game.Scences;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    Label countdownLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel, landLabel, processLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        timeCount = 0;

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("MAP", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        landLabel = new Label("LUSTEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        processLabel = new Label("PROCESS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        countdownLabel.setFontScale(2.0f);
        timeLabel.setFontScale(2.0f);
        levelLabel.setFontScale(2.0f);
        worldLabel.setFontScale(2.0f);
        landLabel.setFontScale(2.0f);
        processLabel.setFontScale(2.0f);

        table.add(worldLabel).expandX().padTop(10);
        table.add(processLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(landLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
