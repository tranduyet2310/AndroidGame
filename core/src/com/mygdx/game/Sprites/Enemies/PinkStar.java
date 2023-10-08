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

public class PinkStar extends Enemy {
    public enum PinkStarState {RUNNING, IDLE, HIT, DEAD, ATTACK, JUMP}

    public PinkStarState currentState;
    public PinkStarState previousState;
    private float stateTimer;
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> pinkStarIdle, pinkStarHit, pinkStarDead, pinkStarAttack, pinkStarRunning, pinkStarJump;
    private boolean setToDestroy;
    private boolean destroyed;
    public int currentHealth, maxHealth, powerAttack;
    //
    private boolean isDead, isHurting, isAttacking, isJumping;

    public PinkStar(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTimer = 0;
        currentHealth = maxHealth = Constants.STAR_MAXHEALTH;
        powerAttack = Constants.STAR_ATTACK;
        currentState = previousState = PinkStarState.IDLE;
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
            Gdx.app.log("PinkStar", "DEAD");
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
            Gdx.app.log("PinkStar", "DEAD");
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

    public PinkStarState getState() {
        if (isDead) {
            return PinkStarState.DEAD;
        } else if (isHurting) {
            return PinkStarState.HIT;
        } else if (isAttacking) {
            return PinkStarState.ATTACK;
        } else if (isJumping) {
            return PinkStarState.JUMP;
        } else if (b2body.getLinearVelocity().x >= 0.5f) {
            return PinkStarState.RUNNING;
        } else return PinkStarState.IDLE;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case DEAD:
                region = pinkStarDead.getKeyFrame(stateTimer);
                if (pinkStarDead.isAnimationFinished(stateTimer)) {
                    setToDestroy = true;
                    Gdx.app.log("PinkStar", "inPinkStarDead");
                }
                break;
            case RUNNING:
                region = pinkStarRunning.getKeyFrame(stateTimer, true);
                break;
            case HIT:
                region = pinkStarHit.getKeyFrame(stateTimer);
                if (pinkStarHit.isAnimationFinished(stateTimer)) {
                    isAttacking = true;
                    isHurting = false;
                    velocity.x = 0.2f;
                    Gdx.app.log("PinkStar", "inPinkStarkHit");
                }
                break;
            case ATTACK:
                region = pinkStarAttack.getKeyFrame(stateTimer);
                if (pinkStarAttack.isAnimationFinished(stateTimer)) {
                    isAttacking = false;
                    Gdx.app.log("PinkStar", "inPinkStarAttack");
                }
                break;
            case JUMP:
                region = pinkStarJump.getKeyFrame(stateTimer);
                if (pinkStarJump.isAnimationFinished(stateTimer)) {
                    Gdx.app.log("PinkStar", "inPinkStarJump");
                }
                break;
            case IDLE:
            default:
                region = pinkStarIdle.getKeyFrame(stateTimer);
                if (pinkStarIdle.isAnimationFinished(stateTimer)) {
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
            frames.add(new TextureRegion(Utils.getRegion("enemy/Pink Star/Idle/Idle 0" + i + ".png")));
        }
        pinkStarIdle = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 8; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Pink Star/Attack/Attack 0" + i + ".png")));
        }
        pinkStarAttack = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 8; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Pink Star/Dead Hit/Dead Hit 0" + i + ".png")));
        }
        pinkStarDead = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Pink Star/Hit/Hit 0" + i + ".png")));
        }
        pinkStarHit = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Pink Star/Run/Run 0" + i + ".png")));
        }
        pinkStarRunning = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 3; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Pink Star/Jump/Jump 0" + i + ".png")));
        }
        pinkStarJump = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
