package com.mygdx.game.Sprites.Items.RequestedItems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Items.Item;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.Utils;

public class SilverCoin extends Item {
    private Animation<TextureRegion> silverCoinAnimation;
    private Array<TextureRegion> frames;
    private float stateTimer;
    private TextureRegion region;

    public SilverCoin(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("item/Requested Items/Silver Coin/0" + i + ".png")));
        }
        silverCoinAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTimer = 0;
        velocity = new Vector2(0, 0);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        stateTimer += dt;
        region = silverCoinAnimation.getKeyFrame(stateTimer, true);
        setRegion(region);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        body.setLinearVelocity(velocity);
    }


    @Override
    public void defineItem() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.SILVER_COIN_BIT;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.GROUND_BIT
                | Constants.SPIKE_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void use(Player player) {
        player.setHasSilverCoin(true);
        destroy();
    }
}
