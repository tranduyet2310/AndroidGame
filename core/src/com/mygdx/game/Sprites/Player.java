package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.StateManager;
import com.mygdx.game.Tools.Utils;

public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING, AIRATTACK, DEAD, HIT}
    ;
    public State currentState;
    public State previousState;
    public World world;
    public Body b2Body;
    private TextureRegion playerStand;
    private Animation<TextureRegion> playerRun, playerJump, playerIdle, playerFalling, playerAttacking, playerAirAttack, playerHit, playerDead;
    private float stateTimer;
    private boolean runningRight;
    public boolean isAttacking = false;
    public boolean isAirAttack = false;

    public Player(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        setPosition(x, y);

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i <= 5; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Idle Sword/Idle Sword 0" + i + ".png")));
        }
        playerIdle = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Run Sword/Run Sword 0" + i + ".png")));
        }
        playerRun = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        for (int i = 1; i <= 3; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Jump Sword/Jump Sword 0" + i + ".png")));
        }
        playerJump = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        for (int i = 1; i <= 3; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Fall Sword/Fall 0" + i + ".png")));
        }
        playerFalling = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        for (int i = 1; i <= 9; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Attack 1/Attack 1 0" + i + ".png")));
        }
        playerAttacking = new Animation<TextureRegion>(0.3f, frames);
        frames.clear();
        for (int i = 1; i <= 6; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Air Attack 1/Air Attack 1 0" + i + ".png")));
        }
        playerAirAttack = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Dead Hit/Dead Hit 0" + i + ".png")));
        }
        playerDead = new Animation<TextureRegion>(0.4f, frames);
        frames.clear();
        for (int i = 1; i <= 4; i++) {
            frames.add(new TextureRegion(Utils.getRegion("player/Hit Sword/Hit Sword 0" + i + ".png")));
        }
        playerHit = new Animation<TextureRegion>(0.4f, frames);

        // define player character in Box2d
        definePlayer();
        playerStand = new TextureRegion(Utils.getRegion("player/Idle Sword/Idle Sword 01.png"));
        setBounds(0, 0, 64 / Constants.PPM, 48 / Constants.PPM);
        setRegion(playerStand);
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case FALLING:
                region = playerFalling.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case ATTACKING:
                region = playerAttacking.getKeyFrame(stateTimer);
                break;
            case AIRATTACK:
                region = playerAirAttack.getKeyFrame(stateTimer);
                break;
            case HIT:
                region = playerHit.getKeyFrame(stateTimer);
                break;
            case DEAD:
                region = playerDead.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = playerIdle.getKeyFrame(stateTimer);
                break;
        }
        if ((b2Body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2Body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (b2Body.getLinearVelocity().y > 0 || (b2Body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
            if (isAirAttack) {
                return State.AIRATTACK;
            }
            return State.JUMPING;
        } else if (b2Body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2Body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else if (isAttacking) {
            return State.ATTACKING;
        } else if (StateManager.playerOnWater) {
            return State.DEAD;
        } else return State.STANDING;
    }

    public void definePlayer() {
        BodyDef bodyDef = new BodyDef();
//        bodyDef.position.set(330 / Constants.PPM, 320 / Constants.PPM);
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Constants.PPM);
        fixtureDef.filter.categoryBits = Constants.PLAYER_BIT;
        fixtureDef.filter.maskBits = Constants.GROUND_BIT | Constants.GOLD_COIN_BIT | Constants.SILVER_COIN_BIT | Constants.ENEMY_BIT | Constants.SPIKE_BIT;

        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef).setUserData("Player");

        FixtureDef swordDef = new FixtureDef();
        EdgeShape swordShape = new EdgeShape();
        swordShape.set(new Vector2((5f) / Constants.PPM, b2Body.getLocalCenter().y / Constants.PPM), new Vector2((30f) / Constants.PPM, b2Body.getLocalCenter().y / Constants.PPM));
        swordDef.shape = swordShape;
        swordDef.isSensor = true;
        b2Body.createFixture(swordDef).setUserData("Sword");

//        EdgeShape head = new EdgeShape();
//        head.set(new Vector2(-2 / MyGdxGame.PPM, 10/ MyGdxGame.PPM), new Vector2(2 / MyGdxGame.PPM, 10/ MyGdxGame.PPM));
//        fixtureDef.shape = head;
        //fixtureDef.isSensor = true;
        //b2Body.createFixture(fixtureDef);
        //b2Body.setUserData("Player");
    }
}
