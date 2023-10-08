package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Tools.Utils;

public class GreenTotem extends Enemy{
    public BlueTotem.TotemState currentState;
    public BlueTotem.TotemState previousState;
    private float stateTimer;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> totemIdle, totemHit, totemDead, totemAttack;
    private boolean setToDestroy;
    private boolean destroyed;
    public int currentHealth, maxHealth, powerAttack;
    //
    private boolean isDead, isHurting, isAttacking;

    public GreenTotem(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        stateTimer = 0;
        currentHealth = maxHealth = Constants.SHARK_MAXHEALTH;
        powerAttack = Constants.SHARK_ATTACK;
        currentState = previousState = BlueTotem.TotemState.IDLE;

        isAttacking = false;
        isDead = false;
        isHurting = false;
        setToDestroy = false;
        destroyed = false;

        setBounds(getX(), getY(), 32 / Constants.PPM, 32 / Constants.PPM);
        velocity.x = 0.0f;

        frames = new Array<TextureRegion>();
        initAnimation(frames);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.ENEMY_BIT;
        fixtureDef.filter.maskBits = Constants.GROUND_BIT | Constants.WATER_BIT | Constants.SWORD_BIT
                | Constants.SPIKE_BIT | Constants.ENEMY_BIT | Constants.PLAYER_BIT | Constants.SWORD_ATTACK_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void getsHurt() {
        if (currentHealth <= 0) {
            isDead = true;
            currentHealth = 0;
            Gdx.app.log("Totems", "DEAD");
        } else {
            currentHealth -= Constants.PLAYER_ATTACK;
            isHurting = true;
        }
    }

    @Override
    public void getSworkAttack() {
        if (currentHealth <= 0) {
            isDead = true;
            currentHealth = 0;
            Gdx.app.log("Totems", "DEAD");
        } else {
            currentHealth -= Constants.SWORD_ATTACK;
            isHurting = true;
        }
    }

    @Override
    public void update(float dt) {
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            isDead = false;
            stateTimer = 0;
        }

        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public void draw(Batch batch) {
        if (!destroyed || stateTimer < 1)
            super.draw(batch);
    }

    public BlueTotem.TotemState getState() {
        if (isDead) {
            return BlueTotem.TotemState.DEAD;
        } else if (isHurting) {
            return BlueTotem.TotemState.HIT;
        } else if (isAttacking) {
            return BlueTotem.TotemState.ATTACK;
        } else return BlueTotem.TotemState.IDLE;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case DEAD:
                region = totemDead.getKeyFrame(stateTimer);
                if (totemDead.isAnimationFinished(stateTimer)) {
                    setToDestroy = true;
                    Gdx.app.log("Totems", "inTotemsDead");
                }
                break;
            case HIT:
                region = totemHit.getKeyFrame(stateTimer);
                if (totemHit.isAnimationFinished(stateTimer)) {
                    isAttacking = true;
                    isHurting = false;
                    Gdx.app.log("Totems", "inTotemslHit");
                }
                break;
            case ATTACK:
                region = totemAttack.getKeyFrame(stateTimer);
                if (totemAttack.isAnimationFinished(stateTimer)) {
                    isAttacking = false;
                    Gdx.app.log("Totems", "inTotemsAttack");
                }
                break;
            case IDLE:
            default:
                region = totemIdle.getKeyFrame(stateTimer, true);
                break;
        }
        if (velocity.x > 0 && !region.isFlipX()) {
            region.flip(true, false);
        }
        if (velocity.x < 0 && region.isFlipX()) {
            region.flip(true, false);
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public void initAnimation(Array<TextureRegion> frames) {

        frames.add(new TextureRegion(Utils.getRegion("enemy/Totems/Head 3/Idle/1.png")));
        totemIdle = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Totems/Head 3/Attack/" + i + ".png")));
        }
        totemAttack = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Totems/Head 3/Destroyed/" + i + ".png")));
        }
        totemDead = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Totems/Head 3/Hit/" + i + ".png")));
        }
        totemHit = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
