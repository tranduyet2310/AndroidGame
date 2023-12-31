package com.mygdx.game.Sprites.Items.Maps;

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
import com.mygdx.game.Sprites.Items.ItemDef;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.Utils;

public class SmallMap4 extends Item {
    private Animation<TextureRegion> smallMap4Animation;
    private Array<TextureRegion> frames;
    private float stateTimer;
    private TextureRegion region;

    public SmallMap4(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 1; i <= 8; i++) {
            frames.add(new TextureRegion(Utils.getRegion("item/Maps/Small Map 4/0" + i + ".png")));
        }
        smallMap4Animation = new Animation<TextureRegion>(0.4f, frames);
        stateTimer = 0;
        velocity = new Vector2(0, 0);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        stateTimer += dt;
        region = smallMap4Animation.getKeyFrame(stateTimer, true);
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
        fixtureDef.filter.categoryBits = Constants.SMALL_MAP_4;
        fixtureDef.filter.maskBits = Constants.PLAYER_BIT | Constants.GROUND_BIT
                | Constants.SPIKE_BIT;

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void use(Player player) {
        screen.spwanItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / Constants.PPM), BigMap.class));
        destroy();
    }
}
