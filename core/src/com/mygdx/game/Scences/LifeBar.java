package com.mygdx.game.Scences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import com.mygdx.game.Sprites.Player;

public class LifeBar implements Disposable {
    public Stage stage;
    private Texture lifeBar;
    private Texture blood;
    private float lifeBarWidth;
    private SpriteBatch sb;
    private Player player;
    private Image imageLifeBar, imageBlood;

    public LifeBar(SpriteBatch sb, Player player) {
        this.sb = sb;
        this.player = player;
        Viewport viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        blood = new Texture("player/red.png");
        lifeBar = new Texture("player/lifeBar.png");

        lifeBarWidth = player.getCurrentHealth() / (Constants.PLAYER_MAXHEALTH * 1.0f);

        imageLifeBar = new Image(lifeBar);
        imageBlood = new Image(blood);
        imageLifeBar.setScale(2.5f, 2.5f);
        imageBlood.setScale(6.0f * lifeBarWidth, 1.5f);
        imageLifeBar.setPosition(21, 610);
        imageBlood.setPosition(65, 650);

        stage.addActor(imageLifeBar);
        stage.addActor(imageBlood);

    }

    public void update(float dt) {
        lifeBarWidth = player.getCurrentHealth() / (Constants.PLAYER_MAXHEALTH * 1.0f);
        if (lifeBarWidth <= 0) lifeBarWidth = 0;
        imageBlood.setScale(6.0f * lifeBarWidth, 1.5f);
    }

    @Override
    public void dispose() {
        lifeBar.dispose();
        blood.dispose();
    }
}
