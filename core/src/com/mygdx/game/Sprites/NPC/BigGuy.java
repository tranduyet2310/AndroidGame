package com.mygdx.game.Sprites.NPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.Utils;

public class BigGuy extends NPC {
    private Animation<TextureRegion> bigGuyAnimation;
    private Array<TextureRegion> frames;
    private float stateTimer;
    private TextureRegion region;

    public BigGuy(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setBounds(getX(), getY(), 56 / Constants.PPM, 48 / Constants.PPM);
        frames = new Array<TextureRegion>();
        for (int i = 1; i <= 38; i++) {
            frames.add(new TextureRegion(Utils.getRegion("npc/Big Guy/" + i + ".png")));
        }
        bigGuyAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTimer = 0;
    }

    @Override
    public void defineNPC() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY() + 34 / Constants.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.NPC_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.GROUND_BIT
                | Constants.SPIKE_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        stateTimer += dt;
        region = bigGuyAnimation.getKeyFrame(stateTimer, true);
        setRegion(region);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    @Override
    public void checkRequest(Player player) {
        if (player.isHasGoldCoin()) {
            Utils.setCompleteRequest(true);
            player.setHasGoldCoin(false);
            destroy();
        }
    }
}
