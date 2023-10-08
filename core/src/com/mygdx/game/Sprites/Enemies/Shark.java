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
import com.mygdx.game.Sprites.Sword.SwordAttack;
import com.mygdx.game.Tools.Utils;

public class Shark extends Enemy {
    public enum SharkState {RUNNING, IDLE, HIT, DEAD, ATTACK, JUMP}

    public SharkState currentState;
    public SharkState previousState;
    private float stateTimer;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> sharkIdle, sharkHit, sharkDead, sharkAttack, sharkRunning, sharkJump;
    private boolean setToDestroy;
    private boolean destroyed;
    public int currentHealth, maxHealth, powerAttack;
    //
    private boolean isDead, isHurting, isAttacking, isJumping;

    public Shark(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTimer = 0;
        currentHealth = maxHealth = Constants.SHARK_MAXHEALTH;
        powerAttack = Constants.SHARK_ATTACK;
        currentState = previousState = SharkState.IDLE;
        isJumping = false;
        isAttacking = false;
        isDead = false;
        isHurting = false;
        setToDestroy = false;
        destroyed = false;

        setBounds(getX(), getY(), 34 / Constants.PPM, 32 / Constants.PPM);

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
            Gdx.app.log("Shark", "DEAD");
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
            Gdx.app.log("Shark", "DEAD");
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

    public SharkState getState() {
        if (isDead) {
            return SharkState.DEAD;
        } else if (isHurting) {
            return SharkState.HIT;
        } else if (isAttacking) {
            return SharkState.ATTACK;
        } else if (isJumping) {
            return SharkState.JUMP;
        } else if (b2body.getLinearVelocity().x >= 0.5f) {
            return SharkState.RUNNING;
        } else return SharkState.IDLE;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case DEAD:
                region = sharkDead.getKeyFrame(stateTimer);
                if (sharkDead.isAnimationFinished(stateTimer)) {
                    setToDestroy = true;
                    Gdx.app.log("Shark", "inSharkDead");
                }
                break;
            case RUNNING:
                region = sharkRunning.getKeyFrame(stateTimer, true);
                break;
            case HIT:
                region = sharkHit.getKeyFrame(stateTimer);
                if (sharkHit.isAnimationFinished(stateTimer)) {
                    isAttacking = true;
                    isHurting = false;
                    velocity.x = 0.2f;
                    Gdx.app.log("Shark", "inSharkHit");
                }
                break;
            case ATTACK:
                region = sharkAttack.getKeyFrame(stateTimer);
                if (sharkAttack.isAnimationFinished(stateTimer)) {
                    isAttacking = false;
                    Gdx.app.log("Shark", "inSharkAttack");
                }
                break;
            case JUMP:
                region = sharkJump.getKeyFrame(stateTimer);
                if (sharkJump.isAnimationFinished(stateTimer)) {
                    Gdx.app.log("Shark", "inSharkJump");
                }
                break;
            case IDLE:
            default:
                region = sharkIdle.getKeyFrame(stateTimer);
                if (sharkIdle.isAnimationFinished(stateTimer)) {
                    velocity.x = 0.5f;
                }
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
        for (int i = 1; i <= 8; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Shark/Idle/Idle 0" + i + ".png")));
        }
        sharkIdle = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 8; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Shark/Attack/Attack 0" + i + ".png")));
        }
        sharkAttack = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 8; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Shark/Dead Hit/Dead Hit 0" + i + ".png")));
        }
        sharkDead = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Shark/Hit/Hit 0" + i + ".png")));
        }
        sharkHit = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Shark/Run/Run 0" + i + ".png")));
        }
        sharkRunning = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 3; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Shark/Jump/Jump 0" + i + ".png")));
        }
        sharkJump = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
