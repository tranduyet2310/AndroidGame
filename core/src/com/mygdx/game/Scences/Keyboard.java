package com.mygdx.game.Scences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants;
import com.mygdx.game.Sprites.Player;

public class Keyboard implements Disposable {
    public Stage stage;
    private Texture back, front, up, attack, skill;
    private SpriteBatch sb;
    private Player player;
    private Image imageBack, imageFront, imageUp, imageAttack, imageSkill;

    public Keyboard(SpriteBatch sb, Player player) {
        this.sb = sb;
        this.player = player;
        Viewport viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Gdx.input.setInputProcessor(stage);

        up = new Texture("keyboard/up.png");
        back = new Texture("keyboard/back.png");
        attack = new Texture("keyboard/attack.png");
        front = new Texture("keyboard/front.png");
        skill = new Texture("keyboard/skill.png");

        imageBack = new Image(back);
        imageFront = new Image(front);
        imageAttack = new Image(attack);
        imageUp = new Image(up);
        imageSkill = new Image(skill);

        imageBack.setScale(2.5f, 2.5f);
        imageUp.setScale(2.5f, 2.5f);
        imageFront.setScale(2.5f, 2.5f);
        imageAttack.setScale(2.5f, 2.5f);
        imageSkill.setScale(2.5f, 2.5f);

        imageBack.setPosition(50, 25);
        imageFront.setPosition(200, 25);
        imageUp.setPosition(125, 100);
        imageAttack.setPosition(1000, 25);
        imageSkill.setPosition(1150, 25);

        stage.addActor(imageBack);
        stage.addActor(imageFront);
        stage.addActor(imageUp);
        stage.addActor(imageAttack);
        stage.addActor(imageSkill);

    }

    private void handleInput(float dt) {
        imageBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("keyboard", "back");
            }
        });
        imageFront.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("keyboard", "front");
            }
        });
        imageUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("keyboard", "up");
            }
        });
        imageAttack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("keyboard", "attack");
            }
        });
        imageSkill.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("keyboard", "skill");
            }
        });
    }

    public void update(float dt) {
//        Gdx.input.setInputProcessor(stage);
//        handleInput(dt);
    }

    @Override
    public void dispose() {
        back.dispose();
        up.dispose();
        front.dispose();
        attack.dispose();
        skill.dispose();
    }
}
