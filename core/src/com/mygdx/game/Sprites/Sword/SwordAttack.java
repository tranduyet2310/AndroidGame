package com.mygdx.game.Sprites.Sword;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Crabby;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.Shark;
import com.mygdx.game.Tools.Utils;

public class SwordAttack extends Sprite {
    private PlayScreen screen;
    private World world;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> swordSpinning;
    private float stateTime;
    private boolean destroyed;
    private boolean setToDestroy;
    private boolean attackRight;
    private Body b2body;

    public SwordAttack(PlayScreen screen, float x, float y, boolean attackRight) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.attackRight = attackRight;
        stateTime = 0;

        frames = new Array<TextureRegion>();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Sword Attack/Sword Spinning 0" + i + ".png")));
        }
        swordSpinning = new Animation<TextureRegion>(0.4f, frames);

        setRegion(swordSpinning.getKeyFrame(stateTime));
        setBounds(x, y, 20 / Constants.PPM, 20 / Constants.PPM);

        defineSwordAttack();
    }

    private void defineSwordAttack() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(attackRight ? getX() + 12 / Constants.PPM : getX() - 12 / Constants.PPM, getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if (!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(4 / Constants.PPM);
        fdef.filter.categoryBits = Constants.SWORD_ATTACK_BIT;
        fdef.filter.maskBits = Constants.GROUND_BIT | Constants.SPIKE_BIT |
                Constants.ENEMY_BIT | Constants.SPECIAL_ITEM_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.setLinearVelocity(new Vector2(attackRight ? 2 : -2, 2.5f));
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt) {
        stateTime += dt;
        setRegion(swordSpinning.getKeyFrame(stateTime, true));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if ((stateTime > 2 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if (b2body.getLinearVelocity().y > 2f) {
            b2body.setLinearVelocity(b2body.getLinearVelocity().x, 2f);
        }
        if ((attackRight && b2body.getLinearVelocity().x < 0) || (!attackRight && b2body.getLinearVelocity().x > 0)) {
            setToDestroy();
        }
    }

    public void setToDestroy() {
        setToDestroy = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
