package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Tools.Utils;

public class Crabby extends Enemy {
    private float stateTime;
    private Array<TextureRegion> frames;

    //
    public enum EnemyState {IDLE, ATTACKING, HIT, DEAD, RUNNING}

    ;
    public EnemyState currentState;
    public EnemyState previousState;
    private float stateTimer;
    private boolean runningRight;
    private Animation<TextureRegion> enemyIdle, enemyAttacking, enemyHit, enemyDead, enemyRunning;
    private boolean isDead = false;
    //
    private boolean setToDestroy;
    private boolean destroyed;

    public Crabby(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTime = 0;
        setBounds(getX(), getY(), 72 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        setToDestroy = false;
        destroyed = false;
        //
        currentState = EnemyState.IDLE;
        previousState = EnemyState.IDLE;
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
    }

    public void update(float dt) {
        stateTime += dt;

        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            isDead = true;
            stateTimer = 0;
            stateTime = 0;
        } else isDead = false;
        //

        //
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        //
        setRegion(getFrame(dt));
    }

    public EnemyState getState() {
        if (b2body.getLinearVelocity().x != 0)
            return EnemyState.RUNNING;
        else if (isDead) {
            return EnemyState.DEAD;
        } else return EnemyState.IDLE;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case ATTACKING:
                region = enemyAttacking.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = enemyRunning.getKeyFrame(stateTimer, true);
                break;
            case HIT:
                region = enemyHit.getKeyFrame(stateTimer);
                break;
            case DEAD:
                region = enemyDead.getKeyFrame(stateTimer);
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
//        bodyDef.position.set(330 / MyGdxGame.PPM, 320 / MyGdxGame.PPM);
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / MyGdxGame.PPM);
        fixtureDef.filter.categoryBits = MyGdxGame.ENEMY_BIT;
        fixtureDef.filter.maskBits = MyGdxGame.GROUND_BIT |
                MyGdxGame.GOLD_COIN_BIT | MyGdxGame.SILVER_COIN_BIT
                | MyGdxGame.SPIKE_BIT | MyGdxGame.ENEMY_BIT | MyGdxGame.PLAYER_BIT;

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed || stateTimer < 1)
            super.draw(batch);
    }

    @Override
    public void hitEnemy() {
        isDead = true;
        setToDestroy = true;
        Gdx.app.log("Crabby: ", "DEAD");
    }
}
