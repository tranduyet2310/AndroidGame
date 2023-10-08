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

public class Seashell extends Enemy {
    public enum SeaShellState {IDLE, HIT, DEAD, ATTACK}

    public SeaShellState currentState;
    public SeaShellState previousState;
    private float stateTimer;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> seaShellIdle, seaShellHit, seaShellDead, seaShellAttack;
    private boolean setToDestroy;
    private boolean destroyed;
    public int currentHealth, maxHealth, powerAttack;
    //
    private boolean isDead, isHurting, isAttacking;

    public Seashell(PlayScreen screen, float x, float y) {
        super(screen, x, y);

        stateTimer = 0;
        currentHealth = maxHealth = Constants.SEASHELL_MAXHEALTH;
        powerAttack = Constants.SEASHELL_ATTACK;
        currentState = previousState = SeaShellState.IDLE;

        isAttacking = false;
        isDead = false;
        isHurting = false;
        setToDestroy = false;
        destroyed = false;

        setBounds(getX(), getY(), 48 / Constants.PPM, 38 / Constants.PPM);
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
            Gdx.app.log("Seashell", "DEAD");
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
            Gdx.app.log("Seashell", "DEAD");
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

    public SeaShellState getState() {
        if (isDead) {
            return SeaShellState.DEAD;
        } else if (isHurting) {
            return SeaShellState.HIT;
        } else if (isAttacking) {
            return SeaShellState.ATTACK;
        } else return SeaShellState.IDLE;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case DEAD:
                region = seaShellDead.getKeyFrame(stateTimer);
                if (seaShellDead.isAnimationFinished(stateTimer)) {
                    setToDestroy = true;
                    Gdx.app.log("Seashell", "inSeashellDead");
                }
                break;
            case HIT:
                region = seaShellHit.getKeyFrame(stateTimer);
                if (seaShellHit.isAnimationFinished(stateTimer)) {
                    isAttacking = true;
                    isHurting = false;
                    Gdx.app.log("Seashell", "inSeashellHit");
                }
                break;
            case ATTACK:
                region = seaShellAttack.getKeyFrame(stateTimer);
                if (seaShellAttack.isAnimationFinished(stateTimer)) {
                    isAttacking = false;
                    Gdx.app.log("Seashell", "inSeashellAttack");
                }
                break;
            case IDLE:
            default:
                region = seaShellIdle.getKeyFrame(stateTimer, true);
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
        for (int i = 1; i <= 5; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Seashell/Idle/" + i + ".png")));
        }
        seaShellIdle = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Seashell/Attack/" + i + ".png")));
        }
        seaShellAttack = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 5; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Seashell/Dead Hit/" + i + ".png")));
        }
        seaShellDead = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Seashell/Hit/" + i + ".png")));
        }
        seaShellHit = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
