package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.Utils;

public class Crabby extends Enemy {
    private Array<TextureRegion> frames;
    //
    public enum CrabbyState {IDLE, ATTACKING, HIT, DEAD, RUNNING}

    public CrabbyState currentState;
    public CrabbyState previousState;
    private float stateTimer;
    public int maxHealth, currentHealth, powerAttack;
    private boolean runningRight;
    private Animation<TextureRegion> enemyIdle, enemyAttacking, enemyHit, enemyDead, enemyRunning;
    private boolean isDead = false;
    private boolean isHurting = false;
    private boolean isAttacking = false;
    //
    private boolean setToDestroy;
    private boolean destroyed;

    public Crabby(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setBounds(getX(), getY(), 72 / Constants.PPM, 32 / Constants.PPM);
        setToDestroy = false;
        destroyed = false;
        //
        currentState = CrabbyState.IDLE;
        previousState = CrabbyState.IDLE;
        stateTimer = 0;
        runningRight = true;
        //
        frames = new Array<TextureRegion>();
        for (int i = 1; i <= 9; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Crabby/Idle/Idle 0" + i + ".png")));
        }
        enemyIdle = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Crabby/Run/Run 0" + i + ".png")));
        }
        enemyRunning = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Crabby/Hit/Hit 0" + i + ".png")));
        }
        enemyHit = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 8; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Crabby/Dead Hit/Dead Hit 0" + i + ".png")));
        }
        enemyDead = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 7; i++) {
            frames.add(new TextureRegion(Utils.getRegion("enemy/Crabby/Attack/Attack 0" + i + ".png")));
        }
        enemyAttacking = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        //
        maxHealth = Constants.CRABBY_MAXHEALTH;
        currentHealth = maxHealth;
        powerAttack = Constants.CRABBY_ATTACK;
    }

    public void update(float dt) {
        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            isDead = false;
            stateTimer = 0;
        }
        //

        //
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //
        setRegion(getFrame(dt));
    }

    public CrabbyState getState() {
        if (isDead) {
            return CrabbyState.DEAD;
        } else if (isHurting) {
            return CrabbyState.HIT;
        } else if (isAttacking) {
            return CrabbyState.ATTACKING;
        } else if (b2body.getLinearVelocity().x >= 0.5f) {
            return CrabbyState.RUNNING;
        } else return CrabbyState.IDLE;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case ATTACKING:
                region = enemyAttacking.getKeyFrame(stateTimer, true);
                if (enemyAttacking.isAnimationFinished(stateTimer)) {
                    isAttacking = false;
                    velocity.x = 0.2f;
                }
                break;
            case RUNNING:
                region = enemyRunning.getKeyFrame(stateTimer, true);
                break;
            case HIT:
                region = enemyHit.getKeyFrame(stateTimer);
                if (enemyHit.isAnimationFinished(stateTimer)) {
                    isHurting = false;
                    isAttacking = true;
                    velocity.x = 0.2f;
                }
                break;
            case DEAD:
                region = enemyDead.getKeyFrame(stateTimer);
                if (enemyDead.isAnimationFinished(stateTimer)) {
                    setToDestroy = true;
                    Gdx.app.log("Crabby: ", "in enemyDead");
                }
                break;
            case IDLE:
            default:
                region = enemyIdle.getKeyFrame(stateTimer);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
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
        fixtureDef.filter.maskBits = Constants.GROUND_BIT |
                Constants.GOLD_COIN_BIT | Constants.SILVER_COIN_BIT
                | Constants.SPIKE_BIT | Constants.ENEMY_BIT | Constants.PLAYER_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed || stateTimer < 1)
            super.draw(batch);
    }

    @Override
    public void getsHurt() {
        currentHealth -= Constants.PLAYER_ATTACK;
        if (currentHealth <= 0) {
            isDead = true;
        } else if (currentHealth < maxHealth) {
            isHurting = true;
        }
    }

    public void canSeePlayer(Player player) {
        Vector2 playerPosition = new Vector2(player.getX(), player.getY());
        Vector2 enemyPosition = new Vector2(getX(), getY());
        float distance = Math.abs(player.getX() - getX());
        float attackRange = 32;
        if (distance - attackRange <= 0) {
            isAttacking = true;
        }
        Gdx.app.log("canSeePlayer", "distance:" + distance);
    }
}
